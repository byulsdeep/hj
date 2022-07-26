<%@ page session="false" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<script src="/res/js/common.js"></script>
<script>

function start(){
	getAjaxJson("https://api.ipify.org", "format=json" , "setPublicIp");
	
}

function accessCtl(){
	const accessInfo = [];
	accessInfo.push(document.getElementsByName("pmbCode"));
	accessInfo.push(document.getElementsByName("pmbPassword"));
	
	/* storeCode.length == 10 */
	if(accessInfo[0][0].value.length != 8){
		alert("NCHAR(8)");
		return;
	}
	
	/* Server 요청 : request << form */
	let form = document.getElementsByName("accessForm")[0];
	const publicIp = document.createElement("input");
	publicIp.setAttribute("type","hidden");
	publicIp.setAttribute("name","aslPublicIp");
	publicIp.setAttribute("value",jsonData.ip);
	//publicIp.setAttribute("value","183.91.223.106");
	
	form.appendChild(publicIp);
	
	form.submit();
}

function getMessage(message){
	if(message != "" && message != null){
		alert(message);
	}
}

function moveSignUp(){
	
	let form = document.getElementsByName("signUp")[0];
	form.method = "get";
	
	form.submit();
}
</script>


</head>
<body onLoad="start()">
	<h1>로그인창~~</h1>
	<h1>프매시</h1>
	<form name="accessForm" action="LogIn" method="post">        
		<input type="text" name="pmbCode" placeholder="Member Code" value="20220705"/>
		<input type="password" name="pmbPassword" placeholder="Password" value="A1234!"/>
		<input type="button" value="System Access" onClick="accessCtl()"/>
	</form>
	<form name="signUp" action="SignUp" method="post">
		<button onClick= "moveSignUp()">회원가입</button>
	</form>
</body>
</html>
