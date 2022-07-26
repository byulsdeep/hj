package com.han.jun.beans;

import java.util.List;

import lombok.Data;

@Data
public class ProjectB {
	private String projectCode;
	private String projectName;
	private String projectComment;
	private String startDate;
	private String endDate;
	private String isVisible;
	private List<ProMemB> projectMembers;
}
