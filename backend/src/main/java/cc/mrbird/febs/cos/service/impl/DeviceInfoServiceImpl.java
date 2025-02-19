package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DeviceInfo;
import cc.mrbird.febs.cos.dao.DeviceInfoMapper;
import cc.mrbird.febs.cos.service.IDeviceInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements IDeviceInfoService {

    /**
     * 分页获取器械信息
     *
     * @param page       分页对象
     * @param deviceInfo 器械信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryDevicePage(Page<DeviceInfo> page, DeviceInfo deviceInfo) {
        return baseMapper.queryDevicePage(page, deviceInfo);
    }

    /**
     * 获取设备状态信息
     *
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> queryDeviceList() {
        return baseMapper.queryDeviceList();
    }
}
