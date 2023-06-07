package org.jeecg.modules.wms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.modules.wms.entity.*;
import org.jeecg.modules.wms.mapper.*;
import org.jeecg.modules.wms.service.IWmsOfferService;
import org.jeecg.modules.wms.vo.WmsOfferVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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
    @Resource
    private WmsConsigneeMapper consigneeMapper;
    @Resource
    private WmsDistributionDetailMapper wmsDistributionDetailMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOfferById(String id, String sstatus) throws ClientException {
        WmsOffer wmsOffer = baseMapper.selectById(id);
        QueryWrapper<WmsOffer> wrapper = new QueryWrapper<>();
        wrapper.eq("distribution_id",wmsOffer.getDistributionId());
        wrapper.eq("sstatus","1");
        List<WmsOffer> wmsOffers = baseMapper.selectList(wrapper);
        if (wmsOffers.size()>0){
            throw new JeecgBootException("该配送单已存在同意单据请勿多次同意不同单据！");
        }
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
            //给货主/客户各发送一条信息
            //1 查询 配送单信息
            WmsDistribution wmsDistribution1 = distributionMapper.selectById(wmsOffer.getDistributionId());
            //2 查询 中转单信息
            WmsDistributionTransfer wmsDistributionTransfer1 = distributionTransferMapper.selectById(wmsOffer.getDistributionTransferId());
            //3 给货主发中转短信
            String mobile = wmsDistribution1.getConsignorIphone();
            WmsConsignee wmsConsignor = consigneeMapper.selectById(wmsDistribution1.getConsignorId());
            JSONObject obj = new JSONObject();
            obj.put("name", wmsConsignor.getName());
            obj.put("code", wmsDistribution1.getCode());
            obj.put("location", wmsDistributionTransfer1.getTransit());
            //中转模板
            DySmsHelper.sendSms(mobile, obj, DySmsEnum.QS_ZZ_CODE);
            //3 给客户发中转短信
            String mobile1 = wmsDistribution1.getConsigneeIphone();
            WmsConsignee wmsConsignee = consigneeMapper.selectById(wmsDistribution1.getConsigneeId());
            JSONObject obj1 = new JSONObject();
            obj1.put("name", wmsConsignee.getName());
            obj1.put("code", wmsDistribution1.getCode());
            obj1.put("location", wmsDistributionTransfer1.getTransit());
            //中转模板
            DySmsHelper.sendSms(mobile1, obj1, DySmsEnum.QS_ZZ_CODE);
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

    @Override
    public List<WmsOfferVo> getOfferList(String distributionCode) {
        List<WmsOfferVo> offerList = baseMapper.getOfferList(distributionCode);
        if (offerList.size()>0){
            offerList.forEach(wmsOfferVo -> {
                QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
                wrapper.eq("distribution_id",wmsOfferVo.getDistributionId());
                List<WmsDistributionDetail> wmsDistributionDetails = wmsDistributionDetailMapper.selectList(wrapper);
                wmsOfferVo.setDetails(wmsDistributionDetails);
            });
        }
        return offerList;
    }

    @Override
    public IPage<WmsOfferVo> pageList(Page<WmsOfferVo> page, String consignorId) {
        IPage<WmsOfferVo> wmsOfferVoIPage = baseMapper.pageList(page, consignorId);
        List<WmsOfferVo> offerList = wmsOfferVoIPage.getRecords();
        if (offerList.size()>0){
            offerList.forEach(wmsOfferVo -> {
                QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
                wrapper.eq("distribution_id",wmsOfferVo.getDistributionId());
                List<WmsDistributionDetail> wmsDistributionDetails = wmsDistributionDetailMapper.selectList(wrapper);
                wmsOfferVo.setDetails(wmsDistributionDetails);
            });
        }
        wmsOfferVoIPage.setRecords(offerList);
        return wmsOfferVoIPage;
    }
}
