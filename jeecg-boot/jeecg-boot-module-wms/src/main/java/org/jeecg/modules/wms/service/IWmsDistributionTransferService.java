package org.jeecg.modules.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.wms.vo.WmsDistributionTransferVo;

/**
 * @Description: 配送单中转表
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface IWmsDistributionTransferService extends IService<WmsDistributionTransfer> {

    IPage<WmsDistributionTransferVo> getDistributionTransferList(Page<WmsDistributionTransferVo> page, String transit, String arrivalStation);
}
