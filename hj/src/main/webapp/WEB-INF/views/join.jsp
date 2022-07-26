<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>::회원가입::</title>

<script>
function init() {
	//let pmbCode = document.getElementsByName("pmbCode")[0];
	//pmbCode.value=;
}
function join(){
	const form = document.getElementsByName("Join")[0];
	
	form.submit();
}
</script>

</head>
<body onLoad="init()">
<form action = "Join" name="Join" method="post">
<input type="text" name="pmbCode" value ="${pmbCode}" readonly="true" >
<input type="password" name="pmbPassword" placeholder="비밀번호 입력" value="A1234!" >
<input type="text" name="pmbName" placeholder="이름 입력" value="한별쿤" >
<input type="text" name="pmbEmail" placeholder="이메일 입력" value="we2857@naver.com" >
<select name="pmbLevel">
<option value="S">학생맨~</option>
<option value="T">선생맨~</option>
<option value="E">직원맨~</option>
</select>

<select name="pmbClass">
<option value="F01">1반</option>
<option value="F02">2반</option>
<option value="F03">3반</option>
</select>
<button onClick="join()">
회원가입
</button>
</form>
</body>
</html>