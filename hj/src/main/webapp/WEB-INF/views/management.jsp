<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>:: PMS Management ::</title>
<style>
	@import url("/res/css/common.css");
</style>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
<link rel="preconnect" href="https://fonts.gstatic.com" />
<link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet" />
<script src="/res/js/common.js"></script>
<script>
let clientData;
let k = 0;
let projectThumb = [];

function init(){
	const message = "${message}";
	if(message != "") alert(message);
	
	postAjaxJson("IsInvited", null, "callBack");
	postAjaxJson("IsInvited2", null, "callBack2");
	postAjaxJson("GetProjectList", null, "callBack3");
}
function callBack(ajaxData) {
	let inviteList = JSON.parse(ajaxData);
	
	if(inviteList != null && inviteList != "") {
		//alert("프로젝트 초대 확인");
		//createInviteList(inviteList);
	}
}
function callBack2(ajaxData) {
	let sentList = JSON.parse(ajaxData);
	
	if(sentList != null && sentList != "") {
		//createSentList(sentList);
	}
}
function callBack3(ajaxData) {
	let projectList = JSON.parse(ajaxData);
	
	if(projectList != null && projectList != "") {	
		createProjectList(projectList);
	}
}
function callBack4(ajaxData) {
	let memberCount = JSON.parse(ajaxData);

	projectThumb[k].insertBefore(createDiv("memberCount" + k, "memberCount", "멤버수 :" + memberCount, null), projectThumb[k].children[2]);
	k++;
}
function createProjectList(projectList) {
	let projectDiv = document.getElementById("projectDiv2");
	let subProjectList = createDiv("subProjectList", null, "", null);
	
	let up = createInput("button", "up", null, "btn button", "▲");
	projectDiv.appendChild(up);
	up.addEventListener("click", function(){
		moveUp();
		});
	let down = createInput("button", "down", null, "btn button", "▼");
	projectDiv.appendChild(down);
	down.addEventListener("click", function(){
		moveDown();
		});
	for(i=0; i<projectList.length; i++) {	
		let box = [];
		
		postAjaxJson("GetProjectMembers", "projectCode=" + projectList[i].projectCode, "callBack4");
		
		projectThumb[i] = createDiv("projectThumb" + i, "projectThumbOn", "", null);
		box.push(createDiv("projectName" + i, "bigAss", projectList[i].projectName, null)); 
		box.push(createDiv("managerName" + i, "managerName", "매니저 :" + projectList[i].managerName, null));	
		
		box.push(createDiv("period" + i, "period", "기간: " + projectList[i].startDate.substr(0,10) + " ~ " + projectList[i].endDate.substr(0,10), null));
		box.push(createDiv("projectComment" + i, "projectComment", "상세: " + projectList[i].projectComment, null));
		box.push(createInput("button", "member" + i, null, "btn button", "MEMBERS"));
		box[4].addEventListener("click", function(){
			moveMgr();
			});
		box.push(createInput("button", "job" + i, null, "btn button", "JOBS"));
		box[5].addEventListener("click", function(){
			moveMgr();
			});
		box.push(createInput("button", "progress" + i, null, "btn button", "PROGRESS"));
		box[6].addEventListener("click", function(){
			showProgress();
			});	
		for(j=0; j<box.length; j++) {		
			projectThumb[i].appendChild(box[j]);
		}	
		projectThumb[i].setAttribute("class", (projectThumb.length >= 4) ? "projectThumbOff": "projectThumbOn");
		subProjectList.appendChild(projectThumb[i]);
	}
	projectDiv.appendChild(subProjectList);
}
function showProgress() {
	let progressBar = document.getElementById("progressBar");
	progressBar.innerHTML = "<img src='res/images/radialProgressBar.png'></img>";
}
function moveUp() {
	if(projectThumb.length >= 4) {
		let a = projectThumb[projectThumb.length-1];	
		projectThumb.splice(projectThumb.length-1,1);	
		projectThumb.unshift(a);
		for(i=0; i<projectThumb.length; i++) {	
			projectThumb[i].setAttribute("class", (i >= 3) ? "projectThumbOff": "projectThumbOn");
		}		
	}
}
function moveDown() {
	if(projectThumb.length >= 4) {
		
		let a = projectThumb[0];
		projectThumb.splice(0,1);		
		projectThumb.push(a);
		
		for(i=0; i<projectThumb.length; i++) {	
			projectThumb[i].setAttribute("class", (i >= 3) ? "projectThumbOff": "projectThumbOn");
		}
	}
}
function createSentList(sentList) {
	let sentDiv = document.getElementById("sentDiv");
	let box = [];
	sentDiv.appendChild(createDiv("bigAss", null, "보낸 초대", null));
	for(i=0; i<sentList.length; i++) {
		
		box.push(createDiv(null, "box", "초대대상자", null));
		box.push(createInput("text", "receiverName", null, "box", sentList[i].receiverName, "ang"));
		box.push(createDiv(null, "box", "초대일자", null));
		box.push(createInput("text", "inviteDate", null, "box", sentList[i].inviteDate, "ang"));
		box.push(createDiv(null, "box", "만료일자", null));
		box.push(createInput("text", "expireDate", null, "box", sentList[i].expireDate, "ang"));
		box.push(createDiv(null, "box", "인증여부", null));
		box.push(createInput("text", "authResult", null, "box", (sentList[i].authResult == "AU") ? "인증완료" : "미인증" , "ang"));
		box.push(document.createElement("br"));
		box.push(document.createElement("br"));
		for(j=0; j<box.length; j++) {
			sentDiv.appendChild(box[j]);
		}
	}	
}
function createInviteList(inviteList) {
	let inviteDiv = document.getElementById("inviteDiv");

	inviteDiv.appendChild(createDiv("bigAss", null, "받은 초대", null));
	for(i=0; i<inviteList.length; i++) {
		let box = [];
		box.push(createDiv(null, "box", "초대자", null));
		box.push(createInput("text", "senderName", null, "box", inviteList[i].senderName, "ang"));
		box.push(createDiv(null, "box", "초대일자", null));
		box.push(createInput("text", "inviteDate", null, "box", inviteList[i].inviteDate, "ang"));
		box.push(createDiv(null, "box", "만료일자", null));
		box.push(createInput("text", "expireDate", null, "box", inviteList[i].expireDate, "ang"));
		box.push(createDiv(null, "box", "인증여부", null));
		box.push(createInput("text", "authResult", null, "box", (inviteList[i].authResult == "AU") ? "인증완료" : "미인증" , "ang"));
		box.push(createInput("button", "inviteNum", null, "btn button", "인증", null));
		box[8].addEventListener("click", function(){
			yesOrNo();
			});
		for(j=0; j<box.length; j++) {
		inviteDiv.appendChild(box[j]);
		}
	}	
}

