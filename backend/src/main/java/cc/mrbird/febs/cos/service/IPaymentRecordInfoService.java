package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PaymentRecordInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface IPaymentRecordInfoService extends IService<PaymentRecordInfo> {

    /**
     * 分页获取支付记录
     *
     * @param page              分页对象
     * @param paymentRecordInfo 支付记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryPaymentRecordPage(Page<PaymentRecordInfo> page, PaymentRecordInfo paymentRecordInfo);
}
