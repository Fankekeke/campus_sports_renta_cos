package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CreditRecordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface CreditRecordInfoMapper extends BaseMapper<CreditRecordInfo> {

    /**
     * 分页获取信用积分记录
     *
     * @param page             分页对象
     * @param creditRecordInfo 信用积分记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryCreditPage(Page<CreditRecordInfo> page, @Param("creditRecordInfo") CreditRecordInfo creditRecordInfo);
}
