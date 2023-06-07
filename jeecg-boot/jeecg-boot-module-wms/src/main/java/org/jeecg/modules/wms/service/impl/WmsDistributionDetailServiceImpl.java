package org.jeecg.modules.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecg.modules.wms.mapper.WmsDistributionDetailMapper;
import org.jeecg.modules.wms.service.IWmsDistributionDetailService;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
@Service
public class WmsDistributionDetailServiceImpl extends ServiceImpl<WmsDistributionDetailMapper, WmsDistributionDetail> implements IWmsDistributionDetailService {

    @Override
    public IPage<WmsDistributionDetailVo> getCargoList(Page<WmsDistributionDetailVo> page, String carNo, String status,String transferCarNo) {
        IPage<WmsDistributionDetailVo> cargoList = baseMapper.getCargoList(page, carNo, status,transferCarNo);
        List<WmsDistributionDetailVo> records = cargoList.getRecords();
        if (records != null && records.size()>0){
            records.forEach(record ->{
                QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
                wrapper.eq("distribution_id",record.getId());
                List<WmsDistributionDetail> wmsDistributionDetails = baseMapper.selectList(wrapper);
                record.setDetails(wmsDistributionDetails);
            });
            cargoList.setRecords(records);
        }
        return cargoList;
    }

    @Override
    public IPage<WmsDistributionDetailVo> getCargoListByIphone(Page<WmsDistributionDetailVo> page, String iphone, String status,String consigneeIphone) {
        IPage<WmsDistributionDetailVo> cargoList = baseMapper.getCargoListByIphone(page, iphone, status,consigneeIphone);
        List<WmsDistributionDetailVo> records = cargoList.getRecords();
        if (records != null && records.size()>0){
            records.forEach(record ->{
                QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
                wrapper.eq("distribution_id",record.getId());
                List<WmsDistributionDetail> wmsDistributionDetails = baseMapper.selectList(wrapper);
                record.setDetails(wmsDistributionDetails);
            });
            cargoList.setRecords(records);
        }
        return cargoList;
    }
}
