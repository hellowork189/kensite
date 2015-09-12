/*
 * Powered By cuichen
 * Since 2014 - 2015
 */
package com.seeyoui.kensite.framework.system.controller;  
 
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seeyoui.kensite.common.base.controller.BaseController;
import com.seeyoui.kensite.common.base.domain.EasyUIDataGrid;
import com.seeyoui.kensite.common.constants.StringConstant;
import com.seeyoui.kensite.common.exception.CRUDException;
import com.seeyoui.kensite.common.util.MD5;
import com.seeyoui.kensite.common.util.RequestResponseUtil;
import com.seeyoui.kensite.framework.system.constants.SysUserConstants;
import com.seeyoui.kensite.framework.system.domain.SysUser;
import com.seeyoui.kensite.framework.system.service.SysUserService;
/**
 * @author cuichen
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping(value = "sysUser")
public class SysUserController extends BaseController {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 展示列表页面
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:view")
	@RequestMapping(value = "/showPageList")
	public ModelAndView showSysUserPageList(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		return new ModelAndView("framework/system/sysUser", modelMap);
	}
	
	/**
	 * 获取列表展示数据
	 * @param modelMap
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:select")
//	@RequiresRoles("sys")
	@RequestMapping(value = "/getListData", method=RequestMethod.POST)
	@ResponseBody
	public String getListData(HttpSession session,
		HttpServletResponse response, HttpServletRequest request,
		ModelMap modelMap, SysUser sysUser) throws Exception{
		List<SysUser> sysUserList = sysUserService.findSysUserList(sysUser);
		EasyUIDataGrid eudg = sysUserService.findSysUserListTotal(sysUser);
		eudg.setRows(sysUserList);
		JSONObject jsonObj = JSONObject.fromObject(eudg);
		RequestResponseUtil.putResponseStr(session, response, request, jsonObj);
		return null;
	}
	
	/**
	 * 保存新增的数据
	 * @param modelMap
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:insert")
	@RequestMapping(value = "/saveByAdd", method=RequestMethod.POST)
	@ResponseBody
	public String saveSysUserByAdd(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, SysUser sysUser) throws Exception{
		String resultInfo = sysUserService.saveSysUser(sysUser);
//		RequestResponseUtil.putResponseStr(session, response, request, resultInfo);
		modelMap.put("message", resultInfo);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 保存修改的数据
	 * @param modelMap
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:update")
	@RequestMapping(value = "/saveByUpdate", method=RequestMethod.POST)
	@ResponseBody
	public String saveSysUserByUpdate(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, SysUser sysUser) throws Exception{
		if (!beanValidator(modelMap, sysUser)){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
			return null;
		}
		sysUserService.updateSysUser(sysUser);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 修改用户密码
	 * @param modelMap
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping(value = "/updatePassword", method=RequestMethod.POST)
	@ResponseBody
	public String updatePassword(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, SysUser sysUser) throws Exception{
		if (!beanValidator(modelMap, sysUser)){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
			return null;
		}
		sysUser.setPassword(MD5.md5(sysUser.getUserName()+sysUser.getPassword()));
		sysUserService.updatePassword(sysUser);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 修改账号状态
	 * @param modelMap
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:update")
	@RequestMapping(value = "/updateState", method=RequestMethod.POST)
	@ResponseBody
	public String updateState(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, SysUser sysUser) throws Exception{
		sysUserService.updateState(sysUser);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 初始化密码
	 * @param modelMap
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:update")
	@RequestMapping(value = "/initPassword", method=RequestMethod.POST)
	@ResponseBody
	public String initPassword(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, SysUser sysUser) throws Exception{
		sysUser = sysUserService.findSysUserById(sysUser.getId());
		sysUser.setPassword(MD5.md5(sysUser.getUserName()+StringConstant.INIT_PASSWORD));
		sysUserService.updatePassword(sysUser);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 删除数据库
	 * @param modelMap
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sysUser:delete")
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, String delDataId) throws Exception {
		List<String> listId = Arrays.asList(delDataId.split(","));
		sysUserService.deleteSysUser(listId);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 验证用户名是否已被占用
	 * @param modelMap
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUserName")
	@ResponseBody
	public String validateUserName(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, String userName) throws Exception {
		SysUser sysUserResult = sysUserService.findSysUserByUserName(userName);
		if(sysUserResult != null 
			&& (sysUserResult.getId() != null && !sysUserResult.getId().equals(""))){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
		}
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 验证用户名是否已被占用
	 * @param modelMap
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validatePassWord")
	@ResponseBody
	public String validatePassWord(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, String userName, String passWord) throws Exception {
		SysUser sysUserResult = sysUserService.findSysUserByUserName(userName);
		if(sysUserResult != null 
			&& (sysUserResult.getId() != null && !sysUserResult.getId().equals(""))){
			String newPassWord = MD5.md5(userName+passWord);
			if(newPassWord.equals(sysUserResult.getPassword())) {
				RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
			}
		}
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
		return null;
	}
}