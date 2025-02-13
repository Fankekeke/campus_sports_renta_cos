package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PurchaseDetailInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IPurchaseDetailInfoService extends IService<PurchaseDetailInfo> {

    /**
     * 分页获取采购详情
     *
     * @param page               分页对象
     * @param purchaseDetailInfo 采购详情
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPurchaseDeatilPage(Page<PurchaseDetailInfo> page, PurchaseDetailInfo purchaseDetailInfo);
}
