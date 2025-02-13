package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.DeviceTypeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface DeviceTypeInfoMapper extends BaseMapper<DeviceTypeInfo> {

    /**
     * 分页获取器械类型
     *
     * @param page           分页对象
     * @param deviceTypeInfo 器械类型
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryDeviceTypePage(Page<DeviceTypeInfo> page, @Param("deviceTypeInfo") DeviceTypeInfo deviceTypeInfo);
}
