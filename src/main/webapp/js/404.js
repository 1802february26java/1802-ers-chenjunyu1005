$(function(){
	//global variable
	time = setInterval("showAd()",1000);
})
function showAd(){
//		    	document.getElementById("showad").style.display="block";
   $("#showad").slideDown(3000);
	clearInterval(time);
	setInterval("hiddenAd()",3000);
}
// function hiddenAd(){
// //		    	document.getElementById("showad").style.display="none";
// 	$("#showad").slideUp(3000);
// 	clearInterval(time);
	
// }