package org.jeecg.modules.wms.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wms.entity.WmsDistribution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
public interface WmsDistributionMapper extends BaseMapper<WmsDistribution> {

    IPage<WmsDistribution> pageList(@Param("page") Page<WmsDistribution> page,
                                    @Param("inputValue") String inputValue,
                                    @Param("startTime") String startTime,
                                    @Param("endTime") String endTime);

    WmsDistribution selectByCode(String code);
}
