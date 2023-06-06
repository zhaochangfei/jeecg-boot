package org.jeecg.modules.wms.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
public class WmsOfferVo {

    @ApiModelProperty(value = "id")
    private java.lang.String id;

    @ApiModelProperty(value = "接单司机Id")
    private java.lang.String consignorId;

    @ApiModelProperty(value = "接单司机名称")
    private java.lang.String consignorName;

    @ApiModelProperty(value = "接单司机电话")
    private java.lang.String consignorIphone;

    @ApiModelProperty(value = "中转费用")
    private java.math.BigDecimal transferMoney;

    @ApiModelProperty(value = "同意忽略状态 0待处理 1同意 2忽略")
    @Dict(dicCode = "offer_sstatus")
    private java.lang.String sstatus;

    @ApiModelProperty(value = "备注")
    private java.lang.String remark;

    @ApiModelProperty(value = "配送单ID")
    private java.lang.String distributionId;

    @ApiModelProperty(value = "配送单编号")
    private java.lang.String distributionCode;

    @ApiModelProperty(value = "配送单中转Id")
    private java.lang.String distributionTransferId;

    @ApiModelProperty(value = "运输时间")
    private java.lang.String transitTime;

    @ApiModelProperty(value = "中转地")
    private String transit;
    @ApiModelProperty(value = "目的地")
    private java.lang.String arrivalStation;
    /**件数*/
    @Excel(name = "件数", width = 15)
    private java.lang.String piece;
    /**重量*/
    @Excel(name = "重量", width = 15)
    private java.lang.Double weight;

    @ApiModelProperty(value = "货物明细")
    private List<WmsDistributionDetail> details;
}
