package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.DeviceTypeInfo;
import cc.mrbird.febs.cos.service.IDeviceTypeInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 器械类型
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/device-type-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeviceTypeInfoController {

    private final IDeviceTypeInfoService deviceTypeInfoService;

    /**
     * 分页获取器械类型
     *
     * @param page           分页对象
     * @param deviceTypeInfo 器械类型
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<DeviceTypeInfo> page, DeviceTypeInfo deviceTypeInfo) {
        return R.ok(deviceTypeInfoService.queryDeviceTypePage(page, deviceTypeInfo));
    }

    /**
     * 器械类型详情
     *
     * @param id 器械ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(deviceTypeInfoService.getById(id));
    }

    /**
     * 器械类型列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(deviceTypeInfoService.list());
    }

    /**
     * 新增器械类型
     *
     * @param deviceTypeInfo 器械类型
     * @return 结果
     */
    @PostMapping
    public R save(DeviceTypeInfo deviceTypeInfo) {
        deviceTypeInfo.setCode("DT-" + System.currentTimeMillis());
        deviceTypeInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(deviceTypeInfoService.save(deviceTypeInfo));
    }

    /**
     * 修改器械类型
     *
     * @param deviceTypeInfo 器械类型
     * @return 结果
     */
    @PutMapping
    public R edit(DeviceTypeInfo deviceTypeInfo) {
        return R.ok(deviceTypeInfoService.updateById(deviceTypeInfo));
    }

    /**
     * 删除器械类型
     *
     * @param ids ids
     * @return 器械类型
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(deviceTypeInfoService.removeByIds(ids));
    }
}
