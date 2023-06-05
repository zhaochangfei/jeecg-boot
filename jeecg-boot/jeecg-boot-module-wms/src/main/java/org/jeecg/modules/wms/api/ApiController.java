package org.jeecg.modules.wms.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.log.Log;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.wms.entity.WmsAppUser;
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import org.jeecg.modules.wms.entity.WmsInformation;
import org.jeecg.modules.wms.entity.WmsOffer;
import org.jeecg.modules.wms.service.*;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;
import org.jeecg.modules.wms.vo.WmsDistributionTransferVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @AutoLog(value = "Api-获取监控Token")
    @ApiOperation(value="Api-获取监控Token", notes="Api-获取监控Token")
    @GetMapping(value = "/getMonitoringToken")
    public Result<String> getMonitoringToken(HttpServletRequest req) {
        Log.error("进入getMonitoringToken");
        String Url = "http://202.100.168.28:8080/StandardApiAction_login.action?account=qsll&password=qsll123";
        JSONObject post = RestUtil.post(Url);
        String jsession = post.get("jsession").toString();
        return Result.OK(jsession);
    }

    @AutoLog(value = "Api-通过车牌号获取货物清单")
    @ApiOperation(value="Api-通过车牌号获取货物清单", notes="Api-通过车牌号获取货物清单")
    @GetMapping(value = "/getCargoListByCarNo")
    public Result<IPage<WmsDistributionDetailVo>> getCargoList(@RequestParam(name = "carNo",required = true) String carNo,
                                  @RequestParam(name = "status",required = false) String status,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                       HttpServletRequest req) {
        Page<WmsDistributionDetailVo> page = new Page<WmsDistributionDetailVo>(pageNo, pageSize);
        IPage<WmsDistributionDetailVo> pageList =  detailService.getCargoList(page,carNo,status);
        return Result.OK(pageList);
    }
    @AutoLog(value = "Api-通过收货人/发货人手机号获取货物清单")
    @ApiOperation(value="Api-通过收货人/发货人手机号获取货物清单", notes="Api-通过收货人/发货人手机号获取货物清单")
    @GetMapping(value = "/getCargoListByIphone")
    public Result<IPage<WmsDistributionDetailVo>> getCargoListByIphone(@RequestParam(name = "iphone",required = true) String iphone,
                                                               @RequestParam(name = "status",required = false) String status,
                                                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                               HttpServletRequest req) {
        Page<WmsDistributionDetailVo> page = new Page<WmsDistributionDetailVo>(pageNo, pageSize);
        IPage<WmsDistributionDetailVo> pageList =  detailService.getCargoListByIphone(page,iphone,status);
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
    public Result<List<WmsOffer>> getOfferList(@RequestParam(name = "distributionCode",required = true) String distributionCode,
                                  HttpServletRequest req) {
        QueryWrapper<WmsOffer> wrapper = new QueryWrapper<>();
        wrapper.eq("distribution_code",distributionCode);
        List<WmsOffer> offers =   offerService.list(wrapper);
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
    public Result<IPage<WmsOffer>> getOfferListById(@RequestParam(name = "consignorId",required = true) String consignorId,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                          HttpServletRequest req) {
        try {
            Page<WmsOffer> page = new Page<WmsOffer>(pageNo, pageSize);
            QueryWrapper<WmsOffer> wrapper = new QueryWrapper<>();
            wrapper.eq("consignor_id",consignorId);
            wrapper.orderByDesc("create_time");
            IPage<WmsOffer> iPage = offerService.page(page, wrapper);
            return Result.OK("成功！",iPage);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(e.getMessage(),null);
        }
    }


}
