package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CreditRecordInfo;
import cc.mrbird.febs.cos.service.ICreditRecordInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 信用积分记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/credit-record-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditRecordInfoController {

    private final ICreditRecordInfoService creditRecordInfoService;

    /**
     * 分页获取信用积分记录
     *
     * @param page             分页对象
     * @param creditRecordInfo 信用积分记录
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CreditRecordInfo> page, CreditRecordInfo creditRecordInfo) {
        return R.ok(creditRecordInfoService.queryCreditPage(page, creditRecordInfo));
    }

    /**
     * 信用积分记录详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(creditRecordInfoService.getById(id));
    }

    /**
     * 信用积分记录列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(creditRecordInfoService.list());
    }

    /**
     * 新增信用积分记录
     *
     * @param creditRecordInfo 信用积分记录
     * @return 结果
     */
    @PostMapping
    public R save(CreditRecordInfo creditRecordInfo) {
        creditRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(creditRecordInfoService.save(creditRecordInfo));
    }

    /**
     * 修改信用积分记录
     *
     * @param creditRecordInfo 信用积分记录
     * @return 结果
     */
    @PutMapping
    public R edit(CreditRecordInfo creditRecordInfo) {
        return R.ok(creditRecordInfoService.updateById(creditRecordInfo));
    }

    /**
     * 删除信用积分记录
     *
     * @param ids ids
     * @return 信用积分记录
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(creditRecordInfoService.removeByIds(ids));
    }
}
