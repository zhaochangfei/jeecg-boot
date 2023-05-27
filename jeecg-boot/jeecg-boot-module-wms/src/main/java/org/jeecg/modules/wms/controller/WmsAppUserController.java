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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.wms.entity.WmsAppUser;
import org.jeecg.modules.wms.service.IWmsAppUserService;

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
 * @Description: app用户表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Api(tags="app用户表")
@RestController
@RequestMapping("/wms/wmsAppUser")
@Slf4j
public class WmsAppUserController extends JeecgController<WmsAppUser, IWmsAppUserService> {
	@Autowired
	private IWmsAppUserService wmsAppUserService;

	/**
	 * 分页列表查询
	 *
	 * @param wmsAppUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "app用户表-分页列表查询")
	@ApiOperation(value="app用户表-分页列表查询", notes="app用户表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WmsAppUser wmsAppUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WmsAppUser> queryWrapper = QueryGenerator.initQueryWrapper(wmsAppUser, req.getParameterMap());
		Page<WmsAppUser> page = new Page<WmsAppUser>(pageNo, pageSize);
		IPage<WmsAppUser> pageList = wmsAppUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param wmsAppUser
	 * @return
	 */
	@AutoLog(value = "app用户表-添加")
	@ApiOperation(value="app用户表-添加", notes="app用户表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WmsAppUser wmsAppUser) {
		try {
			wmsAppUserService.add(wmsAppUser);
			return Result.OK("添加成功！");
		}catch (Exception e){
            e.getMessage();
			return Result.error(e.getMessage());
		}
	}

	/**
	 *  编辑
	 *
	 * @param wmsAppUser
	 * @return
	 */
	@AutoLog(value = "app用户表-编辑")
	@ApiOperation(value="app用户表-编辑", notes="app用户表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WmsAppUser wmsAppUser) {
		try {
			wmsAppUserService.editById(wmsAppUser);
			return Result.OK("编辑成功!");
		}catch (Exception e){
			e.getMessage();
			return Result.error(e.getMessage());
		}
	}
	 /**
	  *  修改密码
	  *
	  * @param wmsAppUser
	  * @return
	  */
	 @AutoLog(value = "app用户表-修改密码")
	 @ApiOperation(value="app用户表-修改密码", notes="app用户表-修改密码")
	 @PutMapping(value = "/editPassWord")
	 public Result<?> editPassWord(@RequestBody WmsAppUser wmsAppUser) {
		 try {
			 WmsAppUser u = this.wmsAppUserService.getOne(new LambdaQueryWrapper<WmsAppUser>().eq(WmsAppUser::getUsername, wmsAppUser.getUsername()));
			 if (u == null) {
				 return Result.error("用户不存在！");
			 }
			 wmsAppUserService.editPassWord(wmsAppUser);
			 return Result.OK("修改成功!");
		 }catch (Exception e){
			 e.getMessage();
			 return Result.error(e.getMessage());
		 }
	 }
	 /**
	  *  校验用户账号是否唯一<br>
	  *  可以校验其他 需要检验什么就传什么。。。
	  *
	  * @param sysUser
	  * @return
	  */
	 @RequestMapping(value = "/checkOnlyUser", method = RequestMethod.GET)
	 public Result<Boolean> checkOnlyUser(WmsAppUser sysUser) {
		 Result<Boolean> result = new Result<>();
		 //如果此参数为false则程序发生异常
		 result.setResult(true);
		 try {
			 //通过传入信息查询新的用户信息
			 sysUser.setPassword(null);
			 WmsAppUser user = wmsAppUserService.getOne(new QueryWrapper<WmsAppUser>(sysUser));
			 if (user != null) {
				 result.setSuccess(false);
				 result.setMessage("用户账号已存在");
				 return result;
			 }

		 } catch (Exception e) {
			 result.setSuccess(false);
			 result.setMessage(e.getMessage());
			 return result;
		 }
		 result.setSuccess(true);
		 result.setCode(CommonConstant.SC_OK_200);
		 return result;
	 }

	/**
	 *   通过id删除/注销
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "app用户表-通过id删除/注销")
	@ApiOperation(value="app用户表-通过id删除/注销", notes="app用户表-通过id删除/注销")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wmsAppUserService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "app用户表-批量删除")
	@ApiOperation(value="app用户表-批量删除", notes="app用户表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.wmsAppUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "app用户表-通过id查询")
	@ApiOperation(value="app用户表-通过id查询", notes="app用户表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WmsAppUser wmsAppUser = wmsAppUserService.getById(id);
		if(wmsAppUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(wmsAppUser);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param wmsAppUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WmsAppUser wmsAppUser) {
        return super.exportXls(request, wmsAppUser, WmsAppUser.class, "app用户表");
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
        return super.importExcel(request, response, WmsAppUser.class);
    }

}
