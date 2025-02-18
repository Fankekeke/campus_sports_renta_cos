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
 * 器械维修记录
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RepairRecordInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 维修编号
     */
    private String code;

    /**
     * 维修主题
     */
    private String title;

    /**
     * 器械ID
     */
    private Integer deviceId;

    /**
     * 维修内容
     */
    private String content;

    /**
     * 开始维修时间
     */
    private String repairStartDate;

    /**
     * 维修结束时间
     */
    private String repairEndDate;

    /**
     * 状态（0.未完成 1.已完成）
     */
    private String status;

    /**
     * 维修人
     */
    private Integer staffId;

    /**
     * 创建时间
     */
    private String createDate;

    @TableField(exist = false)
    private String staffName;


}
