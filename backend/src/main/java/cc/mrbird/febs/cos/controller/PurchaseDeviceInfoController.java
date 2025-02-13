package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PurchaseDeviceInfo;
import cc.mrbird.febs.cos.service.IPurchaseDeviceInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 器材采购记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/purchase-device-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PurchaseDeviceInfoController {

    private final IPurchaseDeviceInfoService purchaseDeviceInfoService;

    /**
     * 分页获取器材采购记录
     *
     * @param page               分页对象
     * @param purchaseDeviceInfo 器材采购记录
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PurchaseDeviceInfo> page, PurchaseDeviceInfo purchaseDeviceInfo) {
        return R.ok(purchaseDeviceInfoService.queryPurchaseDevicePage(page, purchaseDeviceInfo));
    }

    /**
     * 器材采购记录详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(purchaseDeviceInfoService.getById(id));
    }

    /**
     * 器材采购记录列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(purchaseDeviceInfoService.list());
    }

    /**
     * 新增器材采购记录
     *
     * @param purchaseDeviceInfo 器材采购记录
     * @return 结果
     */
    @PostMapping
    public R save(PurchaseDeviceInfo purchaseDeviceInfo) throws FebsException {
        return R.ok(purchaseDeviceInfoService.addPurchaseRecord(purchaseDeviceInfo));
    }

    /**
     * 修改器材采购记录
     *
     * @param purchaseDeviceInfo 器材采购记录
     * @return 结果
     */
    @PutMapping
    public R edit(PurchaseDeviceInfo purchaseDeviceInfo) {
        return R.ok(purchaseDeviceInfoService.updateById(purchaseDeviceInfo));
    }

    /**
     * 删除器材采购记录
     *
     * @param ids ids
     * @return 器材采购记录
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(purchaseDeviceInfoService.removeByIds(ids));
    }
}
