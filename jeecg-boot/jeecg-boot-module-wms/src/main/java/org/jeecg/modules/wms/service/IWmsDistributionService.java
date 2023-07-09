package org.jeecg.modules.wms.service;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.wms.dto.WmsDistributionDto;
import org.jeecg.modules.wms.entity.WmsDistribution;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
public interface IWmsDistributionService extends IService<WmsDistribution> {

    void addOrEdit(WmsDistributionDto wmsDistributionDto);

    IPage<WmsDistribution> pageList(Page<WmsDistribution> page, String inputValue, String startTime, String endTime);

    void updateStatus(String id, String status) throws ClientException;

    void updateStatusByCode(String code, String status) throws ClientException;

    WmsDistributionDto selectByCode(String code);

    IPage<WmsDistribution> deriveList(Page<WmsDistribution> page, String inputValue, String startTime, String endTime,String carIphone);

    IPage<WmsDistribution> consigneeList(Page<WmsDistribution> page, String inputValue, String startTime, String endTime,String consigneeIphone);
}
