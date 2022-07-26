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
<script src="/res/js/common.js"></script>
<script>
function init() {
	getAjaxJson("https://api.ipify.org", "format=json", "setPublicIp");
	lightBoxCtl('로그인창~~프매시', true);
	let cbody = document.getElementById("cbody");
	let box = [];
	                   //type      name      placeholder  class   value
	box.push(createInput("text", "pmbCode", "회원코드", "box", "20220707", null));
	box.push(createInput("password", "pmbPassword", "패스워드", "box", "A1234!", null));	
	box.push(createInput("button", "button", null, "btn button", "Sign In", null));
	box.push(createInput("button", "button2", null, "btn button", "Sign Up", null));	
	
	for(i=0; i<box.length; i++) {
		cbody.appendChild(box[i]);
	}
	
	box[2].addEventListener("click", function(){
		accessCtl();
		});
	
	box[3].addEventListener("click", function(){
		moveSignUp();
		});
		
	let form = document.getElementsByName("clientData")[0];
	let canvas = document.getElementById("canvas");
	form.appendChild(canvas);
	
	const message = "${message}";
	if(message != "") alert(message);
}
function accessCtl(){
	//alert("ACCESS : " + jsonData.ip);
	const accessInfo = [];
	accessInfo.push(document.getElementsByName("pmbCode"));
	accessInfo.push(document.getElementsByName("pmbPassword"));
	
	/* storeCode.length == 10 */ /* contains space 추가 */
	if(accessInfo[0][0].value.length == 0){
		accessInfo[0][0].focus();
		return;
	}
	if(accessInfo[1][0].value.length == 0) {
		accessInfo[1][0].focus();
		return;
	}
	
	/* Server 요청 : request << form */
	let form = document.getElementsByName("clientData")[0];
	form.appendChild(createInput("hidden", "aslPublicIp", null, null, jsonData.ip, null));
	form.action = "LogIn";
	form.submit();
}
function moveSignUp() {
	let form = document.getElementsByName("clientData")[0];
	form.action = "SignUp";
	form.method = "get";
	form.submit();
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
	<form name="clientData" method="post"></form> 	
</body>
</html>

