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
 * 器械采购
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseDeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 采购订单编号
     */
    private String code;

    /**
     * 采购人
     */
    private Integer chargePerson;

    /**
     * 供应商ID
     */
    private Integer supplierId;

    /**
     * 采购备注
     */
    private String content;

    /**
     * 采购总价
     */
    private BigDecimal totalPrice;

    /**
     * 采购时间
     */
    private String createDate;

    @TableField(exist = false)
    private String goods;

    @TableField(exist = false)
    private String supplierName;

    @TableField(exist = false)
    private String staffName;
}
