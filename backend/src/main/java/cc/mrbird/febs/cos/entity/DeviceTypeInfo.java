package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 器械类型
 *
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceTypeInfo implements Serializable {

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
     * 器械类型名称
     */
    private String name;

    /**
     * 备注
     */
    private String content;

    /**
     * 删除标识（0.未删除 1.已删除）
     */
    private String delFlag;

    /**
     * 图片
     */
    private String images;

    /**
     * 创建时间
     */
    private String createDate;


}
