let jsonData; 

function getAjaxJson(jobCode, clientData, fn){
	const ajax =  new XMLHttpRequest();
	const action = (clientData != "")? (jobCode + "?" + clientData):jobCode;
	
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			window[fn](ajax.responseText);
		}
	};
	ajax.open("get", action);
	ajax.send();
}

/* AJAX :: POST */
function postAjaxJson(jobCode, clientData, fn){
	const ajax =  new XMLHttpRequest();
	
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			window[fn](ajax.responseText);
		}
	};
	
	ajax.open("post", jobCode);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(clientData);
}

/* Public IP Saving */
function setPublicIp(ajaxData){
	jsonData = JSON.parse(ajaxData);
}

function logOut() {
	let form = document.getElementsByName("form")[0];
	form.action = "LogOut";
	form.submit();	
}



