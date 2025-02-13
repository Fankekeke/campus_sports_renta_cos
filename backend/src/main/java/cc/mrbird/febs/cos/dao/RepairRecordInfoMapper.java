package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RepairRecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface RepairRecordInfoMapper extends BaseMapper<RepairRecordInfo> {

    /**
     * 分页获取器械维修
     *
     * @param page             分页对象
     * @param repairRecordInfo 器械维修
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryRepairPage(Page<RepairRecordInfo> page, @Param("repairRecordInfo") RepairRecordInfo repairRecordInfo);
}
