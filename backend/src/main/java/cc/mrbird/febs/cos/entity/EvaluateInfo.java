package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 租借订单评价
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EvaluateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 评价分数
     */
    private Integer score;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价图片
     */
    private String images;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String deviceName;
    @TableField(exist = false)
    private String userName;


}
