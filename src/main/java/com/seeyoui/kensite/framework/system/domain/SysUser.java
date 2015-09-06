/*
 * Powered By cuichen
 * Since 2014 - 2015
 */
package com.seeyoui.kensite.framework.system.domain;  

import java.io.Serializable;
import java.util.List;

import com.seeyoui.kensite.common.base.domain.Pager;

/**
 * @author cuichen
 * @version 1.0
 * @since 1.0
 */
public class SysUser extends Pager implements Serializable {  
    private static final long serialVersionUID = 5454155825314635342L;  
      
    private String id;  
    private String userName;  
    private String password;  
    private String name;  
    private String email;  
    private String phone;  
    private String departmentId;  
    private String state;  
    private SysDepartment sysDepartment;
    private List<SysRole> roleList;
 
    public void setId(String id) {  
        this.id = id;  
    }  
      
    public String getId() {  
        return this.id;  
    }  
    public void setUserName(String userName) {  
        this.userName = userName;  
    }  
      
    public String getUserName() {  
        return this.userName;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
      
    public String getPassword() {  
        return this.password;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
      
    public String getName() {  
        return this.name;  
    }  
    public void setDepartmentId(String departmentId) {  
        this.departmentId = departmentId;  
    }  
      
    public String getDepartmentId() {  
        return this.departmentId;  
    }  
    public void setState(String state) {  
        this.state = state;  
    }  
      
    public String getState() {  
        return this.state;  
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SysDepartment getSysDepartment() {
		return sysDepartment;
	}

	public void setSysDepartment(SysDepartment sysDepartment) {
		this.sysDepartment = sysDepartment;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}  
}