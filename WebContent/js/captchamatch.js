function Captcha() { debugger;
	var alpha = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
	var i;
	for (i = 0; i < 5; i++) {
		var a = alpha[Math.floor(Math.random() * alpha.length)];
		var b = alpha[Math.floor(Math.random() * alpha.length)];
		var c = alpha[Math.floor(Math.random() * alpha.length)];
		var d = alpha[Math.floor(Math.random() * alpha.length)];
		var e = alpha[Math.floor(Math.random() * alpha.length)];
		var f = alpha[Math.floor(Math.random() * alpha.length)];
	}
	var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f;

	if (document.getElementById("mainCaptcha") != null)
		document.getElementById("mainCaptcha").value = code;

	if (document.getElementById("ErrorMessageDiv")!= null)
		document.getElementById("ErrorMessageDiv").style.display = "none";

}

function removeSpaces(string) {
	return string.split(' ').join('');
}
function validateLogin() {
	var CarrierId = document.getElementById("CarrierId").value;
	var agentid = document.getElementById("agentId").value;
	var password = document.getElementById("password").value;
	var txtInput = removeSpaces(document.getElementById("txtInput").value);
	var mainCaptcha = removeSpaces(document.getElementById('mainCaptcha').value);
	
	var message = "";
	if (txtInput == "" || txtInput == null)
		message = "Captcha  is not Blank.";
	if (password == "" || password == null)
		message = "password  is not Blank.";
	if (agentid == "" || agentid == null)
		message = "Agent ID is not Blank.";
	if (CarrierId == "" || CarrierId == null)
		message = "Carrier Id  is not Blank.";
	
	if (message == "") {
	    if (mainCaptcha != txtInput)
	    	message =  "Captcha does not match.";
	}
	
	if (message != "" && message != null && removeSpaces(message) != '') {
		document.getElementById("ErrorMessageDiv").style.display = "";
		document.getElementById("ErrorMessage").innerHTML = message;
		
		return false;
	} 
	
	document.getElementById("ErrorMessageDiv").style.display = "none";
	document.getElementById("ErrorMessage").innerHTML = "";
	
	if (message == "") {
		var surl = "validate?CarrierId=" + CarrierId + "&agentid=" + agentid
				+ "&password=" + password;
		validateUser(surl,"agentinfo","Agent ID");
	}
	
	return true;

}

function LoginUser() {

var agentid = document.getElementById("userName").value;
var password = document.getElementById("password").value;
var txtInput = removeSpaces(document.getElementById("txtInput").value);
var mainCaptcha = removeSpaces(document.getElementById('mainCaptcha').value);
var message = "";
if (txtInput == "" || txtInput == null)
	message = "Captcha  is not Blank.";
if (password == "" || password == null)
	message = "password  is not Blank.";
if (agentid == "" || agentid == null)
	message = "User ID is not Blank.";

if (message == "") {
    if (mainCaptcha != txtInput)
    	message =  "Captcha does not match.";
}

if (message != "" && message != null && removeSpaces(message) != '') {
	document.getElementById("ErrorMessageDiv").style.display = "";
	document.getElementById("ErrorMessage").innerHTML = message;
	
	return false;
} 

document.getElementById("ErrorMessageDiv").style.display = "none";
document.getElementById("ErrorMessage").innerHTML = "";
if (message == "") {
	var surl = "validateAdmin?userName=" + agentid + "&password=" + password;
	validateUser(surl,"listagents","User ID");
	
}

return false;
}

function validateUser(surl,location,id) {
	$.ajax({
				type : "GET",
				contentType : "plain/text",
				url : surl,
				success : function(data) {
					if (data == "true"){
						document.getElementById("ErrorMessageDiv").style.display = "none";
						document.getElementById("ErrorMessage").innerHTML = "";
						document.location = location;
						return true;
					}else{
						document.getElementById("ErrorMessageDiv").style.display = "";
						document.getElementById("ErrorMessage").innerHTML = id+" or Password is invalid.";
						return false;
					}
				}
	});
}