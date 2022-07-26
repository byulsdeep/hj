package com.han.jun.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.beans.AuthB;
import com.han.jun.inter.ServicesRule;
import com.han.jun.utils.Encryption;
import com.han.jun.utils.ProjectUtils;


@Service //빈에 올려야하기떄문에 어노테이션 걸어주어야한다.
public class Auth implements ServicesRule {	
	@Autowired 
	SqlSessionTemplate session;
	@Autowired 
	ProjectUtils pu;
	@Autowired
	Encryption enc;
	
	public Auth() {}

	public void backController(String serviceCode, ModelAndView mav) {
		System.out.println("Authentication/backController");
		if(isSession()) {
			switch(serviceCode){ 
			case "First" : // session 확인 후 home or main 이동 결정
				first(mav);
				break;
			case "Refresh" :
				refresh(mav);
				break;	
			case "LogOut" :
				logOut(mav);
				break;		
			}
		} else {
			switch(serviceCode){ 
			/*case "Landing" : // IP확인용 서비스 
				break;*/
			case "First" : // session 확인 후 home or main 이동 결정
				first(mav);
				break;
			case "Refresh" :
				refresh(mav);
				break;	
			case "LogIn" :
				logIn(mav);
				break;
			case "SignUp" :
				signUp(mav);
				break;	
			case "Join" :
				join(mav);
				break;	
			}
		}
	}
	
	public void backController(String serviceCode, Model model) {}
	
	private void first(ModelAndView mav) {
		System.out.println("first|랜딩");
		
		String page = "home";
		if(isSession()) {page = "main";}
		mav.setViewName(page);
	}
	
	private void refresh(ModelAndView mav) {
		System.out.println("refresh|새로고침 제어");
		if(isSession()) {mav.setViewName("main");} else {logIn(mav);}
	}
	
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	private void logIn(ModelAndView mav) {
		System.out.println("logIn|로그인");
		String page = "home";
		AuthB ab = ((AuthB)mav.getModel().get("authB"));
		
		//세션확인
		 if(this.enc.matches(ab.getPmbPassword(), this.session.selectOne("getPassword" , ab))) {
		 
		 System.out.println("비밀번호 일치");
		 if(this.convert(this.session.selectOne("isAccess", ab))) {
			 ab.setAslAction(-1);
			 this.session.insert("insAsl", ab);	
			 System.out.println("logout|강제로그아웃");
		 }
		 ab.setAslAction(1);
		 if (this.convert(this.session.insert("insAsl", ab))) {
			System.out.println("login Success");
			AuthB auth = (AuthB)session.selectList("getAccessInfo", ab).get(0);
			
			 try {
				 auth.setPmbName(this.enc.aesDecode(auth.getPmbName(), auth.getPmbCode()));
				 auth.setPmbEmail(this.enc.aesDecode(auth.getPmbEmail(), auth.getPmbCode()));
				 pu.setAttribute("accessInfo", auth);
				 System.out.println("session set");
				 //여기도 정상적으로 찍힘
			} catch (Exception e) {e.printStackTrace();}		 
		 } 
		 page = "main";
		 } else {
			 System.out.println("wrong credentials|정보 오류");
		 }
		mav.setViewName(page);
	}
	@Transactional(readOnly = true)
	private void signUp(ModelAndView mav) {
		
	
		mav.addObject("pmbCode", this.session.selectOne("getNewPmbCode"));
		
		mav.setViewName("join");
		
	}
	private void join(ModelAndView mav) {
		String page = "join";
		
		AuthB ab = (AuthB)mav.getModel().get("authB");
		
		try {
			ab.setPmbName(enc.aesEncode(ab.getPmbName(), ab.getPmbCode()));
			ab.setPmbEmail(enc.aesEncode(ab.getPmbEmail(), ab.getPmbCode()));
		} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {e.printStackTrace();}
		
		ab.setPmbPassword(enc.encode(ab.getPmbPassword()));

		if(convert(session.insert("insPmb", ab))) { page = "home"; } 
		
		mav.setViewName(page);
	}
	
	private void logOut(ModelAndView mav) {
		try {
			AuthB ab = (AuthB)this.pu.getAttribute("accessInfo");
			ab.setAslAction(-1);
			this.session.insert("insAsl", ab);
			this.pu.removeAttribute("accessInfo");
		} catch (Exception e) {e.printStackTrace();}
		
		mav.setViewName("home");
	}

	boolean convert(int result) {
		return result >= 1 ? true : false;
	}
	
	boolean isSession() {
		AuthB session;		
		try {
			return (session = (AuthB)this.pu.getAttribute("accessInfo")) != null ? true : false;		
		} catch (Exception e) {e.printStackTrace(); return false;}	
	}
}
