package com.han.jun.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.inter.ServicesRule;

@Service
public class Management implements ServicesRule{
	
	@Autowired
	private Auth auth;
	
	public Management() {}

	public void backController(String serviceCode, ModelAndView mav) {
		if(auth.isSession()) {
		System.out.println("Management/backController");
			switch(serviceCode) {
			case "MoveMgr":
				this.mainCtl(mav);
				break;
			}
		} else {
			mav.setViewName("home");
		}
	}
	
	public void backController(String serviceCode, Model model) {}
	
	private void mainCtl(ModelAndView mav) {
		System.out.println("Management/mainCtl");
		String page = "management";
		mav.setViewName(page);
	}
}
