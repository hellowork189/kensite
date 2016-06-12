/*
 * Powered By cuichen
 * Since 2014 - 2016
 */
package com.seeyoui.kensite.planManage.planTaskDelay.controller;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.lucene.index.Term;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seeyoui.kensite.common.constants.StringConstant;
import com.seeyoui.kensite.common.util.RequestResponseUtil;
import com.seeyoui.kensite.framework.luence.domain.LuceneDocument;
import com.seeyoui.kensite.framework.luence.util.LuceneUtils;

import com.seeyoui.kensite.common.util.DateUtils;
import com.seeyoui.kensite.common.constants.StringConstant;
import com.seeyoui.kensite.common.base.domain.EasyUIDataGrid;
import com.seeyoui.kensite.common.base.controller.BaseController;
import com.seeyoui.kensite.common.util.RequestResponseUtil;
import com.seeyoui.kensite.common.util.excel.ExportExcel;

import com.seeyoui.kensite.framework.luence.domain.LuceneDocument;
import com.seeyoui.kensite.framework.luence.util.LuceneUtils;

import com.seeyoui.kensite.planManage.planTaskDelay.domain.PlanTaskDelay;
import com.seeyoui.kensite.planManage.planTaskDelay.service.PlanTaskDelayService;
/**
 * 任务延期
 * @author cuichen
 * @version 1.0
 * @since 1.0
 * @date 2016-06-06
 */
@Controller
@RequestMapping(value = "planManage/planTaskDelay")
public class PlanTaskDelayController extends BaseController {
	
	@Autowired
	private PlanTaskDelayService planTaskDelayService;
	
	/**
	 * 展示列表页面
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:view")
	@RequestMapping(value = "/{page}")
	public ModelAndView view(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, @PathVariable String page, String taskId) throws Exception {
		modelMap.put("taskId", taskId);
		return new ModelAndView("planManage/planTaskDelay/"+page, modelMap);
	}
	
	/**
	 * 根据ID查询单条数据
	 * @param modelMap
	 * @param module
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:select")
	@RequestMapping(value = "/data/{id}")
	@ResponseBody
	public Object data(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, @PathVariable String id) throws Exception {
		PlanTaskDelay planTaskDelay = planTaskDelayService.findOne(id);
		return planTaskDelay;
	}
	
	/**
	 * 获取列表展示数据
	 * @param modelMap
	 * @param planTaskDelay
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:select")
	@RequestMapping(value = "/list/data", method=RequestMethod.POST)
	@ResponseBody
	public Object listData(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, PlanTaskDelay planTaskDelay) throws Exception {
		List<PlanTaskDelay> planTaskDelayList = planTaskDelayService.findList(planTaskDelay);
		int total = planTaskDelayService.findTotal(planTaskDelay);
		EasyUIDataGrid eudg = new EasyUIDataGrid();
		eudg.setTotal(String.valueOf(total));
		eudg.setRows(planTaskDelayList);
		return eudg;
	}
	
	/**
	 * 获取所有数据
	 * @param modelMap
	 * @param planTaskDelay
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:select")
	@RequestMapping(value = "/list/all", method=RequestMethod.POST)
	@ResponseBody
	public Object listAll(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, PlanTaskDelay planTaskDelay) throws Exception {
		List<PlanTaskDelay> planTaskDelayList = planTaskDelayService.findAll(planTaskDelay);
		return planTaskDelayList;
	}
	
	/**
	 * 保存新增的数据
	 * @param modelMap
	 * @param planTaskDelay
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:insert")
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	@ResponseBody
	public String save(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, PlanTaskDelay planTaskDelay) throws Exception {
		if (!beanValidator(modelMap, planTaskDelay)){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
			return null;
		}
		planTaskDelayService.save(planTaskDelay);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 保存修改的数据
	 * @param modelMap
	 * @param planTaskDelay
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:update")
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	@ResponseBody
	public String update(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, PlanTaskDelay planTaskDelay) throws Exception {
		if (!beanValidator(modelMap, planTaskDelay)){
			RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.FALSE);
			return null;
		}
		planTaskDelayService.update(planTaskDelay);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}
	
	/**
	 * 删除数据库
	 * @param modelMap
	 * @param planTaskDelayId
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:delete")
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public String delete(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, String id) throws Exception {
		List<String> listId = Arrays.asList(id.split(","));
		planTaskDelayService.delete(listId);
		RequestResponseUtil.putResponseStr(session, response, request, modelMap, StringConstant.TRUE);
		return null;
	}

	/**
	 * 导出Excel数据
	 * @param modelMap
	 * @param planTaskDelay
	 * @return
	 * @throws Exception
	 */
	//@RequiresPermissions("planManage:planTaskDelay:export")
	@RequestMapping(value = "/export")
	public String export(HttpSession session,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap, PlanTaskDelay planTaskDelay) throws Exception {
		String fileName = DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
		List<PlanTaskDelay> planTaskDelayList = planTaskDelayService.findAll(planTaskDelay);
		new ExportExcel(null, PlanTaskDelay.class).setDataList(planTaskDelayList).write(response, fileName).dispose();
		return null;
	}
}