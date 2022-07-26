<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="refresh" content="0;url=http://192.168.0.103/First" />
<script src="/res/js/common.js">
</script>
<script>
function callServer(a){
	jsonData = JSON.parse(a);
	const publicIp = "aslPublicIp=" + jsonData.ip;
	
	location.href = "http://192.168.0.66/First?" + publicIp;
}
</script>

<meta charset="UTF-8">
<title>::landing::</title>
</head>
<body>

<script>
getAjaxJson("https://api.ipify.org", "format=json", "callServer"); 
</script>
	
</body>
</html>