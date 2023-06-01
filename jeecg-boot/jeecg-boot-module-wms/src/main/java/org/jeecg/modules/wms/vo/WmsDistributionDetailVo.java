package org.jeecg.modules.wms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;

import java.util.List;

@Data
public class WmsDistributionDetailVo {

    @ApiModelProperty(value = "运单Id")
    private java.lang.String Id;
    @ApiModelProperty(value = "运单号")
    private java.lang.String code;
    @ApiModelProperty(value = "收货人id")
    @Dict(dicCode = "id",dicText = "name",dictTable = "wms_consignee")
    private java.lang.String consigneeId;
    @ApiModelProperty(value = "收货人电话")
    private java.lang.String consigneeIphone;
    @ApiModelProperty(value = "收货地")
    private java.lang.String arrivalStation;
    @ApiModelProperty(value = "件数")
    private java.lang.String number;
    @ApiModelProperty(value = "重量")
    private java.lang.String quantity;
    @ApiModelProperty(value = "配送单状态")
    @Dict(dicCode = "di_sstatus")
    private java.lang.String sstatus;
    @ApiModelProperty(value = "总运费（费用合计）")
    private java.lang.String sumMoney;
    @ApiModelProperty(value = "其他支出")
    private java.math.BigDecimal otherExpenses;
    @ApiModelProperty(value = "应收合计")
    private java.math.BigDecimal receivable;

    @ApiModelProperty(value = "货物明细")
    private List<WmsDistributionDetail> details;


}
