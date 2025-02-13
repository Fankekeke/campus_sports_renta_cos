package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.DeviceInfoMapper;
import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.RentOrderInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentOrderInfoServiceImpl extends ServiceImpl<RentOrderInfoMapper, RentOrderInfo> implements IRentOrderInfoService {

    private final DeviceInfoMapper deviceInfoMapper;

    private final UserInfoMapper userInfoMapper;

    private final IPaymentRecordInfoService paymentRecordInfoService;

    private final ICreditRecordInfoService creditRecordInfoService;

    private final IMessageInfoService messageInfoService;

    private final StaffInfoMapper staffInfoMapper;

    private final IBulletinInfoService bulletinInfoService;

    /**
     * 分页获取租借订单
     *
     * @param page          分页对象
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryRentOrderPage(Page<RentOrderInfo> page, RentOrderInfo rentOrderInfo) {
        return baseMapper.queryRentOrderPage(page, rentOrderInfo);
    }

    /**
     * 计算订单金额
     *
     * @param rentOrderInfo 订单信息
     * @return 结果
     */
    @Override
    public RentOrderInfo calculateOrderPrice(RentOrderInfo rentOrderInfo) throws FebsException {
        // 设置起租时间
        rentOrderInfo.setStartDate(DateUtil.formatDateTime(new Date()));
        // 判断维修结束时间是否小于等于当前时间
        if (DateUtil.compare(DateUtil.parseDateTime(rentOrderInfo.getEndDate()), new Date()) <= 0) {
            throw new FebsException("租赁结束时间不能小于当前时间");
        }
        // 计算租赁小时
        if (rentOrderInfo.getStartDate() != null && rentOrderInfo.getEndDate() != null) {
            long startTime = DateUtil.parse(rentOrderInfo.getStartDate()).getTime();
            long endTime = DateUtil.parse(rentOrderInfo.getEndDate()).getTime();
            long hours = (endTime - startTime) / (1000 * 60 * 60);
            rentOrderInfo.setRentHour((int) hours);
        }

        // 获取用户信息
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, rentOrderInfo.getUserId()));
        // 获取器材信息
        if (rentOrderInfo.getDeviceId() != null) {
            // 获取器材信息
            DeviceInfo deviceInfo = deviceInfoMapper.selectById(rentOrderInfo.getDeviceId());
            // 租借价格
            BigDecimal rentPrice = NumberUtil.mul(deviceInfo.getUnitPrice(), rentOrderInfo.getRentHour());
            // 押金
            if (userInfo != null && userInfo.getCreditScore() > 90) {
                rentOrderInfo.setDepositPrice(BigDecimal.ZERO);
            } else {
                rentOrderInfo.setDepositPrice(deviceInfo.getDepositPrice());
            }
            rentOrderInfo.setTotalPrice(NumberUtil.add(rentPrice, rentOrderInfo.getDepositPrice()));
        }
        return rentOrderInfo;
    }

    /**
     * 新增租借订单
     *
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    @Override
    public boolean addRentOrder(RentOrderInfo rentOrderInfo) {
        rentOrderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 设置租借订单编号
        rentOrderInfo.setCode("OR-" + System.currentTimeMillis());
        // 设置用户ID
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, rentOrderInfo.getUserId()));
        if (userInfo != null) {
            rentOrderInfo.setUserId(userInfo.getId());
        }
        rentOrderInfo.setStatus("0");
        return this.save(rentOrderInfo);
    }

    /**
     * 支付回调
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean callbackPayment(String orderCode) {
        RentOrderInfo rentOrderInfo = this.getOne(Wrappers.<RentOrderInfo>lambdaQuery().eq(RentOrderInfo::getCode, orderCode));
        // 设置器械状态
        DeviceInfo deviceInfo = deviceInfoMapper.selectOne(Wrappers.<DeviceInfo>lambdaQuery().eq(DeviceInfo::getId, rentOrderInfo.getDeviceId()));
        deviceInfo.setStatus("2");
        deviceInfoMapper.updateById(deviceInfo);
        // 更新订单状态
        rentOrderInfo.setStatus("1");
        // 添加支付记录
        PaymentRecordInfo paymentInfo = new PaymentRecordInfo();
        paymentInfo.setOrderCode(orderCode);
        paymentInfo.setUserId(rentOrderInfo.getUserId());
        paymentInfo.setOrderPrice(rentOrderInfo.getTotalPrice());
        paymentInfo.setPayDate(DateUtil.formatDateTime(new Date()));
        paymentRecordInfoService.save(paymentInfo);

        // 添加支付消息通知
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setUserId(rentOrderInfo.getUserId());
        messageInfo.setContent("您有一笔租借订单已完成支付，请在 " + rentOrderInfo.getEndDate() + " 前及时归还设备");
        messageInfo.setStatus("0");
        messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        messageInfoService.save(messageInfo);

        return this.updateById(rentOrderInfo);
    }

    /**
     * 校验用户是否还有未归还订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public boolean checkOrderByUser(Integer userId) throws FebsException {
        // 获取用户信息
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (userInfo != null) {
            if (userInfo.getCreditScore() < 60) {
                throw new FebsException("用户信用分不足，无法进行租借");
            }
            // 获取用户未归还订单
            int count = this.count(Wrappers.<RentOrderInfo>lambdaQuery().eq(RentOrderInfo::getUserId, userInfo.getId()).eq(RentOrderInfo::getStatus, "1"));
            if (count > 0) {
                throw new FebsException("用户还有未归还订单，无法进行租借");
            }
        }
        return false;
    }

    /**
     * 归还设备
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @Override
    public boolean returnDevice(String orderCode) {
        // 获取订单信息
        RentOrderInfo rentOrderInfo = this.getOne(Wrappers.<RentOrderInfo>lambdaQuery().eq(RentOrderInfo::getCode, orderCode));
        // 获取用户信息
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, rentOrderInfo.getUserId()));
        // 逾期一次扣10分
        if (DateUtil.compare(DateUtil.parseDateTime(rentOrderInfo.getReturnDate()), new Date()) > 0) {
            // 获取用户信息
            userInfo.setCreditScore(userInfo.getCreditScore() - 10);
            userInfoMapper.updateById(userInfo);
            // 添加信用积分记录
            CreditRecordInfo creditRecordInfo = new CreditRecordInfo();
            creditRecordInfo.setUserId(userInfo.getId());
            creditRecordInfo.setType("2");
            creditRecordInfo.setScore(10);
            creditRecordInfo.setContent("订单-" + rentOrderInfo.getCode() + "逾期归还，信用分-10");
            creditRecordInfo.setAfterScore(userInfo.getCreditScore());
            creditRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            creditRecordInfoService.save(creditRecordInfo);

            // 添加消息通知
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setUserId(rentOrderInfo.getUserId());
            messageInfo.setContent("订单-" + rentOrderInfo.getCode() + "逾期归还，信用分-10");
            messageInfo.setStatus("0");
            messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            messageInfoService.save(messageInfo);
        } else {
            userInfo.setCreditScore(userInfo.getCreditScore() + 10);
            userInfoMapper.updateById(userInfo);
            // 添加信用积分记录
            CreditRecordInfo creditRecordInfo = new CreditRecordInfo();
            creditRecordInfo.setUserId(userInfo.getId());
            creditRecordInfo.setType("1");
            creditRecordInfo.setScore(10);
            creditRecordInfo.setContent("订单-" + rentOrderInfo.getCode() + "在 " + rentOrderInfo.getEndDate() + " 前归还，信用分+10");
            creditRecordInfo.setAfterScore(userInfo.getCreditScore());
            creditRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            creditRecordInfoService.save(creditRecordInfo);

            // 添加消息通知
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setUserId(rentOrderInfo.getUserId());
            messageInfo.setContent("订单-" + rentOrderInfo.getCode() + "在 " + rentOrderInfo.getEndDate() + " 前归还，信用分+10");
            messageInfo.setStatus("0");
            messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            messageInfoService.save(messageInfo);
        }
        rentOrderInfo.setStatus("2");
        rentOrderInfo.setReturnDate(DateUtil.formatDateTime(new Date()));
        return this.updateById(rentOrderInfo);
    }

    /**
     * 器材归还审核入库
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @Override
    public boolean checkReturnDevice(String orderCode) {
        // 获取订单信息
        RentOrderInfo rentOrderInfo = this.getOne(Wrappers.<RentOrderInfo>lambdaQuery().eq(RentOrderInfo::getCode, orderCode));
        // 设置器材状态
        DeviceInfo deviceInfo = deviceInfoMapper.selectOne(Wrappers.<DeviceInfo>lambdaQuery().eq(DeviceInfo::getId, rentOrderInfo.getDeviceId()));
        deviceInfo.setStatus("1");
        deviceInfoMapper.updateById(deviceInfo);
        // 订单状态完成
        rentOrderInfo.setStatus("3");
        return this.updateById(rentOrderInfo);
    }

    /**
     * 首页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 查询员工信息
        Integer staffNum = staffInfoMapper.selectCount(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getDelFlag, "0"));

        // 总订单数量
        Integer orderNum = this.count(Wrappers.<RentOrderInfo>lambdaQuery().ne(RentOrderInfo::getStatus, 0));
        // 总收益
        List<PaymentRecordInfo> paymentRecordList = paymentRecordInfoService.list(Wrappers.<PaymentRecordInfo>lambdaQuery());
        BigDecimal amount = CollectionUtil.isEmpty(paymentRecordList) ? BigDecimal.ZERO : paymentRecordList.stream().map(PaymentRecordInfo::getOrderPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<RentOrderInfo> orderListMonth = baseMapper.selectOrderInfoByMonth();
        List<RentOrderInfo> orderListYear = baseMapper.selectOrderInfoByYear();
        // 本月订单量
        Integer orderNumMonth = orderListMonth.size();
        // 本月收益
        BigDecimal orderAmountMonth = orderListMonth.stream().filter(e -> !"0".equals(e.getStatus())).map(RentOrderInfo::getTotalPrice).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 本年订单量
        Integer orderNumYear = orderListYear.size();
        // 本年收益
        BigDecimal orderAmountYear = orderListYear.stream().filter(e -> !"0".equals(e.getStatus())).map(RentOrderInfo::getTotalPrice).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 近十天内订单数量统计
        List<LinkedHashMap<String, Object>> orderNumDays = baseMapper.selectOrderNumDays();
        // 近十天内订单收益统计
        List<LinkedHashMap<String, Object>> orderAmountDays = baseMapper.selectOrderAmountDays();
        // 公告
        List<BulletinInfo> bulletinInfoList = bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1));
        result.put("orderNumMonth", orderNumMonth);
        result.put("orderAmountMonth", orderAmountMonth);
        result.put("orderNumYear", orderNumYear);
        result.put("orderAmountYear", orderAmountYear);

        result.put("staffNum", staffNum);
        result.put("orderNum", orderNum);
        result.put("totalPrice", amount);
        result.put("orderNumDays", orderNumDays);
        result.put("orderAmountDays", orderAmountDays);
        result.put("bulletinInfoList", bulletinInfoList);
        return result;
    }
}
