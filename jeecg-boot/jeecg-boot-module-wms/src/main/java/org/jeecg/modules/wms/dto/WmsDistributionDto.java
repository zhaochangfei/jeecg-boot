package org.jeecg.modules.wms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.List;

@Data
public class WmsDistributionDto extends WmsDistribution {

    /**收货人名字*/
    @ApiModelProperty(value = "收货人名字")
    private java.lang.String consigneeName;
    /**大车车牌号*/
    @ApiModelProperty(value = "大车车牌号")
    private java.lang.String licensePlateNumber;

    @ApiModelProperty(value = "发货明细")
    private List<WmsDistributionDetail> details;
}
