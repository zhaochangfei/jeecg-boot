package org.jeecg.modules.wms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.wms.dto.WmsDistributionDto;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecg.modules.wms.mapper.WmsDistributionMapper;
import org.jeecg.modules.wms.service.IWmsDistributionDetailService;
import org.jeecg.modules.wms.service.IWmsDistributionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
@Service
public class WmsDistributionServiceImpl extends ServiceImpl<WmsDistributionMapper, WmsDistribution> implements IWmsDistributionService {

    @Resource
    private IWmsDistributionDetailService detailService;

    @Override
    public void addOrEdit(WmsDistributionDto wmsDistributionDto) {
        WmsDistribution wmsDistribution = new WmsDistribution();
        BeanUtils.copyProperties(wmsDistributionDto,wmsDistribution);
        //新增配送单
        if (StringUtils.isEmpty(wmsDistribution.getId())){
            wmsDistribution.setCode(FillRuleUtil.executeRule("code_rule", JSONObject.parseObject("{\"prefix\":\"" + "FH" + "\"}")).toString());
        }else {
            wmsDistribution.setCreateTime(null);
        }
        this.saveOrUpdate(wmsDistribution);
        List<WmsDistributionDetail> details = wmsDistributionDto.getDetails();
        ArrayList<String> detailIds = new ArrayList<>();
        if (details!=null && details.size()>0){
            for (WmsDistributionDetail detail : details) {
                if (StringUtils.isEmpty(detail.getId())){
                    detail.setDistributionId(wmsDistribution.getId());
                }else {
                    detail.setCreateTime(null);
                }
                detailService.saveOrUpdate(detail);
                detailIds.add(detail.getId());
            }
        }
        //删除不需要的明细
        QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("distribution_id",wmsDistribution.getId());
        if (detailIds.size()>0){
            wrapper.notIn("id",detailIds);
        }
        detailService.remove(wrapper);
    }
}