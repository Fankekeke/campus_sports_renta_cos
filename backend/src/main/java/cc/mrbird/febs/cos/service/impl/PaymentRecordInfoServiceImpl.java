package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PaymentRecordInfo;
import cc.mrbird.febs.cos.dao.PaymentRecordInfoMapper;
import cc.mrbird.febs.cos.service.IPaymentRecordInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Service
public class PaymentRecordInfoServiceImpl extends ServiceImpl<PaymentRecordInfoMapper, PaymentRecordInfo> implements IPaymentRecordInfoService {

    /**
     * 分页获取支付记录
     *
     * @param page              分页对象
     * @param paymentRecordInfo 支付记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryPaymentRecordPage(Page<PaymentRecordInfo> page, PaymentRecordInfo paymentRecordInfo) {
        return baseMapper.queryPaymentRecordPage(page, paymentRecordInfo);
    }
}
