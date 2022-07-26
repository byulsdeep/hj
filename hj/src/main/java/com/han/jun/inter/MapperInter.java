package com.han.jun.inter;

import java.util.List;

import com.han.jun.beans.AuthB;

public interface MapperInter {
	public String getNewPmbCode();
	public int insPmb(AuthB ab);
	public String getPassword(AuthB ab);
	public int insAuthLog(AuthB ab);
	public int isAccess(AuthB ab);
	public int insAsl(AuthB ab);
	public List<AuthB> getAccessInfo(AuthB ab);
	
}
