package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PurchaseDeviceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface PurchaseDeviceInfoMapper extends BaseMapper<PurchaseDeviceInfo> {

    /**
     * 分页获取器材采购记录
     *
     * @param page               分页对象
     * @param purchaseDeviceInfo 器材采购记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPurchaseDevicePage(Page<PurchaseDeviceInfo> page, @Param("purchaseDeviceInfo") PurchaseDeviceInfo purchaseDeviceInfo);
}
