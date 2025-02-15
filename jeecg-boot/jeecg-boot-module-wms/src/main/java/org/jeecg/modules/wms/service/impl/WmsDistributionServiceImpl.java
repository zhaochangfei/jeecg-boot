package org.jeecg.modules.wms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.common.util.FillRuleUtil;
import org.jeecg.modules.wms.dto.WmsDistributionDto;
import org.jeecg.modules.wms.entity.WmsCar;
import org.jeecg.modules.wms.entity.WmsConsignee;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecg.modules.wms.mapper.WmsCarMapper;
import org.jeecg.modules.wms.mapper.WmsConsigneeMapper;
import org.jeecg.modules.wms.mapper.WmsDistributionMapper;
import org.jeecg.modules.wms.service.IWmsConsigneeService;
import org.jeecg.modules.wms.service.IWmsDistributionDetailService;
import org.jeecg.modules.wms.service.IWmsDistributionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
@Service
public class WmsDistributionServiceImpl extends ServiceImpl<WmsDistributionMapper, WmsDistribution> implements IWmsDistributionService {

    @Resource
    private IWmsDistributionDetailService detailService;
    @Resource
    private WmsConsigneeMapper consigneeMapper;
    @Resource
    private WmsCarMapper carMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrEdit(WmsDistributionDto wmsDistributionDto) {
        WmsDistribution wmsDistribution = new WmsDistribution();
        BeanUtils.copyProperties(wmsDistributionDto,wmsDistribution);
        if (StringUtils.isEmpty(wmsDistribution.getConsignorId())){
            String norId = findOrAddConsignee(wmsDistributionDto.getConsignorName(), wmsDistributionDto.getConsignorIphone());
            wmsDistribution.setConsignorId(norId);
        }
        if (StringUtils.isEmpty(wmsDistribution.getConsigneeId())){
            String neeId = findOrAddConsignee(wmsDistributionDto.getConsigneeName(), wmsDistributionDto.getConsigneeIphone());
            wmsDistribution.setConsigneeId(neeId);
        }

        //新增配送单
        if (StringUtils.isEmpty(wmsDistribution.getId())){
            wmsDistribution.setCode(FillRuleUtil.executeRule("code_rule", JSONObject.parseObject("{\"prefix\":\"" + "QS" + "\"}")).toString());
        }else {
            wmsDistribution.setCreateTime(null);
        }
        this.saveOrUpdate(wmsDistribution);
        List<WmsDistributionDetail> details = wmsDistributionDto.getDetails();
        ArrayList<String> detailIds = new ArrayList<>();
        if (details!=null && details.size()>0){
            for (WmsDistributionDetail detail : details) {
                if (StringUtils.isEmpty(detail.getId())){
                    detail.setDistributionId(wmsDistribution.getId());
                }else {
                    detail.setCreateTime(null);
                }
                detailService.saveOrUpdate(detail);
                detailIds.add(detail.getId());
            }
        }
        //删除不需要的明细
        QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("distribution_id",wmsDistribution.getId());
        if (detailIds.size()>0){
            wrapper.notIn("id",detailIds);
        }
        detailService.remove(wrapper);
    }

