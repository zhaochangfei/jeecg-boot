package org.jeecg.modules.wms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;

import java.util.List;

@Data
public class WmsDistributionDto extends WmsDistribution {

    @ApiModelProperty(value = "发货明细")
    private List<WmsDistributionDetail> details;
}
