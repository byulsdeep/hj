package com.han.jun;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.beans.AuthB;
import com.han.jun.services.Auth;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	Auth auth;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model ) {
		logger.info("서버온!");

		return "landing";
	}
	@RequestMapping(value = "/First", method = RequestMethod.GET)
	public ModelAndView first(ModelAndView mav, @ModelAttribute AuthB ab) {
		mav.addObject(ab);
		auth.backController("First", mav);
		return mav;
	}
	@RequestMapping(value = "/LogIn", method = RequestMethod.POST)
	public ModelAndView logIn(HttpServletRequest req,ModelAndView mav, @ModelAttribute AuthB ab) {
		mav.addObject(ab);
		ab.setAslPrivateIp(req.getRemoteAddr());
		auth.backController("LogIn", mav);
		return mav;
	}
	@RequestMapping(value = "/SignUp", method = RequestMethod.GET)
	public ModelAndView signUp(ModelAndView mav) {
		auth.backController("SignUp", mav);
		return mav;
	}
	@RequestMapping(value = "/Join", method = RequestMethod.POST)
	public ModelAndView join(ModelAndView mav, @ModelAttribute AuthB ab) {
		mav.addObject(ab);
		auth.backController("Join", mav);
		return mav;
	}
	@RequestMapping(value = "/LogOut", method = RequestMethod.POST)
	public ModelAndView logOut(ModelAndView mav, @ModelAttribute AuthB ab) {
		System.out.println("HomeController/LogOut");
		mav.addObject(ab);
		auth.backController("LogOut", mav);
		return mav;
	}






}
