package org.jeecg.modules.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
public interface IWmsDistributionDetailService extends IService<WmsDistributionDetail> {

    IPage<WmsDistributionDetail> getCargoList(Page<WmsDistributionDetail> page, String carNo, String status);
}
