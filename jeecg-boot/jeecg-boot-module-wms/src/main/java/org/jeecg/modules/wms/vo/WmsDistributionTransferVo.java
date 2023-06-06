package org.jeecg.modules.wms.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.util.List;

@Data
public class WmsDistributionTransferVo {

    /**中转单Id*/
    @ApiModelProperty(value = "中转单Id")
    private String id;
    /**配送单Id*/
    @ApiModelProperty(value = "配送单Id")
    private String distributionId;
    /**配送单编号*/
    @ApiModelProperty(value = "配送单编号")
    private String distributionCode;
    /**中转地*/
    @ApiModelProperty(value = "中转地")
    private String transit;
    @ApiModelProperty(value = "目的地")
    private java.lang.String arrivalStation;
    /**车辆要求*/
    @ApiModelProperty(value = "车辆要求")
    private String requires;
    /**件数*/
    @Excel(name = "件数", width = 15)
    private java.lang.String piece;
    /**重量*/
    @Excel(name = "重量", width = 15)
    private java.lang.Double weight;

    @ApiModelProperty(value = "货物明细")
    private List<WmsDistributionDetail> details;
}
