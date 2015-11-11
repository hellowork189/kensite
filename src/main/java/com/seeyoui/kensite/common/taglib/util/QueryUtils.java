package com.seeyoui.kensite.common.taglib.util;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import com.seeyoui.kensite.common.constants.StringConstant;
import com.seeyoui.kensite.common.taglib.constants.TableColumnConstants;
import com.seeyoui.kensite.common.util.CacheUtils;
import com.seeyoui.kensite.common.util.DBUtils;
import com.seeyoui.kensite.common.util.SpringContextHolder;
import com.seeyoui.kensite.common.util.StringUtils;
import com.seeyoui.kensite.framework.mod.tableColumn.domain.TableColumn;
import com.seeyoui.kensite.framework.mod.tableColumn.persistence.TableColumnMapper;
import com.seeyoui.kensite.framework.plugin.dict.domain.Dict;
import com.seeyoui.kensite.framework.system.util.DictUtils;

/**
 * 表单控件工具类
 * @author Ken
 * @version 2015-11-10
 */
public class QueryUtils {
	
	public static StringBuffer getTableColumnStr(TableColumn tableColumn) throws Exception {
		TableColumn tc = TagCacheUtils.getTableColumn(tableColumn);
		if(tc == null) {
			return null;
		}
		StringBuffer result = getEasyUIStr(tc);
		return result;
	}
	
	private static StringBuffer getEasyUIStr(TableColumn tableColumn) throws Exception {
		StringBuffer result = (StringBuffer)CacheUtils.get(TableColumnConstants.CACHE_QUERY+TableColumnConstants.CACHE_SPLIT+TableColumnConstants.CACHE_EASYUI+TableColumnConstants.CACHE_SPLIT+tableColumn.getTableName()+TableColumnConstants.CACHE_SPLIT+tableColumn.getName());
		if (result !=  null){
			return result;
		}
		boolean needCache = true;
		result = new StringBuffer();
		String column = tableColumn.getName();
		column = StringUtils.toCamelCase(column);
		result.append("<span>"+tableColumn.getComments()+"</span>");
		if(TableColumnConstants.TEXTBOX.equals(tableColumn.getCategory()) || TableColumnConstants.TEXTAREA.equals(tableColumn.getCategory()) || TableColumnConstants.HTMLDESIGN.equals(tableColumn.getCategory())) {
			result.append("<input class=\"easyui-textbox\" id=\"sel_");
			result.append(column);
			result.append("\" name=\"sel_");
			result.append(column);
			result.append("\" data-options=\"\"/>");
		}
		if(TableColumnConstants.NUMBERBOX.equals(tableColumn.getCategory())) {
			result.append("<input class=\"easyui-numberbox\" id=\"sel_");
			result.append(column);
			result.append("\" name=\"sel_");
			result.append(column);
			result.append("\" data-options=\"");
			if(StringUtils.isNoneBlank(tableColumn.getSettings())) {
				result.append(tableColumn.getSettings());
			}
			result.append("\" "+tableColumn.getHtmlInner());
			result.append("/>");
		}
		if(TableColumnConstants.COMBOBOX.equals(tableColumn.getCategory()) || TableColumnConstants.RADIOBOX.equals(tableColumn.getCategory()) || TableColumnConstants.CHECKBOX.equals(tableColumn.getCategory())) {
			needCache = false;
			result.append("<input class=\"easyui-combobox\" id=\"sel_");
			result.append(column);
			result.append("\" name=\"sel_");
			result.append(column);
			result.append("\" data-options=\"");
			int dataCount = 0;
			if(StringUtils.isNoneBlank(tableColumn.getSettings())) {
				result.append("valueField: 'value',textField: 'label',");
//				if(TableColumnConstants.CHECKBOX.equals(tableColumn.getCategory())) {
//					result.append("multiple:true,");
//				}
				String settings = tableColumn.getSettings();
				if(settings.indexOf("SQL>") != -1) {
					result.append("data: [");
					String[] settingsArr = settings.split("\\|");
					String sql = settingsArr[0].replace("SQL>", "");
					String value = settingsArr[1];
					String label = settingsArr[2];
					List<Map<Object, Object>> list = DBUtils.executeQuery(sql);
					for(Map<Object, Object> map : list) {
						dataCount++;
						result.append("{value: '"+map.get(value.toUpperCase())+"',label: '"+map.get(label.toUpperCase())+"'},");
					}
					result.substring(0, result.lastIndexOf(",")-1);
					result.append("]");
				} else if(settings.indexOf("DICT>") != -1) {
					result.append("data: [");
					List<Dict> dictList = DictUtils.getDictList(DictUtils.getDict(settings.replace("DICT>", "")).getValue());
					for(Dict dict : dictList) {
						dataCount++;
						result.append("{value: '"+dict.getValue()+"',label: '"+dict.getLabel()+"'},");
					}
					result.substring(0, result.lastIndexOf(",")-1);
					result.append("]");
				} else  {
					result.append("data: [");
					String[] settingsArr = settings.split("\\|");
					for(String set : settingsArr) {
						dataCount++;
						if(set.indexOf(":") == -1) {
							result.append("{label: '"+set+"',value: '"+set+"'},");
						} else {
							String[] setArr = set.split(":");
							result.append("{value: '"+setArr[0]+"',label: '"+setArr[1]+"'},");
						}
					}
					result.substring(0, result.lastIndexOf(",")-1);
					result.append("]");
				}
			}
			if(dataCount <= 5) {
				result.append(",panelHeight:'auto',");
			}
			result.append("\" "+tableColumn.getHtmlInner()+"/>");
		}
		if(TableColumnConstants.DATEBOX.equals(tableColumn.getCategory())) {
			result.append("<input id=\"sel_");
			result.append(column);
			result.append("\" name=\"sel_");
			result.append(column);
			result.append("\" "+tableColumn.getHtmlInner());
			if(StringUtils.isNoneBlank(tableColumn.getSettings())) {
				result.append(" onClick=\"WdatePicker({");
				result.append(tableColumn.getSettings());
				result.append("})\"");
			} else {
				result.append(" onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})\"");
			}
			result.append("/>");
		}
		if(needCache) {
			CacheUtils.put(TableColumnConstants.CACHE_QUERY+TableColumnConstants.CACHE_SPLIT+TableColumnConstants.CACHE_EASYUI+TableColumnConstants.CACHE_SPLIT+tableColumn.getTableName()+TableColumnConstants.CACHE_SPLIT+tableColumn.getName(), result);
		}
		return result;
	}
}