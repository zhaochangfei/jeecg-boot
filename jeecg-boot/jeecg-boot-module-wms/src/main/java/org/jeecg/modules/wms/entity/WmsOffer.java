package org.jeecg.modules.wms.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 报价表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Data
@TableName("wms_offer")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_offer对象", description="报价表")
public class WmsOffer implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**逻辑删除标记*/
	@Excel(name = "逻辑删除标记", width = 15)
    @ApiModelProperty(value = "逻辑删除标记")
    @TableLogic
    private java.lang.String delFlag;
	/**单号*/
	@Excel(name = "单号", width = 15)
    @ApiModelProperty(value = "单号")
    private java.lang.String code;
	/**接单司机Id*/
	@Excel(name = "接单司机Id", width = 15)
    @ApiModelProperty(value = "接单司机Id")
    private java.lang.String consignorId;
	/**接单司机名称*/
	@Excel(name = "接单司机名称", width = 15)
    @ApiModelProperty(value = "接单司机名称")
    private java.lang.String consignorName;
	/**接单司机电话*/
	@Excel(name = "接单司机电话", width = 15)
    @ApiModelProperty(value = "接单司机电话")
    private java.lang.String consignorIphone;
	/**中转费用*/
	@Excel(name = "中转费用", width = 15)
    @ApiModelProperty(value = "中转费用")
    private java.math.BigDecimal transferMoney;
	/**同意忽略状态 0待处理 1同意 2忽略*/
	@Excel(name = "同意忽略状态 0待处理 1同意 2忽略", width = 15)
    @ApiModelProperty(value = "同意忽略状态 0待处理 1同意 2忽略")
    private java.lang.String sstatus;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
}
