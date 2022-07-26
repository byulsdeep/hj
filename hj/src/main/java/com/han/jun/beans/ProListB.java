package com.han.jun.beans;

import java.util.List;

import lombok.Data;

@Data
public class ProListB {
	private String projectCode;
	private String projectName;
	private String projectComment;
	private String startDate;
	private String endDate;
	private String isVisible;
	
	private String pmbCode;
	private String manager;
	private String managerName;
	private String position;
	
	private String pmbName;
	private String pmbLevel;
	private String pmbClass;
	
	private String pmbLevelName;
	private String pmbClassName;

	private List<ProjectB> projectBean;
}
