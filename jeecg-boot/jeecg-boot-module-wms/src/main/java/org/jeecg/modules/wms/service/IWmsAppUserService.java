package org.jeecg.modules.wms.service;

import org.jeecg.modules.wms.entity.WmsAppUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: app用户表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
public interface IWmsAppUserService extends IService<WmsAppUser> {

    void add(WmsAppUser wmsAppUser);

    void editById(WmsAppUser wmsAppUser);

    void editPassWord(WmsAppUser wmsAppUser);

    WmsAppUser getUserByName(String username);
}
