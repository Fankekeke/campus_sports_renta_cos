package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RepairRecordInfo;
import cc.mrbird.febs.cos.service.IRepairRecordInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 器械维修
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/repair-record-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairRecordInfoController {

    private final IRepairRecordInfoService repairRecordInfoService;

    /**
     * 分页获取器械维修
     *
     * @param page             分页对象
     * @param repairRecordInfo 器械维修
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RepairRecordInfo> page, RepairRecordInfo repairRecordInfo) {
        return R.ok(repairRecordInfoService.queryRepairPage(page, repairRecordInfo));
    }

    /**
     * 器械维修详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(repairRecordInfoService.getById(id));
    }

    /**
     * 器械维修列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(repairRecordInfoService.list());
    }

    /**
     * 新增器械维修
     *
     * @param repairRecordInfo 器械维修
     * @return 结果
     */
    @PostMapping
    public R save(RepairRecordInfo repairRecordInfo) throws FebsException {
        return R.ok(repairRecordInfoService.addRepairRecord(repairRecordInfo));
    }

    /**
     * 更新维修记录状态
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/updateRepairStatus")
    public R updateRepairStatus(Integer id) {
        return R.ok(repairRecordInfoService.updateRepairStatus(id));
    }

    /**
     * 修改器械维修
     *
     * @param repairRecordInfo 器械维修
     * @return 结果
     */
    @PutMapping
    public R edit(RepairRecordInfo repairRecordInfo) {
        return R.ok(repairRecordInfoService.updateById(repairRecordInfo));
    }

    /**
     * 删除器械维修
     *
     * @param ids ids
     * @return 器械维修
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(repairRecordInfoService.removeByIds(ids));
    }
}
