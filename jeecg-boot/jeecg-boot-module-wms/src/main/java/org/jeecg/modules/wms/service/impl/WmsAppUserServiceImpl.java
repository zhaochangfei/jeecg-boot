package org.jeecg.modules.wms.service.impl;

import com.alibaba.fastjson.JSON;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.wms.entity.WmsAppUser;
import org.jeecg.modules.wms.entity.WmsCar;
import org.jeecg.modules.wms.entity.WmsConsignee;
import org.jeecg.modules.wms.mapper.WmsAppUserMapper;
import org.jeecg.modules.wms.mapper.WmsCarMapper;
import org.jeecg.modules.wms.mapper.WmsConsigneeMapper;
import org.jeecg.modules.wms.service.IWmsAppUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: app用户表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WmsAppUserServiceImpl extends ServiceImpl<WmsAppUserMapper, WmsAppUser> implements IWmsAppUserService {

    @Resource
    protected WmsCarMapper carMapper;
    @Resource
    protected WmsConsigneeMapper consigneeMapper;


    @Override
    public void add(WmsAppUser wmsAppUser) {
        String salt = oConvertUtils.randomGen(8);
        wmsAppUser.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(wmsAppUser.getUsername(), wmsAppUser.getPassword(), salt);
        wmsAppUser.setPassword(passwordEncode);
        //同步到司机/收货人员（客户）
        if ("0".equals(wmsAppUser.getType())){
            //司机
            WmsCar wmsCar = new WmsCar();
            wmsCar.setDriver(wmsAppUser.getDriver());
            wmsCar.setIphone(wmsAppUser.getIphone());
            wmsCar.setLicensePlateNumber(wmsAppUser.getLicensePlateNumber());
            wmsCar.setCarType(wmsAppUser.getCarType());
            wmsCar.setCarColor(wmsAppUser.getCarColor());
            wmsCar.setIdCard(wmsAppUser.getIdCard());
            wmsCar.setRsqas(wmsAppUser.getRsqas());
            wmsCar.setDrivingLicence(wmsAppUser.getDrivingLicence());
            carMapper.insert(wmsCar);
        }
        if ("0".equals(wmsAppUser.getType())){
            //客户
            WmsConsignee wmsConsignee = new WmsConsignee();
            wmsConsignee.setName(wmsAppUser.getDriver());
            wmsConsignee.setIphone(wmsAppUser.getIphone());
            wmsConsignee.setIdcard(wmsAppUser.getIdCard());
            wmsConsignee.setAddress(wmsAppUser.getAddress());
            wmsConsignee.setCity(wmsAppUser.getCity());
            consigneeMapper.insert(wmsConsignee);
        }
        baseMapper.insert(wmsAppUser);
    }

    @Override
    public void editById(WmsAppUser wmsAppUser) {
        //同步到司机/收货人员（客户）
        if ("0".equals(wmsAppUser.getType())){
            //司机
            WmsCar wmsCar = new WmsCar();
            wmsCar.setId(wmsAppUser.getPublicId());
            wmsCar.setDriver(wmsAppUser.getDriver());
            wmsCar.setIphone(wmsAppUser.getIphone());
            wmsCar.setLicensePlateNumber(wmsAppUser.getLicensePlateNumber());
            wmsCar.setCarType(wmsAppUser.getCarType());
            wmsCar.setCarColor(wmsAppUser.getCarColor());
            wmsCar.setIdCard(wmsAppUser.getIdCard());
            wmsCar.setRsqas(wmsAppUser.getRsqas());
            wmsCar.setDrivingLicence(wmsAppUser.getDrivingLicence());
            carMapper.updateById(wmsCar);
        }
        if ("0".equals(wmsAppUser.getType())){
            //客户
            WmsConsignee wmsConsignee = new WmsConsignee();
            wmsConsignee.setId(wmsAppUser.getPublicId());
            wmsConsignee.setName(wmsAppUser.getDriver());
            wmsConsignee.setIphone(wmsAppUser.getIphone());
            wmsConsignee.setIdcard(wmsAppUser.getIdCard());
            wmsConsignee.setAddress(wmsAppUser.getAddress());
            wmsConsignee.setCity(wmsAppUser.getCity());
            consigneeMapper.updateById(wmsConsignee);
        }
        baseMapper.updateById(wmsAppUser);
    }

    @Override
    public void editPassWord(WmsAppUser wmsAppUser) {
        String salt = oConvertUtils.randomGen(8);
        wmsAppUser.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(wmsAppUser.getUsername(), wmsAppUser.getPassword(), salt);
        wmsAppUser.setPassword(passwordEncode);
        baseMapper.updateById(wmsAppUser);
    }

    @Override
    public WmsAppUser getUserByName(String username) {
        return baseMapper.getUserByName(username);
    }
}
