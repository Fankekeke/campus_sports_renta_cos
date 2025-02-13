package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.PurchaseDeviceInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IPurchaseDeviceInfoService extends IService<PurchaseDeviceInfo> {

    /**
     * 分页获取器材采购记录
     *
     * @param page               分页对象
     * @param purchaseDeviceInfo 器材采购记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPurchaseDevicePage(Page<PurchaseDeviceInfo> page, PurchaseDeviceInfo purchaseDeviceInfo);

    /**
     * 添加器材采购记录
     *
     * @param purchaseDeviceInfo 采购记录
     * @return 结果
     */
    Boolean addPurchaseRecord(PurchaseDeviceInfo purchaseDeviceInfo) throws FebsException;
}
