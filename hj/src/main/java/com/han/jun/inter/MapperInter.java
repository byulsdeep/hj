package com.han.jun.inter;

import java.util.List;

import com.han.jun.beans.AuthB;
import com.han.jun.beans.ProjectB;
import com.han.jun.beans.ProListB;
import com.han.jun.beans.ProMemB;
import com.han.jun.beans.AulB;

public interface MapperInter {
	/* auth */	
	public String getPassword(AuthB ab);
	public int insAuthLog(AuthB ab);
	public int isAccess(AuthB ab);
	public int insAsl(AuthB ab);
	public List<AuthB> getAccessInfo(AuthB ab);
	
	/* signup */
	public String getNewPmbCode();
	public List<AuthB> getLevelList();
	public List<AuthB> getClassList();
	public int insPmb(AuthB ab);
	
	/* project */
	public int insProject(ProjectB pb);
	public List<AuthB> getMemberList(AuthB ab);
	public int insProjectMembers(ProjectB pb);
	public int insSelf(ProMemB pm);
	
	/* email auth */
	public int insAul(AulB aul);
	public int updPrm(ProMemB pm);
	public int updAul(AulB aul);
	
	/* invites */
	public AulB receivedInvitationInfo(AuthB ab); 
	public AulB sentInvitationInfo(AuthB ab);  
	public String isInvited(AuthB ab);
	public String getInviteDate(AulB aul);
	
	
	/* project */
	public List<ProListB> getProjectList(AuthB ab);
	public String getProjectMembers(ProjectB pb);
	
}
