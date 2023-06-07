package org.jeecg.modules.wms.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wms.entity.WmsOffer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.wms.vo.WmsOfferVo;

/**
 * @Description: 报价表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
public interface WmsOfferMapper extends BaseMapper<WmsOffer> {

    List<WmsOfferVo> getOfferList(String distributionCode);

    IPage<WmsOfferVo> pageList(@Param("page") Page<WmsOfferVo> page,@Param("consignorId") String consignorId);
}
