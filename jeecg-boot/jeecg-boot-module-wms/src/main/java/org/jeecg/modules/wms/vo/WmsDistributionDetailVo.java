package org.jeecg.modules.wms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

@Data
public class WmsDistributionDetailVo {

    @ApiModelProperty(value = "运单号")
    private java.lang.String code;
    @ApiModelProperty(value = "收货人id")
    @Dict(dicCode = "id",dicText = "name",dictTable = "wms_consignee")
    private java.lang.String consigneeId;
    @ApiModelProperty(value = "收货人电话")
    private java.lang.String consigneeIphone;
    @ApiModelProperty(value = "收货地")
    private java.lang.String arrivalStation;
    @ApiModelProperty(value = "货物名称")
    private java.lang.String goodsName;
    @ApiModelProperty(value = "运费")
    private java.lang.String freight;
    @ApiModelProperty(value = "件数")
    private java.lang.String number;
    @ApiModelProperty(value = "重量")
    private java.lang.String quantity;
    @ApiModelProperty(value = "配送单状态")
    @Dict(dicCode = "di_sstatus")
    private java.lang.String sstatus;
}
