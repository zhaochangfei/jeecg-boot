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
 * @Description: wms_app_versions
 * @Author: jeecg-boot
 * @Date:   2023-06-07
 * @Version: V1.0
 */
@Data
@TableName("wms_app_versions")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_app_versions对象", description="wms_app_versions")
public class WmsAppVersions implements Serializable {
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
	/**版本号*/
	@Excel(name = "版本号", width = 15)
    @ApiModelProperty(value = "版本号")
    private java.lang.String code;
	/**包名*/
	@Excel(name = "包名", width = 15)
    @ApiModelProperty(value = "包名")
    private java.lang.String name;
	/**下载地址*/
	@Excel(name = "下载地址", width = 15)
    @ApiModelProperty(value = "下载地址")
    private java.lang.String location;
	/**接单司机电话*/
	@Excel(name = "接单司机电话", width = 15)
    @ApiModelProperty(value = "接单司机电话")
    private java.lang.String consignorIphone;
	/**中转费用*/
	@Excel(name = "中转费用", width = 15)
    @ApiModelProperty(value = "中转费用")
    private java.math.BigDecimal transferMoney;
	/**是否强制更新 0 否 1是*/
	@Excel(name = "是否强制更新 0 否 1是", width = 15)
    @ApiModelProperty(value = "是否强制更新 0 否 1是")
    private java.lang.String sstatus;
	/**更新说明*/
	@Excel(name = "更新说明", width = 15)
    @ApiModelProperty(value = "更新说明")
    private java.lang.String remark;
	/**是否发布*/
	@Excel(name = "是否发布", width = 15)
    @ApiModelProperty(value = "是否发布")
    private java.lang.String issueIs;
}
