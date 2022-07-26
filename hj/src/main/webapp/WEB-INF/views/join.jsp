<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>:: PMS :: Project Management System</title>
<style>
	@import url("/res/css/common.css");
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<link rel="preconnect" href="https://fonts.gstatic.com" />
<link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet" />
<style>
.button { margin: 0 0 0 169px;}
</style>
<script src="/res/js/common.js"></script>
<script>
function init() {
	lightBoxCtl('회원가입', true);
	let cbody = document.getElementById("cbody");
	let box = [];
	                   //type      name      placeholder  class   value
	box.push(createInput("text", "pmbCode", "회원코드", "box", ${pmbCode}, null, "something"));
	box.push(createInput("password", "pmbPassword", "패스워드", "box", "A1234!", null));	
	box.push(createInput("text", "pmbName", "회원이름", "box", "대식이", null));	
	box.push(createInput("text", "pmbEmail", "이메일", "box", "we2857@naver.com", null));	
	
	for(i=0; i<box.length; i++) {
		cbody.appendChild(box[i]);
	}
	
	let saved = cbody.innerHTML;
	cbody.innerHTML = (saved + "${level}" + "${classs}");
	
	box.push(createInput("button", "button", null, "btn button", "submit", null));	
	cbody.appendChild(box[4]);
	box[4].addEventListener("click", function(){
		regMemberCtl();
		});
	
	let form = document.getElementsByName("regForm")[0];
	let canvas = document.getElementById("canvas");
	form.appendChild(canvas);
	
	const message = "${message}";
	if(message != "") alert(message);
}
function regMemberCtl(){
	const memberInfo = [];
	memberInfo.push(document.getElementsByName("pmbCode"));
	memberInfo.push(document.getElementsByName("pmbPassword"));
	memberInfo.push(document.getElementsByName("pmbName"));
	memberInfo.push(document.getElementsByName("pmbLevel"));
	memberInfo.push(document.getElementsByName("pmbClass"));
	//memberInfo.push(document.getElementsByName("email"));
	
	if(isFormat(memberInfo[1][0].value, true)) {
		if(!isCharLength(memberInfo[1][0].value, 6)) {
			alert("6+"); 
			memberInfo[1][0].focus();
			return; 		
		}
	} else {
		alert("alphabets, numbers, symbols");
		memberInfo[1][0].focus();
		return;
	}
	
	if(isFormat(memberInfo[2][0].value, false)) {
		if(!isCharLength(memberInfo[2][0].value, 2, 5)) {
			alert("2~5");
			memberInfo[2][0].focus();
			return;
		}
	} else {
		alert("kor only");
		memberInfo[2][0].focus();
		return;
	}
	
	let form = document.getElementsByName("regForm")[0];	
	form.submit();
}
function getMessage(message){
	if(message != "" && message != null){
		alert(message);
	}
}
</script>
</head>
<body onLoad="init()" >
	<!-- Light Box Start -->
	<div id="canvas" class="canvas">
		<div id="light-box" class="light-box">
			<div id="image-zone" class="lightbox image"></div>
			<div id="content-zone" class="lightbox content">
				<div id="cheader"></div>
				<div id="cbody"></div>			
				<div id="cfoot"></div>
			</div>
		</div>	
	</div>
	<!-- Light Box End -->	
	<form name="regForm" action="Join" method="post"></form>	  
</body>
</html>
