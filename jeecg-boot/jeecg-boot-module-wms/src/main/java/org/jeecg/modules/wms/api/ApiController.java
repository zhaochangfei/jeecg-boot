package org.jeecg.modules.wms.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.entity.WmsDistributionDetail;
import org.jeecg.modules.wms.service.IWmsDistributionDetailService;
import org.jeecg.modules.wms.service.IWmsDistributionService;
import org.jeecg.modules.wms.vo.WmsDistributionDetailVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/ms/api")
@Slf4j
public class ApiController {

    @Resource
    private IWmsDistributionDetailService detailService;
    @Resource
    private IWmsDistributionService distributionService;

    @AutoLog(value = "Api-获取监控Token")
    @ApiOperation(value="Api-获取监控Token", notes="Api-获取监控Token")
    @GetMapping(value = "/getMonitoringToken")
    public Result<String> getMonitoringToken(HttpServletRequest req) {
        String Url = "http://202.100.168.28:8080/StandardApiAction_login.action?account=qsll&password=qsll123";
        JSONObject post = RestUtil.post(Url);
        String jsession = post.get("jsession").toString();
        return Result.OK(jsession);
    }

    @AutoLog(value = "Api-获取货物清单")
    @ApiOperation(value="Api-获取货物清单", notes="Api-获取货物清单")
    @GetMapping(value = "/getCargoList ")
    public Result<?> getCargoList(@RequestParam(name = "carNo",required = true) String carNo,
                                  @RequestParam(name = "status",required = true) String status,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                       HttpServletRequest req) {
        Page<WmsDistributionDetailVo> page = new Page<WmsDistributionDetailVo>(pageNo, pageSize);
        IPage<WmsDistributionDetailVo> pageList =  detailService.getCargoList(page,carNo,status);
        return Result.OK(pageList);
    }
//    @AutoLog(value = "Api-修改运单状态")
//    @ApiOperation(value="Api-修改运单状态", notes="Api-修改运单状态")
//    @GetMapping(value = "/updateStatus ")
//    public Result<?> updateStatus(@RequestParam(name = "code",required = true) String code,
//                                  @RequestParam(name = "status",required = true) String status,
//                                  HttpServletRequest req) {
//        distributionService;
//        return Result.OK(pageList);
//    }


}
