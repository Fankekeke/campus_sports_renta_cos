package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.CreditRecordInfo;
import cc.mrbird.febs.cos.dao.CreditRecordInfoMapper;
import cc.mrbird.febs.cos.service.ICreditRecordInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class CreditRecordInfoServiceImpl extends ServiceImpl<CreditRecordInfoMapper, CreditRecordInfo> implements ICreditRecordInfoService {

    /**
     * 分页获取信用积分记录
     *
     * @param page             分页对象
     * @param creditRecordInfo 信用积分记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryCreditPage(Page<CreditRecordInfo> page, CreditRecordInfo creditRecordInfo) {
        return baseMapper.queryCreditPage(page, creditRecordInfo);
    }
}
