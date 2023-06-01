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
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
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
    /**配送单状态*/
    @Excel(name = "配送单状态", width = 15,dicCode = "di_sstatus")
    @ApiModelProperty(value = "配送单状态")
    @Dict(dicCode = "di_sstatus")
    private java.lang.String sstatus;
	/**起运站*/
	@Excel(name = "起运站", width = 15)
    @ApiModelProperty(value = "起运站")
    private java.lang.String originatingStation;
	/**发货人id*/
	@Excel(name = "发货人", width = 15,dicCode = "id",dicText = "name",dictTable = "wms_consignee")
    @ApiModelProperty(value = "发货人id")
    @Dict(dicCode = "id",dicText = "name",dictTable = "wms_consignee")
    private java.lang.String consignorId;
	/**发货人身份证号*/
//	@Excel(name = "发货人身份证号", width = 15)
    @ApiModelProperty(value = "发货人身份证号")
    private java.lang.String consignorIdcard;
	/**发货人电话*/
//	@Excel(name = "发货人电话", width = 15)
    @ApiModelProperty(value = "发货人电话")
    private java.lang.String consignorIphone;
	/**到达站*/
	@Excel(name = "到达站", width = 15)
    @ApiModelProperty(value = "到达站")
    private java.lang.String arrivalStation;
	/**收货人id*/
	@Excel(name = "收货人", width = 15,dicCode = "id",dicText = "name",dictTable = "wms_consignee")
    @ApiModelProperty(value = "收货人id")
    @Dict(dicCode = "id",dicText = "name",dictTable = "wms_consignee")
    private java.lang.String consigneeId;
	/**收货人电话*/
//	@Excel(name = "收货人电话", width = 15)
    @ApiModelProperty(value = "收货人电话")
    private java.lang.String consigneeIphone;
	/**现付*/
//	@Excel(name = "现付", width = 15)
    @ApiModelProperty(value = "现付")
    private java.math.BigDecimal spotPayment;
	/**预付（代收货款）*/
//	@Excel(name = "预付（代收货款）", width = 15)
    @ApiModelProperty(value = "预付（代收货款）")
    private java.math.BigDecimal prepay;
	/**提付*/
//	@Excel(name = "提付", width = 15)
    @ApiModelProperty(value = "提付")
    private java.math.BigDecimal withdrawal;
	/**总运费（费用合计）*/
	@Excel(name = "总运费", width = 15)
    @ApiModelProperty(value = "总运费（费用合计）")
    private java.lang.String sumMoney;
	/**经办人*/
	@Excel(name = "经办人", width = 15)
    @ApiModelProperty(value = "经办人")
    private java.lang.String operator;
	/**车辆Id*/
	@Excel(name = "车辆Id", width = 15,dicCode = "id",dicText = "license_plate_number",dictTable = "wms_car")
    @ApiModelProperty(value = "车辆Id")
    @Dict(dicCode = "id",dicText = "license_plate_number",dictTable = "wms_car")
    private java.lang.String carId;
	/**车辆电话*/
//	@Excel(name = "车辆电话", width = 15)
    @ApiModelProperty(value = "车辆电话")
    private java.lang.String carIphone;
    /**车辆司机*/
//    @Excel(name = "车辆司机", width = 15)
    @ApiModelProperty(value = "车辆司机")
    private java.lang.String carDriver;
//    @Excel(name = "中转车辆Id", width = 15)
    @ApiModelProperty(value = "中转车辆Id")
    @Dict(dicCode = "id",dicText = "license_plate_number",dictTable = "wms_car")
    private java.lang.String carIdTransfer;
    /**车辆电话*/
//    @Excel(name = "中转车辆电话", width = 15)
    @ApiModelProperty(value = "中转车辆电话")
    private java.lang.String carIphoneTransfer;
    /**车辆司机*/
//    @Excel(name = "中转车辆司机", width = 15)
    @ApiModelProperty(value = "中转车辆司机")
    private java.lang.String carDriverTransfer;
	/**逻辑删除标记*/
//	@Excel(name = "逻辑删除标记", width = 15)
    @ApiModelProperty(value = "逻辑删除标记")
    @TableLogic
    private java.lang.String delFlag;
    /**件数*/
    @Excel(name = "件数", width = 15)
    @ApiModelProperty(value = "件数")
    private java.lang.String piece;
	/**重量*/
	@Excel(name = "重量", width = 15)
    @ApiModelProperty(value = "重量")
    private java.lang.Double weight;
	/**是否中转 0否 1是*/
//	@Excel(name = "是否中转 0否 1是", width = 15)
    @ApiModelProperty(value = "是否中转 0否 1是")
    @Dict(dicCode = "transferIs")
    private java.lang.String transferIs;
	/**中转费*/
//	@Excel(name = "中转费", width = 15)
    @ApiModelProperty(value = "中转费")
    private java.math.BigDecimal transferMoney;
	/**保费*/
//	@Excel(name = "保费", width = 15)
    @ApiModelProperty(value = "保费")
    private java.math.BigDecimal premium;
	/**倒车费*/
//	@Excel(name = "倒车费", width = 15)
    @ApiModelProperty(value = "倒车费")
    private java.math.BigDecimal backFare;
	/**送货费*/
//	@Excel(name = "送货费", width = 15)
    @ApiModelProperty(value = "送货费")
    private java.math.BigDecimal deliveryExpense;
	/**接货费*/
//	@Excel(name = "接货费", width = 15)
    @ApiModelProperty(value = "接货费")
    private java.math.BigDecimal pickUpCharge;
	/**其他支出*/
//	@Excel(name = "其他支出", width = 15)
    @ApiModelProperty(value = "其他支出")
    private java.math.BigDecimal otherExpenses;
	/**应收合计*/
//	@Excel(name = "应收合计", width = 15)
    @ApiModelProperty(value = "应收合计")
    private java.math.BigDecimal receivable;
	/**运输类型*/
	@Excel(name = "运输类型", width = 15,dicCode = "di_type")
    @ApiModelProperty(value = "运输类型")
    @Dict(dicCode = "di_type")
    private java.lang.String type;
	/**送货方式*/
	@Excel(name = "送货方式", width = 15,dicCode = "shippingMethod")
    @ApiModelProperty(value = "送货方式")
    @Dict(dicCode = "shippingMethod")
    private java.lang.String shippingMethod;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**开票员*/
//	@Excel(name = "开票员", width = 15)
    @ApiModelProperty(value = "开票员")
    private java.lang.String drawer;
	/**业务员*/
//	@Excel(name = "业务员", width = 15)
    @ApiModelProperty(value = "业务员")
    private java.lang.String salesman;
	/**关联单号*/
//	@Excel(name = "关联单号", width = 15)
    @ApiModelProperty(value = "关联单号")
    private java.lang.String publicCode;

}
