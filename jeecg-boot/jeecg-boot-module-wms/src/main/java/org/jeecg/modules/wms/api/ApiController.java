package org.jeecg.modules.wms.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.RestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ms/api")
@Slf4j
public class ApiController {

    @AutoLog(value = "Api-获取监控Token")
    @ApiOperation(value="Api-获取监控Token", notes="Api-获取监控Token")
    @GetMapping(value = "/getMonitoringToken")
    public Result<String> getMonitoringToken(HttpServletRequest req) {
        String Url = "http://202.100.168.28:8080/StandardApiAction_login.action?account=qsll&password=qsll123";
        JSONObject post = RestUtil.post(Url);
        String jsession = post.get("jsession").toString();
        return Result.OK(jsession);
    }
}
