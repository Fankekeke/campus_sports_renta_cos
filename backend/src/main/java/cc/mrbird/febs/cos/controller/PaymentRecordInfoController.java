package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PaymentRecordInfo;
import cc.mrbird.febs.cos.service.IPaymentRecordInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 支付记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/payment-record-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentRecordInfoController {

    private final IPaymentRecordInfoService paymentRecordInfoService;

    /**
     * 分页获取支付记录
     *
     * @param page              分页对象
     * @param paymentRecordInfo 支付记录
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PaymentRecordInfo> page, PaymentRecordInfo paymentRecordInfo) {
        return R.ok(paymentRecordInfoService.queryPaymentRecordPage(page, paymentRecordInfo));
    }

    /**
     * 支付记录详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(paymentRecordInfoService.getById(id));
    }

    /**
     * 支付记录列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(paymentRecordInfoService.list());
    }

    /**
     * 新增支付记录
     *
     * @param paymentRecordInfo 支付记录
     * @return 结果
     */
    @PostMapping
    public R save(PaymentRecordInfo paymentRecordInfo) {
        paymentRecordInfo.setPayDate(DateUtil.formatDateTime(new Date()));
        return R.ok(paymentRecordInfoService.save(paymentRecordInfo));
    }

    /**
     * 修改支付记录
     *
     * @param paymentRecordInfo 支付记录
     * @return 结果
     */
    @PutMapping
    public R edit(PaymentRecordInfo paymentRecordInfo) {
        return R.ok(paymentRecordInfoService.updateById(paymentRecordInfo));
    }

    /**
     * 删除支付记录
     *
     * @param ids ids
     * @return 支付记录
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(paymentRecordInfoService.removeByIds(ids));
    }
}
