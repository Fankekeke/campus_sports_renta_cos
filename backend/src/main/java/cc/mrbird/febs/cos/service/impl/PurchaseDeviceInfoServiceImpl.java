package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.DeviceInfo;
import cc.mrbird.febs.cos.entity.DeviceTypeInfo;
import cc.mrbird.febs.cos.entity.PurchaseDetailInfo;
import cc.mrbird.febs.cos.entity.PurchaseDeviceInfo;
import cc.mrbird.febs.cos.dao.PurchaseDeviceInfoMapper;
import cc.mrbird.febs.cos.service.IDeviceInfoService;
import cc.mrbird.febs.cos.service.IDeviceTypeInfoService;
import cc.mrbird.febs.cos.service.IPurchaseDetailInfoService;
import cc.mrbird.febs.cos.service.IPurchaseDeviceInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseDeviceInfoServiceImpl extends ServiceImpl<PurchaseDeviceInfoMapper, PurchaseDeviceInfo> implements IPurchaseDeviceInfoService {

    private final IPurchaseDetailInfoService purchaseDetailInfoService;

    private final IDeviceInfoService deviceInfoService;

    private final IDeviceTypeInfoService deviceTypeInfoService;

    /**
     * 分页获取器材采购记录
     *
     * @param page               分页对象
     * @param purchaseDeviceInfo 器材采购记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPurchaseDevicePage(Page<PurchaseDeviceInfo> page, PurchaseDeviceInfo purchaseDeviceInfo) {
        return baseMapper.queryPurchaseDevicePage(page, purchaseDeviceInfo);
    }

    /**
     * 添加器材采购记录
     *
     * @param purchaseDeviceInfo 采购记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addPurchaseRecord(PurchaseDeviceInfo purchaseDeviceInfo) throws FebsException {
        // 设置采购编号
        purchaseDeviceInfo.setCode("PUR-" + System.currentTimeMillis());
        purchaseDeviceInfo.setCreateDate(DateUtil.formatDateTime(new Date()));

        // 获取采购器械信息
        if (StrUtil.isEmpty(purchaseDeviceInfo.getGoods())) {
            throw new FebsException("请填写采购信息");
        }
        JSONArray array = JSONUtil.parseArray(purchaseDeviceInfo.getGoods());
        List<PurchaseDetailInfo> purchaseDeviceList = JSONUtil.toList(array, PurchaseDetailInfo.class);

        // 器材入库
        List<DeviceInfo> deviceInfoList = new ArrayList<>();

        // 计算总价格
        for (PurchaseDetailInfo purchaseDetailInfo : purchaseDeviceList) {
            purchaseDetailInfo.setPurchaseCode(purchaseDeviceInfo.getCode());
            purchaseDeviceInfo.setTotalPrice(NumberUtil.mul(purchaseDetailInfo.getNum(), purchaseDetailInfo.getUnitPrice()));

            // 添加器材
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setCode("DEV-" + UUID.randomUUID());
            deviceInfo.setName(purchaseDetailInfo.getName());
            deviceInfo.setTypeId(purchaseDetailInfo.getTypeId());
            deviceInfo.setModel(purchaseDetailInfo.getModel());
            deviceInfo.setChargePerson(purchaseDeviceInfo.getChargePerson());
            deviceInfo.setBrand(purchaseDetailInfo.getBrand());
            deviceInfo.setStatus("1");
            deviceInfo.setOutFlag("0");
            deviceInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            deviceInfo.setSupplierId(purchaseDeviceInfo.getSupplierId());
            deviceInfo.setDepositPrice(BigDecimal.ZERO);
            deviceInfo.setUnitPrice(BigDecimal.ZERO);
            deviceInfoList.add(deviceInfo);
        }

        deviceInfoService.saveBatch(deviceInfoList);
        return this.save(purchaseDeviceInfo) && purchaseDetailInfoService.saveBatch(purchaseDeviceList);
    }

    /**
     * 获取器材采购记录详情
     *
     * @param code 采购单编号
     * @return 结果
     */
    @Override
    public List<PurchaseDetailInfo> queryPurchaseDetail(String code) {
        // 获取器材采购记录详情
        List<PurchaseDetailInfo> purchaseDetailInfoList = purchaseDetailInfoService.list(Wrappers.<PurchaseDetailInfo>lambdaQuery().eq(PurchaseDetailInfo::getPurchaseCode, code));
        if (CollectionUtil.isEmpty(purchaseDetailInfoList)) {
            return purchaseDetailInfoList;
        }

        // 获取器材类型
        List<DeviceTypeInfo> deviceTypeInfoList = deviceTypeInfoService.list();
        // 按id转map
        Map<Integer, String> typeMap = deviceTypeInfoList.stream().collect(Collectors.toMap(DeviceTypeInfo::getId, DeviceTypeInfo::getName));
        for (PurchaseDetailInfo purchaseDetailInfo : purchaseDetailInfoList) {
            purchaseDetailInfo.setType(typeMap.get(purchaseDetailInfo.getTypeId()));
        }
        return purchaseDetailInfoList;
    }
}
