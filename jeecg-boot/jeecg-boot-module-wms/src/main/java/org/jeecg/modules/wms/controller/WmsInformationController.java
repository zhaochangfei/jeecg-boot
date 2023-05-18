package org.jeecg.modules.wms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.wms.entity.WmsInformation;
import org.jeecg.modules.wms.service.IWmsInformationService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 信息录入表
 * @Author: jeecg-boot
 * @Date:   2023-05-18
 * @Version: V1.0
 */
@Api(tags="信息录入表")
@RestController
@RequestMapping("/wms/wmsInformation")
@Slf4j
public class WmsInformationController extends JeecgController<WmsInformation, IWmsInformationService> {
	@Autowired
	private IWmsInformationService wmsInformationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param wmsInformation
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "信息录入表-分页列表查询")
	@ApiOperation(value="信息录入表-分页列表查询", notes="信息录入表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsInformation wmsInformation,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsInformation> queryWrapper = QueryGenerator.initQueryWrapper(wmsInformation, req.getParameterMap());
		Page<WmsInformation> page = new Page<WmsInformation>(pageNo, pageSize);
		IPage<WmsInformation> pageList = wmsInformationService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param wmsInformation
	 * @return
	 */
	@AutoLog(value = "信息录入表-添加")
	@ApiOperation(value="信息录入表-添加", notes="信息录入表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsInformation wmsInformation) {
		wmsInformationService.save(wmsInformation);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param wmsInformation
	 * @return
	 */
	@AutoLog(value = "信息录入表-编辑")
	@ApiOperation(value="信息录入表-编辑", notes="信息录入表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsInformation wmsInformation) {
		wmsInformationService.updateById(wmsInformation);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "信息录入表-通过id删除")
	@ApiOperation(value="信息录入表-通过id删除", notes="信息录入表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsInformationService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "信息录入表-批量删除")
	@ApiOperation(value="信息录入表-批量删除", notes="信息录入表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsInformationService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "信息录入表-通过id查询")
	@ApiOperation(value="信息录入表-通过id查询", notes="信息录入表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsInformation wmsInformation = wmsInformationService.getById(id);
		if(wmsInformation==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsInformation);
	}

	 /**
	  * 通过id受理
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "信息录入表-通过id受理")
	 @ApiOperation(value="信息录入表-通过id受理", notes="信息录入表-通过id受理")
	 @GetMapping(value = "/changeStatusById")
	 public Result<?> changeStatusById(@RequestParam(name="id",required=true) String id) {
		 WmsInformation wmsInformation = wmsInformationService.getById(id);
		 if ("1".equals(wmsInformation.getSstatus())){
			 return Result.error("已受理请勿重复受理！");
		 }else {
			 WmsInformation information = new WmsInformation();
			 information.setId(id);
			 information.setSstatus("1");
			 wmsInformationService.updateById(information);
		 }
		 return Result.OK("受理成功！");
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param wmsInformation
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsInformation wmsInformation) {
        return super.exportXls(request, wmsInformation, WmsInformation.class, "信息录入表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WmsInformation.class);
    }

}
