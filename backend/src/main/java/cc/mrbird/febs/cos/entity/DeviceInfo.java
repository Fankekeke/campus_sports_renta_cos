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
 * 器械管理
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 器械编号
     */
    private String code;

    /**
     * 器械名称
     */
    private String name;

    /**
     * 器械类型
     */
    private Integer typeId;

    /**
     * 图片
     */
    private String images;

    /**
     * 器械备注
     */
    private String content;

    /**
     * 型号
     */
    private String model;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 押金
     */
    private BigDecimal depositPrice;

    /**
     * 单价/小时
     */
    private BigDecimal unitPrice;

    /**
     * 负责人
     */
    private Integer chargePerson;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 状态（1.正常 2.租用中 3.维保中）
     */
    private String status;

    /**
     * 上下架状态（0.下架 1.上架）
     */
    private String outFlag;

    /**
     * 所属供应商
     */
    private Integer supplierId;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String typeName;


}
