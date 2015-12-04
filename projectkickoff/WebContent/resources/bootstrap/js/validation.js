/**
 * 
 */

	function validate()/*to validate the form*/
	{
		if ( Date.parse(document.frm.sowstartday.value) > Date.parse(document.frm.sowstopday.value)) {
			alert("Invalid Date Range!\nSOW Start Date cannot be after SOW Stop Date!");
			return false;
			}

		if ( isNaN(Date.parse(document.frm.sowstartday.value)) ) {
			alert("Please Enter Valid Date");
			document.frm.sowstartday.focus();
			return false;
			}

		if ( isNaN(Date.parse(document.frm.sowstopday.value)) ) {
			alert("Please Enter Valid Date");
			document.frm.sowstopday.focus();
			return false;
			}
		if ( document.getElementById('signed').checked == false ){
			if ( isNaN(Date.parse(document.frm.sowappday.value)) ) {
				alert("Please Enter Valid Date");
				document.frm.sowappday.focus();
				return false;
				}		
			}
		if(document.getElementById("redmineUrl").value.trim()=="")
			{
			alert("Please enter project redmine url..!!");
				 document.getElementById("redmineUrl").focus(); 
			}
		}
	
	
	
	
	function search(){
		if (document.find.name.value.trim() == "") {
			alert("Enter search text please");
			document.find.name.focus();
			return false;
		}
	}
	
	
	
	function userAlreadyExists(){
		alert("Project already exists with the same Identification Number ");
	}
	
	
	
	
	function projectAlreadyAprroved(verifiedby){
		alert("Project already approved by "+ verifiedby);
	}
	
	
	
	function disablefield()
	{
	if ( document.getElementById('signed').checked == true ){
	document.getElementById('datepicker').disabled = true;
	document.getElementById('datepicker').value ="";	
	}
	else if (document.getElementById('signed').checked == false ){		
	document.getElementById('datepicker').disabled = false;}
	}
	
	
	
	function saveOrUpdateMessage(message){
		alert(message);
	}