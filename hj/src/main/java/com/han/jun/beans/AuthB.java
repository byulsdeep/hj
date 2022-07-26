package com.han.jun.beans;
import lombok.Data;

@Data
public class AuthB {
	private String pmbCode;
	private String pmbName;
	private String pmbPassword;
	private String aslPublicIp;
	private String aslPrivateIp;
	private String pmbLevel;
	private String pmbLevelName;
	private String pmbClass;
	private String pmbClassName;
	private String pmbEmail;
	private String aslDate;
	private int aslAction;
	private String authCode;
	private String message;
}
