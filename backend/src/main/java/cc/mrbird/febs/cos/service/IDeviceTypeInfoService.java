package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.DeviceTypeInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IDeviceTypeInfoService extends IService<DeviceTypeInfo> {

    /**
     * 分页获取器械类型
     *
     * @param page           分页对象
     * @param deviceTypeInfo 器械类型
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryDeviceTypePage(Page<DeviceTypeInfo> page, DeviceTypeInfo deviceTypeInfo);
}