    @Override
    public IPage<WmsDistribution> pageList(Page<WmsDistribution> page, String inputValue, String startTime, String endTime,String sysCode) {
        return baseMapper.pageList(page,inputValue,startTime,endTime,sysCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String id, String status) throws ClientException {
        WmsDistribution wmsDistribution = baseMapper.selectById(id);
        if (status.equals(wmsDistribution.getSstatus())){
            throw new JeecgBootException("已更改请勿重复更改！");
        }
        WmsDistribution wmsDistribution1 = new WmsDistribution();
        wmsDistribution1.setId(id);
        wmsDistribution1.setSstatus(status);
        if ("1".equals(status)) {
            //给货主发短信
            String mobile1 = wmsDistribution.getConsignorIphone();
            WmsConsignee wmsConsignor = consigneeMapper.selectById(wmsDistribution.getConsignorId());
            JSONObject obj1 = new JSONObject();
            obj1.put("name", wmsConsignor.getName());
            obj1.put("code", wmsDistribution.getCode());
            obj1.put("location", wmsDistribution.getOriginatingStation());
            //发货模板
            DySmsHelper.sendSms(mobile1, obj1, DySmsEnum.QS_FH_CODE);
            //给收货人发短信
            String mobile = wmsDistribution.getConsigneeIphone();
            WmsConsignee wmsConsignee = consigneeMapper.selectById(wmsDistribution.getConsigneeId());
            JSONObject obj = new JSONObject();
            obj.put("name", wmsConsignee.getName());
            obj.put("code", wmsDistribution.getCode());
            obj.put("location", wmsDistribution.getOriginatingStation());
            //发货模板
            DySmsHelper.sendSms(mobile, obj, DySmsEnum.QS_FH_CODE);
        }
        baseMapper.updateById(wmsDistribution1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByCode(String code, String status) throws ClientException {
        WmsDistribution wmsDistribution = baseMapper.selectByCode(code);
        WmsDistribution wmsDistribution1 = new WmsDistribution();
        wmsDistribution1.setId(wmsDistribution.getId());
        wmsDistribution1.setSstatus(status);
        //送达  给货主发短信
        if ("2".equals(status)) {
            WmsConsignee wmsConsignor = consigneeMapper.selectById(wmsDistribution.getConsignorId());
            String mobile = wmsDistribution.getConsignorIphone();
            JSONObject obj = new JSONObject();
            obj.put("name", wmsConsignor.getName());
            obj.put("code",wmsDistribution.getCode() );
            //收货模板
            DySmsHelper.sendSms(mobile, obj, DySmsEnum.QS_SH_CODE);
        }
        baseMapper.updateById(wmsDistribution1);
    }

    @Override
    public WmsDistributionDto selectByCode(String code) {
        WmsDistribution wmsDistribution = baseMapper.selectByCode(code);
        WmsDistributionDto wmsDistributionDto = new WmsDistributionDto();
        if (wmsDistribution != null){
            BeanUtils.copyProperties(wmsDistribution,wmsDistributionDto);
            //查询收货人姓名
            if (StringUtils.isNotEmpty(wmsDistribution.getConsigneeId())){
                WmsConsignee wmsConsignee = consigneeMapper.selectById(wmsDistribution.getConsigneeId());
                if (wmsConsignee!= null){
                    wmsDistributionDto.setConsigneeName(wmsConsignee.getName());
                }
            }
            //查询大车车牌号
            if (StringUtils.isNotEmpty(wmsDistribution.getCarId())){
                WmsCar car = carMapper.selectById(wmsDistribution.getCarId());
                if (car!= null){
                    wmsDistributionDto.setLicensePlateNumber(car.getLicensePlateNumber());
                }
            }
            QueryWrapper<WmsDistributionDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("distribution_id",wmsDistribution.getId());
            List<WmsDistributionDetail> wmsDistributionDetails = detailService.list(wrapper);
            wmsDistributionDto.setDetails(wmsDistributionDetails);
        }
        return  wmsDistributionDto;
    }

    @Override
    public IPage<WmsDistribution> deriveList(Page<WmsDistribution> page, String inputValue, String startTime, String endTime,String carIphone) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return baseMapper.deriveList(page,inputValue,startTime,endTime,carIphone,sysUser.getOrgCode());
    }

    @Override
    public IPage<WmsDistribution> consigneeList(Page<WmsDistribution> page, String inputValue, String startTime, String endTime,String consigneeIphone) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        return baseMapper.consigneeList(page,inputValue,startTime,endTime,consigneeIphone,sysUser.getOrgCode());
    }

    @Transactional(rollbackFor = Exception.class)
    public String findOrAddConsignee(String name,String phone){
        //查询是否存在该客户
        QueryWrapper<WmsConsignee> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        wrapper.eq("iphone",phone);
        List<WmsConsignee> wmsConsignees = consigneeMapper.selectList(wrapper);
        if (wmsConsignees.size()>1){
            throw new JeecgBootException("您输入的名字："+name+" 存在多个请点击左边按钮进行选择！");
        }
        if (wmsConsignees==null||wmsConsignees.size()==0){
            //不存在则新增一个
            WmsConsignee wmsConsignee = new WmsConsignee();
            wmsConsignee.setName(name);
            wmsConsignee.setIphone(phone);
            consigneeMapper.insert(wmsConsignee);
            return wmsConsignee.getId();
        }
        return wmsConsignees.get(0).getId();
    }
}
