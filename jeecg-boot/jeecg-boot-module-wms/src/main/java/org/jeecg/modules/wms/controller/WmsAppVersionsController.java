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
import org.jeecg.modules.wms.entity.WmsAppVersions;
import org.jeecg.modules.wms.service.IWmsAppVersionsService;

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
 * @Description: wms_app_versions
 * @Author: jeecg-boot
 * @Date:   2023-06-07
 * @Version: V1.0
 */
@Api(tags="wms_app_versions")
@RestController
@RequestMapping("/wms/wmsAppVersions")
@Slf4j
public class WmsAppVersionsController extends JeecgController<WmsAppVersions, IWmsAppVersionsService> {
	@Autowired
	private IWmsAppVersionsService wmsAppVersionsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param wmsAppVersions
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "wms_app_versions-分页列表查询")
	@ApiOperation(value="wms_app_versions-分页列表查询", notes="wms_app_versions-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsAppVersions wmsAppVersions,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsAppVersions> queryWrapper = QueryGenerator.initQueryWrapper(wmsAppVersions, req.getParameterMap());
		Page<WmsAppVersions> page = new Page<WmsAppVersions>(pageNo, pageSize);
		IPage<WmsAppVersions> pageList = wmsAppVersionsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param wmsAppVersions
	 * @return
	 */
	@AutoLog(value = "wms_app_versions-添加")
	@ApiOperation(value="wms_app_versions-添加", notes="wms_app_versions-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsAppVersions wmsAppVersions) {
		wmsAppVersionsService.save(wmsAppVersions);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param wmsAppVersions
	 * @return
	 */
	@AutoLog(value = "wms_app_versions-编辑")
	@ApiOperation(value="wms_app_versions-编辑", notes="wms_app_versions-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsAppVersions wmsAppVersions) {
		wmsAppVersionsService.updateById(wmsAppVersions);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wms_app_versions-通过id删除")
	@ApiOperation(value="wms_app_versions-通过id删除", notes="wms_app_versions-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsAppVersionsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "wms_app_versions-批量删除")
	@ApiOperation(value="wms_app_versions-批量删除", notes="wms_app_versions-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsAppVersionsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "wms_app_versions-通过id查询")
	@ApiOperation(value="wms_app_versions-通过id查询", notes="wms_app_versions-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsAppVersions wmsAppVersions = wmsAppVersionsService.getById(id);
		if(wmsAppVersions==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsAppVersions);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsAppVersions
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsAppVersions wmsAppVersions) {
        return super.exportXls(request, wmsAppVersions, WmsAppVersions.class, "wms_app_versions");
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
        return super.importExcel(request, response, WmsAppVersions.class);
    }

}
