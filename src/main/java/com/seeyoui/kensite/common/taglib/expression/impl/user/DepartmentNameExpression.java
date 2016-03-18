package com.seeyoui.kensite.common.taglib.expression.impl.user;

import com.seeyoui.kensite.common.taglib.expression.ExpressionBase;
import com.seeyoui.kensite.common.taglib.expression.ExpressionInterface;
import com.seeyoui.kensite.common.util.StringUtils;
import com.seeyoui.kensite.framework.system.util.UserUtils;

public class DepartmentNameExpression extends ExpressionBase implements ExpressionInterface {

	@Override
	public String expresstionPares() {
		if(UserUtils.getUser() != null && UserUtils.getUser().getSysDepartment() != null) {
			return UserUtils.getUser().getSysDepartment().getName();
		} else {
			return "";
		}
	}
	
}
