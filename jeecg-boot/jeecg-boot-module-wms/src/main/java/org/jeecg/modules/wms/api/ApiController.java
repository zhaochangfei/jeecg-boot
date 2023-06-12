package org.jeecg.modules.wms.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonArray;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.wms.dto.WmsDistributionDto;
import org.jeecg.modules.wms.entity.*;
import org.jeecg.modules.wms.service.*;
import org.jeecg.modules.wms.util.MD5Utils;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;
import org.jeecg.modules.wms.vo.WmsDistributionTransferVo;
import org.jeecg.modules.wms.vo.WmsOfferVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/wms/api")
@Slf4j
public class ApiController {

    @Resource
    private IWmsDistributionDetailService detailService;
    @Resource
    private IWmsDistributionService distributionService;
    @Resource
    private IWmsDistributionTransferService distributionTransferService;
    @Resource
    private IWmsOfferService offerService;
    @Resource
    private IWmsAppUserService appUserService;
    @Resource
    private IWmsInformationService informationService;
    @Resource
    private IWmsAppVersionsService appVersionsService;

    @AutoLog(value = "Api-根据车牌号获取行车位置")
    @ApiOperation(value="Api-根据车牌号获取行车位置", notes="Api-根据车牌号获取行车位置")
    @GetMapping(value = "/getDrivingPosition")
    public Result<String> getMonitoringToken(@RequestParam(name = "carNo",required = true) String carNo,
                                             HttpServletRequest req) {

        String appkey="d6c7c508-3e51-483f-ba41-193e7b22fccc";
        String appsecret= "6b96ee9d-0ca9-41b9-aee1-37c02a80b20b";
        String format= "json";
        String method="GetVehcileInfo";
        String sessionId="";
        String timestamp=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String vehicleNo= carNo;
        String contactStr = appsecret + "appkey" + appkey + "format" + format  + "method" +method + "sessionId" + sessionId + "timestamp" + timestamp + "vehicleNo" +vehicleNo + appsecret;
        String sign = MD5Utils.getMD5UpperString(contactStr);

        String Url = "https://api.e6yun.com/public/v4/MONITOR/api/vehicleMonitor/getVehicleInfo?"+
                "method=GetVehcileInfo" +
                "&timestamp=" +timestamp+
                "&format=json" +
                "&vehicleNo=" +carNo+
                "&sessionId=" +
                "&appkey=" +appkey+
                "&sign="+sign;
        JSONObject object = RestUtil.get(Url);
        ArrayList<LinkedHashMap> data = (ArrayList<LinkedHashMap>) object.get("data");
        String placeName = data.get(0).get("placeName").toString();
        String roadName = data.get(0).get("roadName").toString();

        return Result.OK(placeName+roadName);
    }

