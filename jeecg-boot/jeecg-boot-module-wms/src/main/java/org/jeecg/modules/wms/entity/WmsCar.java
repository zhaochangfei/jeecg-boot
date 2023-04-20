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
 * @Description: wms_car
 * @Author: jeecg-boot
 * @Date:   2023-04-08
 * @Version: V1.0
 */
@Data
@TableName("wms_car")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_car对象", description="wms_car")
public class WmsCar implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
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
	/**驾驶员*/
	@Excel(name = "驾驶员", width = 15)
    @ApiModelProperty(value = "驾驶员")
    private java.lang.String driver;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String iphone;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
    @ApiModelProperty(value = "车牌号")
    private java.lang.String licensePlateNumber;
	/**司机状态*/
	@Excel(name = "司机状态", width = 15,dicCode = "car_sstatus")
    @ApiModelProperty(value = "司机状态")
    @Dict(dicCode = "car_sstatus")
    private java.lang.String sstatus;
	/**车辆类型*/
	@Excel(name = "车辆类型", width = 15,dicCode = "car_type")
    @ApiModelProperty(value = "车辆类型")
    @Dict(dicCode = "car_type")
    private java.lang.String carType;
	/**车牌颜色*/
	@Excel(name = "车牌颜色", width = 15,dicCode = "car_color")
    @ApiModelProperty(value = "车牌颜色")
    @Dict(dicCode = "car_color")
    private java.lang.String carColor;
	/**身份证号码*/
	@Excel(name = "身份证号码", width = 15)
    @ApiModelProperty(value = "身份证号码")
    private java.lang.String idCard;
	/**道路运输证号码*/
	@Excel(name = "道路运输证号码", width = 15)
    @ApiModelProperty(value = "道路运输证号码")
    private java.lang.String rsqas;
	/**驾驶证号*/
	@Excel(name = "驾驶证号", width = 15)
    @ApiModelProperty(value = "驾驶证号")
    private java.lang.String drivingLicence;
	/**车辆状态*/
	@Excel(name = "车辆状态", width = 15,dicCode = "car_status")
    @ApiModelProperty(value = "车辆状态")
    @Dict(dicCode = "car_status")
    private java.lang.String carStatus;
	/**开户行*/
	@Excel(name = "开户行", width = 15)
    @ApiModelProperty(value = "开户行")
    private java.lang.String openingBank;
	/**银行卡号*/
	@Excel(name = "银行卡号", width = 15)
    @ApiModelProperty(value = "银行卡号")
    private java.lang.String bankCard;
	/**是否绑定GPS*/
	@Excel(name = "是否绑定GPS", width = 15,dicCode = "gps")
    @ApiModelProperty(value = "是否绑定GPS")
    @Dict(dicCode = "gps")
    private java.lang.String gps;
}
