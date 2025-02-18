package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.PurchaseDetailInfo;
import cc.mrbird.febs.cos.entity.PurchaseDeviceInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 获取器材采购记录详情
     *
     * @param code 采购单编号
     * @return 结果
     */
    List<PurchaseDetailInfo> queryPurchaseDetail(String code);
}
