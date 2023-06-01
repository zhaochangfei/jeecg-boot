package org.jeecg.modules.wms.service.impl;

import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import org.jeecg.modules.wms.entity.WmsOffer;
import org.jeecg.modules.wms.mapper.WmsDistributionMapper;
import org.jeecg.modules.wms.mapper.WmsDistributionTransferMapper;
import org.jeecg.modules.wms.mapper.WmsOfferMapper;
import org.jeecg.modules.wms.service.IWmsOfferService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description: 报价表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Service
public class WmsOfferServiceImpl extends ServiceImpl<WmsOfferMapper, WmsOffer> implements IWmsOfferService {

    @Resource
    private WmsDistributionMapper distributionMapper;
    @Resource
    private WmsDistributionTransferMapper distributionTransferMapper;
    @Override
    public void updateOfferById(String id, String sstatus) {
        WmsOffer wmsOffer = baseMapper.selectById(id);
        if (sstatus.equals(wmsOffer.getSstatus())){
            throw new JeecgBootException("请勿重复操作！请刷新列表后在试！");
        }
        //同意
        if ("1".equals(sstatus)){
            //修改配送单数据
            WmsDistribution wmsDistribution = new WmsDistribution();
            wmsDistribution.setId(wmsOffer.getDistributionId());
            wmsDistribution.setSstatus("4");
            wmsDistribution.setTransferMoney(wmsOffer.getTransferMoney());
            wmsDistribution.setCarIdTransfer(wmsOffer.getConsignorId());
            wmsDistribution.setCarDriverTransfer(wmsOffer.getConsignorName());
            wmsDistribution.setCarIphoneTransfer(wmsOffer.getConsignorIphone());
            distributionMapper.updateById(wmsDistribution);
            //修改中转单状态为关闭
            WmsDistributionTransfer wmsDistributionTransfer = new WmsDistributionTransfer();
            wmsDistributionTransfer.setId(wmsOffer.getDistributionTransferId());
            wmsDistributionTransfer.setSstatus("1");
            distributionTransferMapper.updateById(wmsDistributionTransfer);
        }
        WmsOffer wmsOffer1 = new WmsOffer();
        wmsOffer1.setId(id);
        wmsOffer1.setSstatus(sstatus);
        baseMapper.updateById(wmsOffer1);
    }

    @Override
    public void recallOfferById(String id) {
        WmsOffer wmsOffer = baseMapper.selectById(id);
        if ("0".equals(wmsOffer.getSstatus())){
            throw new JeecgBootException("请勿重复操作！请刷新列表后在试！");
        }
        //修改配送单数据
        WmsDistribution wmsDistribution = new WmsDistribution();
        wmsDistribution.setId(wmsOffer.getDistributionId());
        wmsDistribution.setSstatus("3");
        wmsDistribution.setTransferMoney(BigDecimal.ZERO);
        wmsDistribution.setCarIdTransfer("");
        wmsDistribution.setCarDriverTransfer("");
        wmsDistribution.setCarIphoneTransfer("");
        distributionMapper.updateById(wmsDistribution);

        //修改中转单状态为开启
        WmsDistributionTransfer wmsDistributionTransfer = new WmsDistributionTransfer();
        wmsDistributionTransfer.setId(wmsOffer.getDistributionTransferId());
        wmsDistributionTransfer.setSstatus("0");
        distributionTransferMapper.updateById(wmsDistributionTransfer);

        WmsOffer wmsOffer1 = new WmsOffer();
        wmsOffer1.setId(id);
        wmsOffer1.setSstatus("0");
        baseMapper.updateById(wmsOffer1);
    }
}
