package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 信用积分记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CreditRecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 类型（1.加分 2.扣分）
     */
    private String type;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 余额
     */
    private Integer afterScore;

    /**
     * 记录备注
     */
    private String content;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String userName;


}
