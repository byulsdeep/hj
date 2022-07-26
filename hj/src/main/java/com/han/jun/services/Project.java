package com.han.jun.services;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.han.jun.beans.AulB;
import com.han.jun.beans.AuthB;
import com.han.jun.beans.ProMemB;
import com.han.jun.beans.ProjectB;
import com.han.jun.inter.ServicesRule;
import com.han.jun.utils.Encryption;
import com.han.jun.utils.ProjectUtils;

@Service
public class Project implements ServicesRule{

	@Autowired
	private SqlSessionTemplate session;
	@Autowired
	private Encryption enc;
	@Autowired 
	private ProjectUtils pu;
	@Autowired
	private Auth auth;
	@Autowired
	private JavaMailSenderImpl mail;

	public Project() {}

	public void backController(String serviceCode, ModelAndView mav) {
		if(auth.isSession()) {
			System.out.println("Project/backController");
			switch(serviceCode) {
			case "MoveProject":
				this.mainCtl(mav);
				break;
			case "InviteMembers":
				this.regProjectMembersCtl(mav);
				break;
			}
		} else {
			mav.setViewName("home");
		}
	}
	public void backController(String serviceCode, Model model) {
		if(auth.isSession()) {
			System.out.println("Project/backController");
			switch(serviceCode) {
			case "InsProject":
				this.regProjectCtl(model);
				break;
			}	
		}
	}

	private void mainCtl(ModelAndView mav) {
		System.out.println("Project/mainCtl");
		String page = "project";
		mav.setViewName(page);
	}

	@Transactional
	private void regProjectCtl(Model model) {
		System.out.println("Project/regProjectCtl");
		List<AuthB> memberList = null;
		AuthB au = null;
		ProjectB pb = ((ProjectB)model.getAttribute("projectB"));

		if(this.convert(this.session.insert("insProject", pb))) {
			System.out.println("insert success");
			memberList = this.session.selectList("getMemberList");

			for(AuthB ab : memberList) {
				try {
					ab.setPmbName(this.enc.aesDecode(ab.getPmbName(), ab.getPmbCode()));
					ab.setPmbEmail(this.enc.aesDecode(ab.getPmbEmail(), ab.getPmbCode()));
					System.out.println(ab.getPmbClassName());
					System.out.println(ab.getPmbLevelName());
					System.out.println(ab.getPmbEmail());
				} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
						| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
						| BadPaddingException e) {e.printStackTrace();}
			}
		} else {
			System.out.println("insert fail");
			memberList = new ArrayList<AuthB>();
			au = new AuthB();
			au.setMessage("생성실패");
			memberList.add(au);
		}		
		model.addAttribute("MemberList", memberList);
	}

	@Transactional
	private void regProjectMembersCtl(ModelAndView mav) {
		System.out.println("regProjectMembersCtl");
		ProjectB pb = ((ProjectB)mav.getModel().get("projectB"));
		AuthB session = null;
		try {
			session = (AuthB)this.pu.getAttribute("accessInfo");
		} catch (Exception e1) {e1.printStackTrace();}
		
		String page = "project";
		mav.setViewName(page);

		int result =  this.session.update("insProjectMembers", pb);
		System.out.println(result);
		
		ProMemB pm = new ProMemB();
		pm.setProjectCode(pb.getProjectCode());
		pm.setPmbCode(session.getPmbCode());
		this.session.insert("insSelf", pm);

		String subject = "[" + pb.getProjectName() + "] 프로젝트 초대장";

		String from = "byulzdeep@outlook.com";
		String[] to = new String [(pb.getProjectMembers().size())];
		String[] authCode = new String [(pb.getProjectMembers().size())];
		String[] content = new String [(pb.getProjectMembers().size())];
		String[] inviteDate = new String [(pb.getProjectMembers().size())];
		String projectCode = pb.getProjectCode();
		String key = "BDGames";
		
		MimeMessage mime = mail.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime,"UTF-8");
		try {
			helper.setFrom(from);
			helper.setSubject(subject);
		} catch (MessagingException e1) {e1.printStackTrace();}

		AulB aul;
		
		for(int i=0; i < to.length; i++) {
			to[i] = pb.getProjectMembers().get(i).getPmbEmail();
			
			aul = new AulB();
			aul.setProjectCode(projectCode);
			aul.setSender(session.getPmbCode());
			aul.setReceiver(pb.getProjectMembers().get(i).getPmbCode());
			this.session.insert("insAul", aul);
			inviteDate[i] = this.session.selectOne("getInviteDate", aul);
			
			try {
				authCode[i] = this.enc.aesEncode(projectCode + ":" + to[i] + ":" + inviteDate[i] + ":" + aul.getSender(), key);
				content[i] = "<!DOCTYPE html>\r\n"
						+ "<html lang=\"en\">\r\n"
						+ "<head>\r\n"
						+ "    <meta charset=\"UTF-8\">\r\n"
						+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
						+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
						+ "    <title>Document</title>\r\n"
						+ "</head>\r\n"
						+ "<style>\r\n"
						+ "    .center { \r\n"
						+ "			position:absolute; \r\n"
						+ "			top:50%; \r\n"
						+ "			left:50%; \r\n"
						+ "			transform:translate(-50%, -50%);		 \r\n"
						+ "		   	text-align:center; \r\n"
						+ "		   	}\r\n"
						+ "</style>\r\n"
						+ "<body>\r\n"
						+ "    <div class=\"center\">\r\n"
						+ "        <img src=\"https://blogger.googleusercontent.com/img/a/AVvXsEhlvbsCOUntQY8Dh6NyZ3Wr0-RPlFMOpHLI5SdQOEWUb51fneTu3i4ER8NX0CfDVSQrltk_tnr1o52NTl2sL6iEq3D9c49Y7Py78NrcA2SUt7eMP0kI690xiy9wY_Cm0hLbcGjnZ7faAp2nHcVbcip4pVQ9-HoYnTzTEENAcXo1qHpQlu2cpqtDV-KKtQ\">\r\n"
						+ "        <h2>\r\n"
						+ pb.getProjectMembers().get(i).getPmbName()
						+ "님을 "
						+ "[" + pb.getProjectName()
						+ "] 프로젝트에 초대합니다.   \r\n"
						+ "        </h2>\r\n"
						+ "        <h3>\r\n"
						+ pb.getProjectComment()
						+ "        </h3>\r\n"
						+ "        <h2>\r\n"
						+ "            프로젝트에 참가하시려면 아래 주소에서 로그인 후 인증해주세요"
						+ "        </h2>\r\n"
						+ "        <h2>\r\n"
						+ "            192.168.0.66"
						+ "        </h2>\r\n"
						+ "        <h2>\r\n"
						+ "            인증코드: [ "
						+ authCode[i] + " ]"
						+ "        </h2>\r\n"
						+ "    </div>\r\n"
						+ "</body>\r\n"
						+ "</html>";
				helper.setTo(to[i]);
				helper.setText(content[i], true);
				mail.send(mime);
		
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
					| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
					| BadPaddingException | MessagingException e) {e.printStackTrace();}
		}
	}

	private boolean convert(int result) {
		return result >= 1 ? true : false;
	}
}
