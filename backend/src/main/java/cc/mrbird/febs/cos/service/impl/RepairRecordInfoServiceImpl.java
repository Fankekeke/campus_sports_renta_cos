package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.DeviceInfoMapper;
import cc.mrbird.febs.cos.entity.DeviceInfo;
import cc.mrbird.febs.cos.entity.RepairRecordInfo;
import cc.mrbird.febs.cos.dao.RepairRecordInfoMapper;
import cc.mrbird.febs.cos.service.IRepairRecordInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairRecordInfoServiceImpl extends ServiceImpl<RepairRecordInfoMapper, RepairRecordInfo> implements IRepairRecordInfoService {

    private final DeviceInfoMapper deviceInfoMapper;

    /**
     * 分页获取器械维修
     *
     * @param page             分页对象
     * @param repairRecordInfo 器械维修
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryRepairPage(Page<RepairRecordInfo> page, RepairRecordInfo repairRecordInfo) {
        return baseMapper.queryRepairPage(page, repairRecordInfo);
    }

    /**
     * 添加器材维修记录
     *
     * @param repairRecordInfo 维修记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addRepairRecord(RepairRecordInfo repairRecordInfo) throws FebsException {
        // 获取器材信息
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(repairRecordInfo.getDeviceId());
        // 维修单号
        repairRecordInfo.setCode("REP-" + System.currentTimeMillis());
        // 创建时间
        repairRecordInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        repairRecordInfo.setRepairStartDate(DateUtil.formatDateTime(new Date()));
        // 默认维修状态
        repairRecordInfo.setStatus("0");
        // 判断维修结束时间是否小于等于当前时间
        if (DateUtil.compare(DateUtil.parseDateTime(repairRecordInfo.getRepairEndDate()), new Date()) <=0) {
            throw new FebsException("维修结束时间不能小于当前时间");
        }
        // 更新器械状态
        deviceInfo.setStatus("3");
        deviceInfoMapper.updateById(deviceInfo);
        return this.save(repairRecordInfo);
    }

    /**
     * 更新维修记录状态
     *
     * @param id 主键ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRepairStatus(Integer id) {
        // 获取维修记录
        RepairRecordInfo repairRecordInfo = baseMapper.selectById(id);
        // 获取器材信息
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(repairRecordInfo.getDeviceId());
        // 更新设置状态为正常
        // 更新器械状态
        deviceInfo.setStatus("1");
        deviceInfoMapper.updateById(deviceInfo);
        repairRecordInfo.setStatus("1");
        return this.updateById(repairRecordInfo);
    }
}
