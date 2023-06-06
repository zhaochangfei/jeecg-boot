package org.jeecg.modules.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import org.jeecg.modules.wms.mapper.WmsDistributionDetailMapper;
import org.jeecg.modules.wms.mapper.WmsDistributionTransferMapper;
import org.jeecg.modules.wms.service.IWmsDistributionTransferService;
import org.jeecg.modules.wms.vo.WmsDistributionTransferVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 配送单中转表
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Service
public class WmsDistributionTransferServiceImpl extends ServiceImpl<WmsDistributionTransferMapper, WmsDistributionTransfer> implements IWmsDistributionTransferService {

    @Resource
    private WmsDistributionDetailMapper wmsDistributionDetailMapper;

    @Override
    public IPage<WmsDistributionTransferVo> getDistributionTransferList(Page<WmsDistributionTransferVo> page, String transit, String arrivalStation) {
        IPage<WmsDistributionTransferVo> distributionTransferList = baseMapper.getDistributionTransferList(page, transit, arrivalStation);
        List<WmsDistributionTransferVo> records = distributionTransferList.getRecords();
        if (records.size()>0){
            for (WmsDistributionTransferVo record : records) {
                QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
                wrapper.eq("distribution_id",record.getDistributionId());
                List<WmsDistributionDetail> wmsDistributionDetails = wmsDistributionDetailMapper.selectList(wrapper);
                record.setDetails(wmsDistributionDetails);
            }
            distributionTransferList.setRecords(records);
        }
        return distributionTransferList;
    }
}
