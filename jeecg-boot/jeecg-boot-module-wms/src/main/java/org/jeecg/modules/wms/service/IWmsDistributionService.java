package org.jeecg.modules.wms.service;

import org.jeecg.modules.wms.dto.WmsDistributionDto;
import org.jeecg.modules.wms.entity.WmsDistribution;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
public interface IWmsDistributionService extends IService<WmsDistribution> {

    void addOrEdit(WmsDistributionDto wmsDistributionDto);
}
