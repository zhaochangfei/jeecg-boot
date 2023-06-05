package org.jeecg.modules.wms.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.wms.vo.WmsDistributionTransferVo;

/**
 * @Description: 配送单中转表
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
public interface WmsDistributionTransferMapper extends BaseMapper<WmsDistributionTransfer> {

    IPage<WmsDistributionTransferVo> getDistributionTransferList(@Param("page") Page<WmsDistributionTransferVo> page,
                                                                 @Param("transit") String transit,
                                                                 @Param("arrivalStation") String arrivalStation);
}
