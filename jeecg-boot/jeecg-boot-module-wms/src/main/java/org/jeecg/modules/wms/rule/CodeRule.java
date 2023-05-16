package org.jeecg.modules.wms.rule;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.wms.entity.WmsCodeCount;
import org.jeecg.modules.wms.mapper.WmsCodeCountMapper;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 填值规则Demo：生成配送单号
 * 【测试示例】
 */
public class CodeRule implements IFillRuleHandler {

    @Override
    public Object execute(JSONObject params, JSONObject formData) {
        String prefix = formData.get("prefix").toString();
        WmsCodeCountMapper wmsCodeCountMapper = (WmsCodeCountMapper) SpringContextUtils.getBean("wmsCodeCountMapper");

        SimpleDateFormat format = new SimpleDateFormat("yyMMdd") ;
        String updateDate=format.format(new Date())   ;
        QueryWrapper<WmsCodeCount> queryWrapper=new QueryWrapper<>()  ;
        queryWrapper.eq("code_pre",prefix);
        queryWrapper.eq("update_date",updateDate);
        List<WmsCodeCount> firs=  wmsCodeCountMapper.selectList(queryWrapper);
        WmsCodeCount fir = null;
        int cnum=0;
        if(CollectionUtils.isEmpty(firs)){
            cnum++;
            fir=new WmsCodeCount();
            fir.setCnum(cnum+1);
            fir.setCodePre(prefix);
            fir.setUpdateDate(updateDate);
            wmsCodeCountMapper.insert(fir);

        }else{
            fir = firs.get(0);
            cnum=   fir.getCnum();
            cnum++;
            fir.setCnum(cnum);
            wmsCodeCountMapper.updateById(fir);
        }

        String value = prefix +  updateDate + String.format("%04d", cnum) ;
        return value;
    }

}
