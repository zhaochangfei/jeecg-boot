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
import org.jeecg.modules.wms.dto.WmsDistributionDto;
import org.jeecg.modules.wms.entity.WmsDistribution;
import org.jeecg.modules.wms.service.IWmsDistributionService;

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
 * @Description: 配送单
 * @Author: jeecg-boot
 * @Date:   2023-05-10
 * @Version: V1.0
 */
@Api(tags="配送单")
@RestController
@RequestMapping("/wms/wmsDistribution")
@Slf4j
public class WmsDistributionController extends JeecgController<WmsDistribution, IWmsDistributionService> {
	@Autowired
	private IWmsDistributionService wmsDistributionService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsDistribution
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "配送单-分页列表查询")
	@ApiOperation(value="配送单-分页列表查询", notes="配送单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsDistribution wmsDistribution,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsDistribution> queryWrapper = QueryGenerator.initQueryWrapper(wmsDistribution, req.getParameterMap());
		Page<WmsDistribution> page = new Page<WmsDistribution>(pageNo, pageSize);
		IPage<WmsDistribution> pageList = wmsDistributionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsDistributionDto
	 * @return
	 */
	@AutoLog(value = "配送单-添加")
	@ApiOperation(value="配送单-添加", notes="配送单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsDistributionDto wmsDistributionDto) {
		try {
			wmsDistributionService.addOrEdit(wmsDistributionDto);
			return Result.OK("添加成功！");
		}catch (Exception e){
			e.printStackTrace();
			return Result.error("添加失败！");
		}

	}

	/**
	 *  编辑
	 *
	 * @param wmsDistributionDto
	 * @return
	 */
	@AutoLog(value = "配送单-编辑")
	@ApiOperation(value="配送单-编辑", notes="配送单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsDistributionDto wmsDistributionDto) {
		try {
			wmsDistributionService.addOrEdit(wmsDistributionDto);
			return Result.OK("编辑成功！");
		}catch (Exception e){
			e.printStackTrace();
			return Result.error("编辑失败！");
		}
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "配送单-通过id删除")
	@ApiOperation(value="配送单-通过id删除", notes="配送单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsDistributionService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "配送单-批量删除")
	@ApiOperation(value="配送单-批量删除", notes="配送单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsDistributionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "配送单-通过id查询")
	@ApiOperation(value="配送单-通过id查询", notes="配送单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsDistribution wmsDistribution = wmsDistributionService.getById(id);
		if(wmsDistribution==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsDistribution);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsDistribution
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsDistribution wmsDistribution) {
        return super.exportXls(request, wmsDistribution, WmsDistribution.class, "配送单");
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
        return super.importExcel(request, response, WmsDistribution.class);
    }

}