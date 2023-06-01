package org.jeecg.modules.wms.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;

/**
 * @Description: 配送单明细表
 * @Author: jeecg-boot
 * @Date:   2023-04-20
 * @Version: V1.0
 */
public interface WmsDistributionDetailMapper extends BaseMapper<WmsDistributionDetail> {

    IPage<WmsDistributionDetailVo> getCargoList(@Param("page") Page<WmsDistributionDetailVo> page,
                                                @Param("carNo") String carNo,
                                                @Param("status") String status);

    IPage<WmsDistributionDetailVo> getCargoListByIphone(@Param("page") Page<WmsDistributionDetailVo> page,
                                                        @Param("iphone") String iphone,
                                                        @Param("status") String status);
}
