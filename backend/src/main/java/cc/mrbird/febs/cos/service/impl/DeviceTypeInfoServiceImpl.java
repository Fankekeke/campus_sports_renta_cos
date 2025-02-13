package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DeviceTypeInfo;
import cc.mrbird.febs.cos.dao.DeviceTypeInfoMapper;
import cc.mrbird.febs.cos.service.IDeviceTypeInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class DeviceTypeInfoServiceImpl extends ServiceImpl<DeviceTypeInfoMapper, DeviceTypeInfo> implements IDeviceTypeInfoService {

    /**
     * 分页获取器械类型
     *
     * @param page           分页对象
     * @param deviceTypeInfo 器械类型
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryDeviceTypePage(Page<DeviceTypeInfo> page, DeviceTypeInfo deviceTypeInfo) {
        return baseMapper.queryDeviceTypePage(page, deviceTypeInfo);
    }
}
