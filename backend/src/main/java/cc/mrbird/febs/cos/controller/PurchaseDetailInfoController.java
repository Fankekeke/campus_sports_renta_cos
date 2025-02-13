package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PurchaseDetailInfo;
import cc.mrbird.febs.cos.service.IPurchaseDetailInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 采购详情
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/purchase-detail-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseDetailInfoController {

    private final IPurchaseDetailInfoService purchaseDetailInfoService;

    /**
     * 分页获取采购详情
     *
     * @param page               分页对象
     * @param purchaseDetailInfo 采购详情
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PurchaseDetailInfo> page, PurchaseDetailInfo purchaseDetailInfo) {
        return R.ok(purchaseDetailInfoService.queryPurchaseDeatilPage(page, purchaseDetailInfo));
    }

    /**
     * 采购详情详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(purchaseDetailInfoService.getById(id));
    }

    /**
     * 采购详情列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(purchaseDetailInfoService.list());
    }

    /**
     * 新增采购详情
     *
     * @param purchaseDetailInfo 采购详情
     * @return 结果
     */
    @PostMapping
    public R save(PurchaseDetailInfo purchaseDetailInfo) {
        return R.ok(purchaseDetailInfoService.save(purchaseDetailInfo));
    }

    /**
     * 修改采购详情
     *
     * @param purchaseDetailInfo 采购详情
     * @return 结果
     */
    @PutMapping
    public R edit(PurchaseDetailInfo purchaseDetailInfo) {
        return R.ok(purchaseDetailInfoService.updateById(purchaseDetailInfo));
    }

    /**
     * 删除采购详情
     *
     * @param ids ids
     * @return 采购详情
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(purchaseDetailInfoService.removeByIds(ids));
    }
}
