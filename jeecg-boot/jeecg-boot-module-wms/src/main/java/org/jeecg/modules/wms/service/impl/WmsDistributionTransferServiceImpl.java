package org.jeecg.modules.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import org.jeecg.modules.wms.mapper.WmsDistributionTransferMapper;
import org.jeecg.modules.wms.service.IWmsDistributionTransferService;
import org.jeecg.modules.wms.vo.WmsDistributionTransferVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 配送单中转表
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Service
public class WmsDistributionTransferServiceImpl extends ServiceImpl<WmsDistributionTransferMapper, WmsDistributionTransfer> implements IWmsDistributionTransferService {

    @Override
    public IPage<WmsDistributionTransferVo> getDistributionTransferList(Page<WmsDistributionTransferVo> page, String transit, String arrivalStation) {
        return baseMapper.getDistributionTransferList(page,transit,arrivalStation);
    }
}
