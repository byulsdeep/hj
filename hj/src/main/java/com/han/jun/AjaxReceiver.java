package com.han.jun;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.han.jun.services.Main;
import com.han.jun.services.Project;
import com.han.jun.beans.AulB;
import com.han.jun.beans.AuthB;
import com.han.jun.beans.ProListB;
import com.han.jun.beans.ProjectB;

@RestController
public class AjaxReceiver {
	@Autowired
	private Project pro;
	@Autowired
	private Main main;
	
/* Main */ 
	
	//강한별
	@RequestMapping(value = "/IsInvited", method = RequestMethod.POST)
	public List<AulB> isInvited(Model model, @ModelAttribute AuthB ab) {
		System.out.println("IsInvited");
		model.addAttribute(ab);
		this.main.backController("IsInvited", model);
		return (List<AulB>)model.getAttribute("InviteList");
	}
	//강한별
	@RequestMapping(value = "/IsInvited2", method = RequestMethod.POST)
	public List<AulB> isInvited2(Model model, @ModelAttribute AuthB ab) {
		System.out.println("IsInvited2");
		model.addAttribute(ab);
		this.main.backController("IsInvited2", model);
		return (List<AulB>)model.getAttribute("SentList");
	}
	//강한별
	@RequestMapping(value = "/GetProjectList", method = RequestMethod.POST)
	public List<ProListB> getProjectList(Model model, @ModelAttribute AuthB ab) {
		System.out.println("GetProjectList");
		model.addAttribute(ab);
		this.main.backController("GetProjectList", model);
		return (List<ProListB>)model.getAttribute("ProjectList");
	}
	//강한별
	@RequestMapping(value = "/GetProjectMembers", method = RequestMethod.POST)
	public String getProjectMembers(Model model, @ModelAttribute ProjectB pb) {
		System.out.println("GetProjectMembers");
		model.addAttribute(pb);
		this.main.backController("GetProjectMembers", model);
		return (String) model.getAttribute("ProjectMembers");
	}	
	
/* Project */ 
	
	//강한별
	@PostMapping("/InsProject")
	public List<AuthB> insProject(Model model, @ModelAttribute ProjectB pb) {
		System.out.println("InsProject");	
		model.addAttribute(pb);	
		this.pro.backController("InsProject", model);
		return (List<AuthB>)model.getAttribute("MemberList");
	}	
}
