package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 租借订单记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RentOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 租借订单编号
     */
    private String code;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 器械ID
     */
    private Integer deviceId;

    /**
     * 开始借用时间
     */
    private String startDate;

    /**
     * 归还时间
     */
    private String endDate;

    /**
     * 租借小时
     */
    private Integer rentHour;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 押金
     */
    private BigDecimal depositPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 状态（0.未支付 1.已支付 2.归还中 3.已完成）
     */
    private String status;

    /**
     * 归还时间
     */
    private String returnDate;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String name;


}