</script>
</head>
<body onLoad="init()">
<br><br><br>
<section>  
  <article>	
  	<div>
  		<div id="projectDiv2">
  		</div>
  	</div>
  	<div>
  		<div id="progressBar">			
  		</div>
  	</div>
  </article>
</section>
<footer>
  <p>공지사항</p>
</footer>
<nav>
  <ul>
    <br>
    <li><i id="default" class="fa-solid fa-chalkboard-user" onClick="moveMain()" ></i></li>
    <br>
    <li><i id="default" class="fa-solid fa-bell" onClick="moveAlert()" ></i></li>
    <br>
    <li><i id="highlight" class="fa-solid fa-wrench" onClick="moveMgr()" ></i></li>
    <br>
    <li><i id="default" class="fa-solid fa-user" onClick="moveMyPage()" ></i></li>
  </ul>
</nav>
<header>
  <span>
	${accessInfo.pmbName}(${accessInfo.pmbLevelName})님 어서오세요&nbsp&nbsp&nbsp&nbsp소속: [${accessInfo.pmbClassName}]&nbsp&nbsp&nbsp&nbsp접속시간: [${accessInfo.aslDate}]
	<input type="button" name="accessOut" value="로그아웃" onClick="logOut()" />
  </span>
</header>
<i id="newProject" class="fa-solid fa-folder-plus" onClick="moveProject()" ></i>
<form name="form" method="post"></form>	
</body>
</html>