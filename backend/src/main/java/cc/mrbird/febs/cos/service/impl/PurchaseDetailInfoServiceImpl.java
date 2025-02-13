package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PurchaseDetailInfo;
import cc.mrbird.febs.cos.dao.PurchaseDetailInfoMapper;
import cc.mrbird.febs.cos.service.IPurchaseDetailInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class PurchaseDetailInfoServiceImpl extends ServiceImpl<PurchaseDetailInfoMapper, PurchaseDetailInfo> implements IPurchaseDetailInfoService {


    /**
     * 分页获取采购详情
     *
     * @param page               分页对象
     * @param purchaseDetailInfo 采购详情
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPurchaseDeatilPage(Page<PurchaseDetailInfo> page, PurchaseDetailInfo purchaseDetailInfo) {
        return baseMapper.queryPurchaseDeatilPage(page, purchaseDetailInfo);
    }
}
