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
 * @Description: app用户表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Data
@TableName("wms_app_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="wms_app_user对象", description="app用户表")
public class WmsAppUser implements Serializable {
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
    private java.lang.String delFlag;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private java.lang.String driver;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String iphone;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
    @ApiModelProperty(value = "车牌号")
    private java.lang.String licensePlateNumber;
	/**车辆类型*/
	@Excel(name = "车辆类型", width = 15)
    @ApiModelProperty(value = "车辆类型")
    private java.lang.String carType;
	/**车牌颜色*/
	@Excel(name = "车牌颜色", width = 15)
    @ApiModelProperty(value = "车牌颜色")
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
	/**城市*/
	@Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private java.lang.String city;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
    private java.lang.String address;
	/**同意忽略状态 0司机  1客户*/
	@Excel(name = "同意忽略状态 0司机  1客户", width = 15)
    @ApiModelProperty(value = "同意忽略状态 0司机  1客户")
    private java.lang.String type;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**账号名称(唯一不可重复)*/
	@Excel(name = "账号名称(唯一不可重复)", width = 15)
    @ApiModelProperty(value = "账号名称(唯一不可重复)")
    private java.lang.String username;
	/**密码*/
	@Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "密码")
    private java.lang.String password;
	/**密码盐*/
	@Excel(name = "密码盐", width = 15)
    @ApiModelProperty(value = "密码盐")
    private java.lang.String salt;
    /**外部Id*/
    @Excel(name = "外部Id", width = 15)
    @ApiModelProperty(value = "外部Id")
    private java.lang.String publicId;
    /**手机Id*/
    @Excel(name = "手机Id", width = 15)
    @ApiModelProperty(value = "手机Id")
    private java.lang.String openId;
}
