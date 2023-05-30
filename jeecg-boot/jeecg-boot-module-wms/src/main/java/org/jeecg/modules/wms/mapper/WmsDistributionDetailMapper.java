package org.jeecg.modules.wms.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
public interface WmsDistributionDetailMapper extends BaseMapper<WmsDistributionDetail> {

    IPage<WmsDistributionDetail> getCargoList(@Param("page") Page<WmsDistributionDetail> page,
                                              @Param("carNo") String carNo,
                                              @Param("status") String status);
}
