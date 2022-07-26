package com.han.jun;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.beans.AuthB;
import com.han.jun.services.Auth;
import com.han.jun.services.Main;
import com.han.jun.services.Management;
import com.han.jun.services.MyPage;
import com.han.jun.services.Notification;
import com.han.jun.services.Project;
import com.han.jun.beans.ProjectB;

@Controller
public class HomeController {
	@Autowired
	Auth auth;
	@Autowired
	Main main;
	@Autowired
	Project pro;
	@Autowired
	Notification alert;
	@Autowired
	Management mgr;
	@Autowired
	MyPage my;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("서버온!");
		return "landing";
	}
	
/* Auth */ 
	
	//이경준
	@RequestMapping(value = "/First", method = RequestMethod.GET)
	public ModelAndView first(ModelAndView mav, @ModelAttribute AuthB ab) {
		mav.addObject(ab);
		auth.backController("First", mav);
		return mav;
	}
	//이경준
	@RequestMapping(value = "/LogIn", method = RequestMethod.POST)
	public ModelAndView logIn(HttpServletRequest req,ModelAndView mav, @ModelAttribute AuthB ab) {
		mav.addObject(ab);
		ab.setAslPrivateIp(req.getRemoteAddr());
		auth.backController("LogIn", mav);
		return mav;
	}	
	//강한별
	@RequestMapping(value = "/LogOut", method = RequestMethod.POST)
	public ModelAndView logOut(ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("HomeController/LogOut");
		mav.addObject(ab);
		auth.backController("LogOut", mav);
		return mav;
	}
	//이경준
	@RequestMapping(value = "/Join", method = RequestMethod.POST)
	public ModelAndView join(ModelAndView mav, @ModelAttribute AuthB ab) {
		mav.addObject(ab);
		auth.backController("Join", mav);
		return mav;
	}

/* MovePage */
	
	//이경준
	@RequestMapping(value = "/SignUp", method = RequestMethod.GET)
	public ModelAndView signUp(ModelAndView mav) {
		auth.backController("SignUp", mav);
		return mav;
	}
	//강한별
	@RequestMapping(value = "/MoveMain", method = RequestMethod.POST)
	public ModelAndView moveMain(HttpServletRequest req, ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("MoveMain");
		ab.setAslPrivateIp(req.getRemoteAddr());
		mav.addObject(ab);
		this.main.backController("MoveMain", mav);
		return mav;
	}
	//강한별
	@RequestMapping(value = "/MoveProject", method = RequestMethod.POST)
	public ModelAndView moveNewProject(HttpServletRequest req, ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("MoveProject");
		ab.setAslPrivateIp(req.getRemoteAddr());
		mav.addObject(ab);
		this.pro.backController("MoveProject", mav);
		return mav;
	}
	//강한별
	@RequestMapping(value = "/MoveAlert", method = RequestMethod.POST)
	public ModelAndView moveAlert(HttpServletRequest req, ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("MoveAlert");
		ab.setAslPrivateIp(req.getRemoteAddr());
		mav.addObject(ab);
		this.alert.backController("MoveAlert", mav);
		return mav;
	}
	//강한별
	@RequestMapping(value = "/MoveMgr", method = RequestMethod.POST)
	public ModelAndView moveMgr(HttpServletRequest req, ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("MoveMgr");
		ab.setAslPrivateIp(req.getRemoteAddr());
		mav.addObject(ab);
		this.mgr.backController("MoveMgr", mav);
		return mav;
	}
	//강한별
	@RequestMapping(value = "/MoveMyPage", method = RequestMethod.POST)
	public ModelAndView moveMyPage(HttpServletRequest req, ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("MoveMyPage");
		ab.setAslPrivateIp(req.getRemoteAddr());
		mav.addObject(ab);
		this.my.backController("MoveMyPage", mav);
		return mav;
	}
	
/* Main */ 
	
	//강한별
	@PostMapping("/EmailAuth")
	public ModelAndView emailAuth(ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("EmailAuth");	
		mav.addObject(ab);
		main.backController("EmailAuth",  mav);
		return mav;
	}
	//강한별
	@PostMapping("/Refusal")
	public ModelAndView refusal(ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("Refusal");	
		mav.addObject(ab);
		main.backController("Refusal",  mav);
		return mav;
	}
	
/* Project */	
	
	//강한별
	@PostMapping("/InviteMembers")
	public ProjectB inviteMember(ModelAndView mav, @ModelAttribute ProjectB pb) {
		System.out.println("HomeController/InviteMembers");	
		mav.addObject(pb);
		pro.backController("InviteMembers",  mav);
		return (ProjectB) mav.getModel().get(pb);
	}	
}
