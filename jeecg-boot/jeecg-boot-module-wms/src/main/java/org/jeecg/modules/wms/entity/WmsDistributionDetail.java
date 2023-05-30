package org.jeecg.modules.wms.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
@Data
@TableName("wms_distribution_detail")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_distribution_detail对象", description="配送单明细表")
public class WmsDistributionDetail implements Serializable {
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
	/**单号*/
	@Excel(name = "单号", width = 15)
    @ApiModelProperty(value = "单号")
    private java.lang.String code;
	/**单据日期*/
	@Excel(name = "单据日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "单据日期")
    private java.util.Date billdate;
	/**货物名称*/
	@Excel(name = "货物名称", width = 15)
    @ApiModelProperty(value = "货物名称")
    private java.lang.String goodsName;
	/**配送单Id*/
	@Excel(name = "配送单Id", width = 15)
    @ApiModelProperty(value = "配送单Id")
    private java.lang.String distributionId;
	/**包装*/
	@Excel(name = "包装", width = 15)
    @ApiModelProperty(value = "包装")
    private java.lang.String packaging;
	/**件数*/
	@Excel(name = "件数", width = 15)
    @ApiModelProperty(value = "件数")
    private java.lang.String number;
	/**T/米*/
	@Excel(name = "T/米", width = 15)
    @ApiModelProperty(value = "T/米")
    private java.lang.String quantity;
	/**保价*/
	@Excel(name = "保价", width = 15)
    @ApiModelProperty(value = "保价")
    private java.lang.String supportValue;
	/**保费*/
	@Excel(name = "保费", width = 15)
    @ApiModelProperty(value = "保费")
    private java.lang.String premium;
	/**运费*/
	@Excel(name = "运费", width = 15)
    @ApiModelProperty(value = "运费")
    private java.lang.String freight;
	/**合计*/
	@Excel(name = "合计", width = 15)
    @ApiModelProperty(value = "合计")
    private java.lang.String totalMoney;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
}
