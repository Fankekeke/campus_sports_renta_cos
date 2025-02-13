package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.RepairRecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IRepairRecordInfoService extends IService<RepairRecordInfo> {

    /**
     * 分页获取器械维修
     *
     * @param page             分页对象
     * @param repairRecordInfo 器械维修
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryRepairPage(Page<RepairRecordInfo> page, RepairRecordInfo repairRecordInfo);

    /**
     * 添加器材维修记录
     *
     * @param repairRecordInfo 维修记录
     * @return 结果
     */
    boolean addRepairRecord(RepairRecordInfo repairRecordInfo) throws FebsException;

    /**
     * 更新维修记录状态
     *
     * @param id 主键ID
     * @return 结果
     */
    boolean updateRepairStatus(Integer id);
}
