package org.jeecg.modules.wms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.wms.entity.WmsAppUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: app用户表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
public interface WmsAppUserMapper extends BaseMapper<WmsAppUser> {

    WmsAppUser getUserByName(String username);
}
