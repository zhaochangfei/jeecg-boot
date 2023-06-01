package org.jeecg.modules.wms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;

/**
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
public interface IWmsDistributionDetailService extends IService<WmsDistributionDetail> {

    IPage<WmsDistributionDetailVo> getCargoList(Page<WmsDistributionDetailVo> page, String carNo, String status);

    IPage<WmsDistributionDetailVo> getCargoListByIphone(Page<WmsDistributionDetailVo> page, String iphone, String status);
}
