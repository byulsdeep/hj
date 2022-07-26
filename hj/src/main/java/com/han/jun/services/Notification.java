package com.han.jun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.inter.ServicesRule;

@Service
public class Notification implements ServicesRule{

	@Autowired
	private Auth auth;
	
	public Notification() {}
	
	public void backController(String serviceCode, ModelAndView mav) {
		if(auth.isSession()) {
		System.out.println("Notification/backController");
			switch(serviceCode) {
			case "MoveAlert":
				this.mainCtl(mav);
				break;
			}
		} else {
			mav.setViewName("home");
		}
	}
	
	public void backController(String serviceCode, Model model) {}
	
	private void mainCtl(ModelAndView mav) {
		System.out.println("Notification/mainCtl");
		String page = "notification";
		mav.setViewName(page);;
	}
}
