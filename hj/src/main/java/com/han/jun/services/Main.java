package com.han.jun.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.beans.AulB;
import com.han.jun.beans.AuthB;
import com.han.jun.beans.ProListB;
import com.han.jun.beans.ProMemB;
import com.han.jun.beans.ProjectB;
import com.han.jun.inter.ServicesRule;
import com.han.jun.utils.Encryption;
import com.han.jun.utils.ProjectUtils;

@Service
public class Main implements ServicesRule{

	@Autowired
	private SqlSessionTemplate session;
	@Autowired
	private Encryption enc;
	@Autowired 
	private ProjectUtils pu;
	@Autowired
	private Auth auth;

	public Main() {}

	public void backController(String serviceCode, ModelAndView mav) {
		if(auth.isSession()) {
			System.out.println("Main/backController");
			switch(serviceCode) {
			case "MoveMain":
				this.mainCtl(mav);
				break;
			case "Refusal":
				this.refusal(mav);
				break;
			case "EmailAuth":
				this.emailAuth(mav);
				break;	
			}
		} else {
			mav.setViewName("home");
		}
	}

	public void backController(String serviceCode, Model model) {
		if(auth.isSession()) {
			System.out.println("Main/backController");
			switch(serviceCode) {
			case "IsInvited": 
				this.isInvited(model);
				break;
			case "IsInvited2": 
				this.isInvited2(model);
				break;	
			case "GetProjectList": 
				this.getProjectList(model);
				break;	
			case "GetProjectMembers": 
				this.getProjectMembers(model);
				break;		
			}
		}
	}

	private void mainCtl(ModelAndView mav) {
		System.out.println("Main/mainCtl");
		String page = "main";
		mav.setViewName(page);
	}

	private void isInvited(Model model) {		
		System.out.println("Main/IsInvited");
		List<AulB> inviteList = null;
		AuthB ab = (AuthB)model.getAttribute("authB");

		try {
			ab.setPmbCode(((AuthB)this.pu.getAttribute("accessInfo")).getPmbCode());
		} catch (Exception e) {e.printStackTrace();}

		if(this.convert(Integer.parseInt(this.session.selectOne("isInvited", ab)))) {
			System.out.println("invited=true");
			inviteList = this.session.selectList("receivedInvitationInfo", ab);
			
			for(AulB aul : inviteList) {
				try {
					aul.setSenderName(this.enc.aesDecode(aul.getSenderName(), aul.getSender()));
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {e.printStackTrace();}
			}
		}
				
		model.addAttribute("InviteList", inviteList);

	}
	private void isInvited2(Model model) {		
		System.out.println("Main/IsInvited2");
		List<AulB> sentList = null;
		AuthB ab = (AuthB)model.getAttribute("authB");

		try {
			ab.setPmbCode(((AuthB)this.pu.getAttribute("accessInfo")).getPmbCode());
		} catch (Exception e) {e.printStackTrace();}		
			sentList = this.session.selectList("sentInvitationInfo", ab);
			
			for(AulB aul : sentList) {
				try {
					aul.setReceiverName(this.enc.aesDecode(aul.getReceiverName(), aul.getReceiver()));
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {e.printStackTrace();}
			}	
		model.addAttribute("SentList", sentList);
	}
	
	private void getProjectList(Model model) {		
		System.out.println("Main/getProjectList");
		List<ProListB> projectList = null;
		AuthB ab = (AuthB)model.getAttribute("authB");

		try {
			ab.setPmbCode(((AuthB)this.pu.getAttribute("accessInfo")).getPmbCode());
		} catch (Exception e) {e.printStackTrace();}		
			projectList = this.session.selectList("getProjectList", ab);
			
			for(ProListB pl : projectList) {
				try {
					pl.setManagerName(this.enc.aesDecode(pl.getManagerName(), pl.getManager()));
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {e.printStackTrace();}
			}	
		model.addAttribute("ProjectList", projectList);
	}
	
	private void emailAuth(ModelAndView mav) {
		System.out.println("EmailAuth");
		AuthB ab = (AuthB)mav.getModel().get("authB");
		ProMemB pm;
		AulB aul;
		String page = "main";
		String message = "";
		String key = "BDGames";
		
		try {
			AuthB session = ((AuthB)this.pu.getAttribute("accessInfo"));		
			String authCode = (this.enc.aesDecode((ab.getAuthCode()), key));
			String split[] = authCode.split(":");
			String projectCode = split[0];
			String email = split[1];
			String inviteDate = split[2];
			String sender = split[3];
					
			if(session.getPmbEmail().equals(email)) {
				System.out.println("authCode accepted");	
					message = "인증성공";
					mav.addObject("message", message);
					
					pm = new ProMemB();
					pm.setIsAccept("AC");
					pm.setProjectCode(projectCode);
					pm.setPmbCode(session.getPmbCode());				
					this.session.update("updPrm", pm);
					System.out.println("updPrm check");	
					
					aul = new AulB();
					aul.setAuthResult("AU");
					aul.setInviteDate(inviteDate);
					aul.setSender(sender);
					aul.setReceiver(session.getPmbCode());		
					this.session.update("updAul", aul);
					System.out.println("updAul check");	
					
					mav.setViewName(page);
					
			} else {
				System.out.println("authCode denied");	
				message = "인증실패";
				
				aul = new AulB();
				aul.setAuthResult("NN");
				aul.setInviteDate(inviteDate);
				aul.setSender(sender);
				aul.setReceiver(session.getPmbCode());		
				this.session.update("updAul", aul);
				System.out.println("updAul check");	
				mav.setViewName("main");
			}
			
		} catch (Exception e) {e.printStackTrace();}	
		
	}
	private void refusal(ModelAndView mav) {
		System.out.println("Main/refusal");
		String page = "main";
		AuthB ab = (AuthB)mav.getModel().get("authB");
		ProMemB pm = new ProMemB();
		AulB aul = new AulB();
		String key = "BDGames";
		
		
		try {
			AuthB session = ((AuthB)this.pu.getAttribute("accessInfo"));
			String authCode = (this.enc.aesDecode((ab.getAuthCode()), key));
			String split[] = authCode.split(":");
			String projectCode = split[0];
			//String email = split[1];
			String inviteDate = split[2];
			String sender = split[3];
			
			pm.setIsAccept("RF");
			pm.setProjectCode(projectCode);
			pm.setPmbCode(session.getPmbCode());	
			this.session.update("updPrm", pm);
			System.out.println("updPrm check");	

			aul.setAuthResult("AU");
			aul.setInviteDate(inviteDate);
			aul.setSender(sender);
			aul.setReceiver(session.getPmbCode());		
			this.session.update("updAul", aul);
			System.out.println("updAul check");	
			
		} catch (Exception e) {e.printStackTrace();}
		

		this.session.update("updAul", aul);
		
		mav.setViewName(page);
	}
	
	private void getProjectMembers(Model model) {		
		System.out.println("Main/getProjectMembers");
		String count = "0";
		ProjectB pb = (ProjectB)model.getAttribute("projectB");
		
			count = this.session.selectOne("getProjectMembers", pb);
			
		model.addAttribute("ProjectMembers", count);
	}
	
	
	private boolean convert(int result) {
		return result >= 1 ? true : false;
	}
}
