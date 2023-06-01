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
import org.jeecg.modules.wms.entity.WmsOffer;
import org.jeecg.modules.wms.service.IWmsOfferService;

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
 * @Description: 报价表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Api(tags="报价表")
@RestController
@RequestMapping("/wms/wmsOffer")
@Slf4j
public class WmsOfferController extends JeecgController<WmsOffer, IWmsOfferService> {
	@Autowired
	private IWmsOfferService wmsOfferService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsOffer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "报价表-分页列表查询")
	@ApiOperation(value="报价表-分页列表查询", notes="报价表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsOffer wmsOffer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsOffer> queryWrapper = QueryGenerator.initQueryWrapper(wmsOffer, null);
		Page<WmsOffer> page = new Page<WmsOffer>(pageNo, pageSize);
		queryWrapper.orderByDesc("create_time");
		IPage<WmsOffer> pageList = wmsOfferService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsOffer
	 * @return
	 */
	@AutoLog(value = "报价表-添加")
	@ApiOperation(value="报价表-添加", notes="报价表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsOffer wmsOffer) {
		wmsOfferService.save(wmsOffer);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param wmsOffer
	 * @return
	 */
	@AutoLog(value = "报价表-编辑")
	@ApiOperation(value="报价表-编辑", notes="报价表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsOffer wmsOffer) {
		wmsOfferService.updateById(wmsOffer);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报价表-通过id删除")
	@ApiOperation(value="报价表-通过id删除", notes="报价表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsOfferService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "报价表-批量删除")
	@ApiOperation(value="报价表-批量删除", notes="报价表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsOfferService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报价表-通过id查询")
	@ApiOperation(value="报价表-通过id查询", notes="报价表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsOffer wmsOffer = wmsOfferService.getById(id);
		if(wmsOffer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsOffer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsOffer
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsOffer wmsOffer) {
        return super.exportXls(request, wmsOffer, WmsOffer.class, "报价表");
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
        return super.importExcel(request, response, WmsOffer.class);
    }

}
