package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.RentOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
public interface RentOrderInfoMapper extends BaseMapper<RentOrderInfo> {

    /**
     * 分页获取租借订单
     *
     * @param page          分页对象
     * @param rentOrderInfo 租借订单
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryRentOrderPage(Page<RentOrderInfo> page, @Param("rentOrderInfo") RentOrderInfo rentOrderInfo);

    /**
     * 近十天内订单数量统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumDays();

    /**
     * 近十天内订单收益统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderAmountDays();

    /**
     * 本月订单信息
     *
     * @return 结果
     */
    List<RentOrderInfo> selectOrderInfoByMonth();

    /**
     * 本年订单信息
     *
     * @return 结果
     */
    List<RentOrderInfo> selectOrderInfoByYear();
}
