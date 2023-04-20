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
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
@Data
@TableName("wms_distribution")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_distribution对象", description="配送单")
public class WmsDistribution implements Serializable {
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
	/**起运站*/
	@Excel(name = "起运站", width = 15)
    @ApiModelProperty(value = "起运站")
    private java.lang.String originatingStation;
	/**发货人id*/
	@Excel(name = "发货人id", width = 15)
    @ApiModelProperty(value = "发货人id")
    private java.lang.String consignorId;
	/**发货人电话*/
	@Excel(name = "发货人电话", width = 15)
    @ApiModelProperty(value = "发货人电话")
    private java.lang.String consignorIphone;
	/**到达站*/
	@Excel(name = "到达站", width = 15)
    @ApiModelProperty(value = "到达站")
    private java.lang.String arrivalStation;
	/**收货人id*/
	@Excel(name = "收货人id", width = 15)
    @ApiModelProperty(value = "收货人id")
    private java.lang.String consigneeId;
	/**收货人电话*/
	@Excel(name = "收货人电话", width = 15)
    @ApiModelProperty(value = "收货人电话")
    private java.lang.String consigneeIphone;
	/**现付*/
	@Excel(name = "现付", width = 15)
    @ApiModelProperty(value = "现付")
    private java.lang.String spotPayment;
	/**预付*/
	@Excel(name = "预付", width = 15)
    @ApiModelProperty(value = "预付")
    private java.lang.String prepay;
	/**提付*/
	@Excel(name = "提付", width = 15)
    @ApiModelProperty(value = "提付")
    private java.lang.String withdrawal;
	/**总运费*/
	@Excel(name = "总运费", width = 15)
    @ApiModelProperty(value = "总运费")
    private java.lang.String sumMoney;
	/**经办人*/
	@Excel(name = "经办人", width = 15)
    @ApiModelProperty(value = "经办人")
    private java.lang.String operator;
	/**车辆Id*/
	@Excel(name = "车辆Id", width = 15)
    @ApiModelProperty(value = "车辆Id")
    private java.lang.String carId;
	/**车辆电话*/
	@Excel(name = "车辆电话", width = 15)
    @ApiModelProperty(value = "车辆电话")
    private java.lang.String carIphone;
}
