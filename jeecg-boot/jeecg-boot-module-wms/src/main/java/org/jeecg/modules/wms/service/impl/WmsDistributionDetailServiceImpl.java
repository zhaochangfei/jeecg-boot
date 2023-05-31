package org.jeecg.modules.wms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecg.modules.wms.mapper.WmsDistributionDetailMapper;
import org.jeecg.modules.wms.service.IWmsDistributionDetailService;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
@Service
public class WmsDistributionDetailServiceImpl extends ServiceImpl<WmsDistributionDetailMapper, WmsDistributionDetail> implements IWmsDistributionDetailService {

    @Override
    public IPage<WmsDistributionDetailVo> getCargoList(Page<WmsDistributionDetailVo> page, String carNo, String status) {
        return baseMapper.getCargoList(page,carNo,status);
    }
}