    @AutoLog(value = "Api-通过车牌号获取货物清单")
    @ApiOperation(value="Api-通过车牌号获取货物清单", notes="Api-通过车牌号获取货物清单")
    @GetMapping(value = "/getCargoListByCarNo")
    public Result<IPage<WmsDistributionDetailVo>> getCargoList(@RequestParam(name = "carNo",required = false) String carNo,
                                                               @RequestParam(name = "transferCarNo",required = false) String transferCarNo,
                                  @RequestParam(name = "status",required = false) String status,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                       HttpServletRequest req) {
        Page<WmsDistributionDetailVo> page = new Page<WmsDistributionDetailVo>(pageNo, pageSize);
        IPage<WmsDistributionDetailVo> pageList =  detailService.getCargoList(page,carNo,status,transferCarNo);
        return Result.OK(pageList);
    }
    @AutoLog(value = "Api-通过配送单号查询详情")
    @ApiOperation(value="Api-通过配送单号查询详情", notes="Api-通过配送单号查询详情")
    @GetMapping(value = "/getWmsDistributionByCode")
    public Result<WmsDistributionDto> getWmsDistributionByCode(@RequestParam(name = "code",required = true) String code,
                                                               HttpServletRequest req) {
        WmsDistributionDto wmsDistribution = distributionService.selectByCode(code);
        return Result.OK(wmsDistribution);
    }
    @AutoLog(value = "Api-通过收货人/发货人手机号获取货物清单")
    @ApiOperation(value="Api-通过收货人/发货人手机号获取货物清单", notes="Api-通过收货人/发货人手机号获取货物清单")
    @GetMapping(value = "/getCargoListByIphone")
    public Result<IPage<WmsDistributionDetailVo>> getCargoListByIphone(@RequestParam(name = "iphone",required = false) String iphone,
                                                               @RequestParam(name = "consigneeIphone",required = false) String consigneeIphone,
                                                               @RequestParam(name = "status",required = false) String status,
                                                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                               HttpServletRequest req) {
        Page<WmsDistributionDetailVo> page = new Page<WmsDistributionDetailVo>(pageNo, pageSize);
        IPage<WmsDistributionDetailVo> pageList =  detailService.getCargoListByIphone(page,iphone,status,consigneeIphone);
        return Result.OK(pageList);
    }
    @AutoLog(value = "Api-通过单号修改运单状态")
    @ApiOperation(value="Api-通过单号修改运单状态", notes="Api-通过单号修改运单状态")
    @GetMapping(value = "/updateStatus")
    public Result<?> updateStatus(@RequestParam(name = "code",required = true) String code,
                                  @RequestParam(name = "status",required = true) String status,
                                  HttpServletRequest req) {
        try {
            distributionService.updateStatusByCode(code,status);
            return Result.OK("修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    /**
     *   发布订单中转信息
     *
     * @param wmsDistributionTransfer
     * @return
     */
    @AutoLog(value = "Api-发布订单中转信息")
    @ApiOperation(value="Api-发布订单中转信息", notes="Api-发布订单中转信息")
    @PostMapping(value = "/addDistributionTransfer")
    public Result<?> addDistributionTransfer(@RequestBody WmsDistributionTransfer wmsDistributionTransfer) {
        try {
            distributionTransferService.save(wmsDistributionTransfer);
            return Result.OK("发布成功！");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @AutoLog(value = "Api-通过配送单号获取报价集合")
    @ApiOperation(value="Api-通过配送单号获取报价集合", notes="Api-通过配送单号获取报价集合")
    @GetMapping(value = "/getOfferList")
    public Result<List<WmsOfferVo>> getOfferList(@RequestParam(name = "distributionCode",required = true) String distributionCode,
                                                 HttpServletRequest req) {
        List<WmsOfferVo> offers =  offerService.getOfferList(distributionCode);
        return Result.OK(offers);
    }

    @AutoLog(value = "Api-同意/忽略报价")
    @ApiOperation(value="Api-同意/忽略报价", notes="Api-同意/忽略报价")
    @GetMapping(value = "/updateOfferById")
    public Result<?> updateOfferById(@RequestParam(name = "id",required = true) String id,
                                     @RequestParam(name = "sstatus",required = true) String sstatus,
                                  HttpServletRequest req) {
        try {
            offerService.updateOfferById(id,sstatus);
            return Result.OK("成功！");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }
    @AutoLog(value = "Api-撤回报价")
    @ApiOperation(value="Api-撤回报价", notes="Api-撤回报价")
    @GetMapping(value = "/recallOfferById")
    public Result<?> recallOfferById(@RequestParam(name = "id",required = true) String id,
                                     HttpServletRequest req) {
        try {
            offerService.recallOfferById(id);
            return Result.OK("成功！");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }

    @AutoLog(value = "Api-获取用户信息")
    @ApiOperation(value="Api-获取用户信息", notes="Api-获取用户信息")
    @GetMapping(value = "/getUserById")
    public Result<WmsAppUser> getUserById(@RequestParam(name = "id",required = true) String id,
                                     HttpServletRequest req) {
        try {
            WmsAppUser appUser = appUserService.getById(id);
            return Result.OK("成功！",appUser);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage(),null);
        }
    }

    /**
     *   添加
     *
     * @param wmsInformation
     * @return
     */
    @AutoLog(value = "Api-信息录入")
    @ApiOperation(value="Api-信息录入", notes="Api-信息录入")
    @PostMapping(value = "/wmsInformationAdd")
    public Result<?> add(@RequestBody WmsInformation wmsInformation) {
        informationService.save(wmsInformation);
        return Result.OK("添加成功！");
    }
    /**
     *   添加
     *
     * @param wmsOffer
     * @return
     */
    @AutoLog(value = "Api-新增报价")
    @ApiOperation(value="Api-新增报价", notes="Api-新增报价")
    @PostMapping(value = "/offerAdd")
    public Result<?> offerAdd(@RequestBody WmsOffer wmsOffer) {
        offerService.save(wmsOffer);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "Api-待中转开启报价的运单列表")
    @ApiOperation(value="Api-待中转开启报价的运单列表", notes="Api-待中转开启报价的运单列表")
    @GetMapping(value = "/getDistributionTransferList")
    public Result<IPage<WmsDistributionTransferVo>> getDistributionTransferList(@RequestParam(name = "transit",required = false) String transit,
                                                                            @RequestParam(name = "arrivalStation",required = false) String arrivalStation,
                                                                            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                                            HttpServletRequest req) {
        Page<WmsDistributionTransferVo> page = new Page<WmsDistributionTransferVo>(pageNo, pageSize);
        IPage<WmsDistributionTransferVo> pageList =  distributionTransferService.getDistributionTransferList(page,transit,arrivalStation);
        return Result.OK(pageList);
    }

    @AutoLog(value = "Api-中转司机查看自己报价列表")
    @ApiOperation(value="Api-中转司机查看自己报价列表", notes="Api-中转司机查看自己报价列表")
    @GetMapping(value = "/getOfferListById")
    public Result<IPage<WmsOfferVo>> getOfferListById(@RequestParam(name = "consignorId",required = true) String consignorId,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                          HttpServletRequest req) {
        try {
            Page<WmsOfferVo> page = new Page<WmsOfferVo>(pageNo, pageSize);
            IPage<WmsOfferVo> iPage = offerService.pageList(page, consignorId);
            return Result.OK("成功！",iPage);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage(),null);
        }
    }
    @AutoLog(value = "Api-根据客户手机号查询客户录入信息列表")
    @ApiOperation(value="Api-根据客户手机号查询客户录入信息列表", notes="Api-根据客户手机号查询客户录入信息列表")
    @GetMapping(value = "/getWmsInformationListByIphone")
    public Result<IPage<WmsInformation>> getWmsInformationListByIphone(@RequestParam(name = "iphone",required = true) String iphone,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                    HttpServletRequest req) {
        try {
            Page<WmsInformation> page = new Page<WmsInformation>(pageNo, pageSize);
            QueryWrapper<WmsInformation> wrapper = new QueryWrapper<>();
            wrapper.eq("iphone",iphone);
            wrapper.orderByDesc("create_time");
            IPage<WmsInformation> iPage = informationService.page(page, wrapper);
            return Result.OK("成功！",iPage);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage(),null);
        }
    }
    @AutoLog(value = "Api-通过包名查询App版本信息")
    @ApiOperation(value="Api-通过包名查询App版本信息", notes="Api-通过包名查询App版本信息")
    @GetMapping(value = "/findAppVersionByname")
    public Result<WmsAppVersions> findAppVersionByname(@RequestParam(name = "name",required = true) String name,
                                  HttpServletRequest req) {
        try {
            QueryWrapper<WmsAppVersions> wrapper = new QueryWrapper<>();
            wrapper.eq("name",name);
            WmsAppVersions one = appVersionsService.getOne(wrapper);
            return Result.OK(one);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage(),null);
        }
    }



}
