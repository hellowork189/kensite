/*
 * Powered By cuichen
 * Since 2014 - 2015
 */
package com.seeyoui.kensite.framework.mod.tableColumn.controller;  
 
import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seeyoui.kensite.common.base.controller.BaseController;
import com.seeyoui.kensite.common.constants.StringConstant;
import com.seeyoui.kensite.common.util.RequestResponseUtil;

import com.seeyoui.kensite.common.constants.StringConstant;
import com.seeyoui.kensite.common.base.domain.EasyUIDataGrid;
import com.seeyoui.kensite.common.base.controller.BaseController;
import com.seeyoui.kensite.common.util.RequestResponseUtil;
import com.seeyoui.kensite.framework.mod.tableColumn.domain.TableColumn;
import com.seeyoui.kensite.framework.mod.tableColumn.service.TableColumnService;
/**
 * 业务表字段
 * @author cuichen
 * @version 1.0
 * @since 1.0
 * @date 2015-10-24
 */
@Controller
@RequestMapping(value = "sys/tableColumn")
public class TableColumnController extends BaseController {
	
	@Autowired
	private TableColumnService tableColumnService;
	
	/**
	 * 获取列表展示数据
	 * @param modelMap
	 * @param tableColumn
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:tableColumn:select")
	@RequestMapping(value = "/list/data", method=RequestMethod.POST)
	@ResponseBody
	public Object listData(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, TableColumn tableColumn) throws Exception{
		List<TableColumn> tableColumnList = tableColumnService.findList(tableColumn);
		int total = tableColumnService.findTotal(tableColumn);
		EasyUIDataGrid eudg = new EasyUIDataGrid();
		eudg.setRows(tableColumnList);
		eudg.setTotal(String.valueOf(total));
		return eudg;
	}
	
	/**
	 * 获取所有数据
	 * @param modelMap
	 * @param tableColumn
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:tableColumn:select")
	@RequestMapping(value = "/list/all", method=RequestMethod.POST)
	@ResponseBody
	public Object listAll(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, TableColumn tableColumn) throws Exception{
		List<TableColumn> tableColumnList = tableColumnService.findAll(tableColumn);
		return tableColumnList;
	}
	
	/**
	 * 保存新增的数据
	 * @param modelMap
	 * @param tableColumn
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:tableColumn:insert")
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, TableColumn tableColumn) throws Exception{
		if (!beanValidator(modelMap, tableColumn)){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
			return null;
		}
		tableColumnService.save(tableColumn);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 保存修改的数据
	 * @param modelMap
	 * @param tableColumn
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:tableColumn:update")
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	@ResponseBody
	public String update(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, TableColumn tableColumn) throws Exception{
		if (!beanValidator(modelMap, tableColumn)){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
			return null;
		}
		tableColumnService.update(tableColumn);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 删除数据库
	 * @param modelMap
	 * @param tableColumnId
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("sys:tableColumn:delete")
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, String id) throws Exception {
		List<String> listId = Arrays.asList(id.split(","));
		tableColumnService.delete(listId);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
}