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
import org.jeecg.modules.wms.entity.WmsDistributionTransfer;
import org.jeecg.modules.wms.service.IWmsDistributionTransferService;

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
 * @Description: 配送单中转表
 * @Author: jeecg-boot
 * @Date:   2023-06-01
 * @Version: V1.0
 */
@Api(tags="配送单中转表")
@RestController
@RequestMapping("/wms/wmsDistributionTransfer")
@Slf4j
public class WmsDistributionTransferController extends JeecgController<WmsDistributionTransfer, IWmsDistributionTransferService> {
	@Autowired
	private IWmsDistributionTransferService wmsDistributionTransferService;
	
	/**
	 * 分页列表查询
	 *
	 * @param wmsDistributionTransfer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "配送单中转表-分页列表查询")
	@ApiOperation(value="配送单中转表-分页列表查询", notes="配送单中转表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsDistributionTransfer wmsDistributionTransfer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsDistributionTransfer> queryWrapper = QueryGenerator.initQueryWrapper(wmsDistributionTransfer, req.getParameterMap());
		Page<WmsDistributionTransfer> page = new Page<WmsDistributionTransfer>(pageNo, pageSize);
		IPage<WmsDistributionTransfer> pageList = wmsDistributionTransferService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param wmsDistributionTransfer
	 * @return
	 */
	@AutoLog(value = "配送单中转表-添加")
	@ApiOperation(value="配送单中转表-添加", notes="配送单中转表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsDistributionTransfer wmsDistributionTransfer) {
		wmsDistributionTransferService.save(wmsDistributionTransfer);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param wmsDistributionTransfer
	 * @return
	 */
	@AutoLog(value = "配送单中转表-编辑")
	@ApiOperation(value="配送单中转表-编辑", notes="配送单中转表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsDistributionTransfer wmsDistributionTransfer) {
		wmsDistributionTransferService.updateById(wmsDistributionTransfer);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "配送单中转表-通过id删除")
	@ApiOperation(value="配送单中转表-通过id删除", notes="配送单中转表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsDistributionTransferService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "配送单中转表-批量删除")
	@ApiOperation(value="配送单中转表-批量删除", notes="配送单中转表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsDistributionTransferService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "配送单中转表-通过id查询")
	@ApiOperation(value="配送单中转表-通过id查询", notes="配送单中转表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsDistributionTransfer wmsDistributionTransfer = wmsDistributionTransferService.getById(id);
		if(wmsDistributionTransfer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsDistributionTransfer);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsDistributionTransfer
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsDistributionTransfer wmsDistributionTransfer) {
        return super.exportXls(request, wmsDistributionTransfer, WmsDistributionTransfer.class, "配送单中转表");
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
        return super.importExcel(request, response, WmsDistributionTransfer.class);
    }

}
