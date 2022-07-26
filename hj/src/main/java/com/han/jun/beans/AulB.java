package com.han.jun.beans;

import lombok.Data;

@Data
public class AulB {
	private String sender;
	private String senderName;
	private String receiver;
	private String receiverName;
	private String inviteDate;
	private String expireDate;
	private String authResult;
	private String authResultName;
	
	/* mine */
	private String projectName;
	
	/* weird */
	private String projectCode;
}
