package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.RentOrderInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IRentOrderInfoService extends IService<RentOrderInfo> {

    /**
     * 分页获取租借订单
     *
     * @param page          分页对象
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryRentOrderPage(Page<RentOrderInfo> page, RentOrderInfo rentOrderInfo);

    /**
     * 计算订单金额
     *
     * @param rentOrderInfo 订单信息
     * @return 结果
     */
    RentOrderInfo calculateOrderPrice(RentOrderInfo rentOrderInfo) throws FebsException;

    /**
     * 新增租借订单
     *
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    boolean addRentOrder(RentOrderInfo rentOrderInfo);

    /**
     * 支付回调
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    boolean callbackPayment(String orderCode);

    /**
     * 校验用户是否还有未归还订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    boolean checkOrderByUser(Integer userId) throws FebsException;

    /**
     * 归还设备
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    boolean returnDevice(String orderCode);

    /**
     * 器材归还审核入库
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    boolean checkReturnDevice(String orderCode);

    /**
     * 首页数据
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();
}
