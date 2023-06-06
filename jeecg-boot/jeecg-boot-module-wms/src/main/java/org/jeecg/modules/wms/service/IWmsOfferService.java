package org.jeecg.modules.wms.service;

import com.aliyuncs.exceptions.ClientException;
import org.jeecg.modules.wms.entity.WmsOffer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.wms.vo.WmsOfferVo;

import java.util.List;

/**
 * @Description: 报价表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
public interface IWmsOfferService extends IService<WmsOffer> {

    void updateOfferById(String id, String sstatus) throws ClientException;

    void recallOfferById(String id);

    List<WmsOfferVo> getOfferList(String distributionCode);
}
