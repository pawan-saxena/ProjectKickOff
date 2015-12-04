<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link href="resources//bootstrap/css/bootstrap-theme.css"
	rel="stylesheet" type="text/css" />
<link href="resources/bootstrap/css/bootstrap-theme.css.map"
	rel="stylesheet" type="text/css" />
<link href="resources//WEB-INF/bootstrap/css/bootstrap-theme.min.css"
	rel="stylesheet" type="text/css" />
<link href="resources/bootstrap/css/bootstrap-themecss" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/bootstrapcss.map" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/formapp.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script src="resources/bootstrap/js/validation.js"></script>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
#header {
	background-image: url("resources/bootstrap/images/bg-header.png");
	height: auto;
}

#signed {
	margin-top: 2%;
}
</style>

<script>
  $(document).ready(function() { 
	  var pickerOpts = {
			        dateFormat:"d MM yy"
			     };  
    $('#datepicker').datepicker(pickerOpts);
    $('#datepicker3').datepicker(pickerOpts);
    $('#datepicker4').datepicker(pickerOpts);
  });
  </script>

<script>
//disabling enabling calender
window.onload = function() {
	disablefield();	
	document.getElementById('signed').onchange = disablefield;
	}
</script>
</head>

<body>

<c:if test="${checkUrl=='invalid'}" >	
		<script type="text/javascript">
		alert("Please Enter Valid Project Redmine Url..")
		</script>
		 </c:if>
		 <c:if test="${checkUrl=='duplicate'}">
		 <script type="text/javascript">
		alert("Project already exists with the same WSR id.Enter a different Project Redmine Url.")
		</script>
		 </c:if>
		 <c:if test="${checkUrl=='noWSR'}" >	
		<script type="text/javascript">
		alert("No WSR tickets could be found for this project please enter another valid Project Redmine URL.")
		</script>
		 </c:if>
	<div class="row" style="width: auto">

		<div class="col-sm-12" id="header">
			<a href="http://www.optimusinfo.com/" target="_blank"><img src="resources/bootstrap/images/optimusinfologo.png"
				id="logoset"></img></a>
			<h3 style="color: white; float: left;">
				<b>Project Kickoff Form</b>
			</h3>
		<div style="margin-left: 55px;">
				<form role="form" name="find" action="show" method="get"
					id="formsearch" onsubmit="return search()">
					<div style="width: 340px">
						<input class="form-control" id="searchbutton" type="text"
							name="name" placeholder="Search" style="padding-left: 10%;">
						<button class="btn btn-primary" type="submit" id="indexsearchbtn" onclick="this.value='';">Search</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<br>
	<div class="container" id="divCSS"><!-- <!-- style="width: 90%;border: 2px solid #49A0C1;border-radius: 25px;" --> 
		<div class="col-sm-9" style="padding-right: 50px;font-size: 15px;font-family: inherit;">			
			<c:choose>
			<c:when test="${edit=='error'}">
					<script type="text/javascript">
					alert("Provided Project Id is invalid.Please do use the provided Links to do the navigation!!");
					</script>
				</c:when>
				<c:when test="${edit=='NoProjectInDb'}">
					<script type="text/javascript">
					alert("No Project is found in database for provided ProjectId.Please do use the provided Links to do the navigation!!");
					</script>
				</c:when>
				<c:when test="${edit=='Yes'}">
					<% request.setAttribute("action", "update"); %>
				</c:when>
				<c:when test="${edit==null}">
					<% request.setAttribute("action", "save"); %>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			
			<form:form action="${action}" method="post"
				modelAttribute="projectView" role="form" name="frm"
				onsubmit="return validate()" cssStyle="margin-left:10px;">
				
				
					<div class="row">
					<h3>
						<b>Overview of Opportunity</b>
					</h3>
					<br>
					<!--Edit this field  -->
					<div id="leftcolumn">
					<form:label path="redmineUrl" >Project Redmine Url </form:label>
						<form:input  required="true" path="redmineUrl"
							 class="form-control" id="redmineUrl" />
					<br>
					
					</div>
					<div id="rightcolumn">
						<form:hidden path="id" />
						<form:hidden path="approve" />
						<form:hidden path="verifiedBy" />
						<label for="id"> Project Identification Number </label>
						<form:input path="projectIdentificationNumber"
							class="form-control"  required="true" id="id" readonly="true"/>
					</div>
				</div>
				
				
				
				
				<div class="row" style="margin-top: 15px">
					
					<div id="rightcolumn">
						<form:label path="potentialopp" for="potential">ZOHO CRM Potential </form:label>
						<form:input required="true" path="potentialopp" class="form-control" 
							  id="potential" />
					</div>
					
					
					<div id="leftcolumn">
						<form:label path="salesperson" for="salesperson" >Sales Person</form:label>
						<form:select path="salesperson" class="form-control"
							id="salesperson">
							<form:option value="Pankaj Agarwal">Pankaj
							Agarwal</form:option>
							<br>
							<form:option value="Lynn Abdelmesseh">Lynn Abdelmesseh</form:option>
							<br>
							<form:option value="Vipul Kushrestha">Vipul Kushrestha</form:option>
							<br>
							<form:option value="Eric Meyer">Eric Meyer</form:option>
							<br>
							<form:option value="Jerry Bauer">Jerry Bauer</form:option>
						</form:select>
					</div>
				</div>
				
				
								
				
				<br>
				<div class="row" style="margin-top: 15px">
					
					<div id="leftcolumn">

						<form:label path="natureofproject" for="nature">Name of the Project </form:label>
						<form:input  required="true" path="natureofproject"
							 class="form-control" id="nature" readonly="true" />
					</div>
					
					
					
				</div>
				<br>

				<div class="row">
					<form:label path="projectsumm">Project Scope Summary</form:label>
					<br>
					<form:textarea rows="3" cols="50" path="projectsumm"
						class="form-control"></form:textarea>
				</div>
				<br>
				<div class="row">
					<form:label path="projectrisk">Any Project Risks or Implied Customer Commitments </form:label>
					<br>
					<form:textarea rows="3" cols="50" path="projectrisk"
						class="form-control"></form:textarea>
				</div>
				<br>
				
				<div class="row">
					<h3>
						<b>Commercial Highlights</b>
					</h3>
					<br>
					<div id="leftcolumn">
						<form:label path="sowdolar" for="sowdolar">Total SOW Dollar Value </form:label>
						<form:input  required="true" path="sowdolar" id="sowdolar" class="form-control" />
					</div>
					<div id="rightcolumn">
						<form:label path="hourlybillingrate" for="hourlybillingrate">Hourly Billing Rate </form:label>
						<form:input   required="true" path="hourlybillingrate" id="hoourlybillingrate"
							class="form-control" />
					</div>
				</div>
				<br>
				<div class="row">
					<form:label path="paymentterms">Payment Terms </form:label>
					<br>
					<form:textarea rows="3" cols="50" path="paymentterms"
						class="form-control"></form:textarea>
				</div>
				<br>
				<div class="row">
					<div id="leftcolumn">
						<form:label path="sowcopy"> Location where the signed SOW soft copy is saved </form:label>
						<form:input   required="true" path="sowcopy" class="form-control"
							placeholder="e.g, Location on the google drive" />
					</div>
				</div>
				<br>
				<div class="row">
					<h3>
						<b>Customer Contact Details</b>
					</h3>
					<br>
					<div id="leftcolumn">
						<form:label path="delcontact" for="delcontact">Name of the Customer Project Delivery Contact </form:label>
						<form:input   required="true" path="delcontact" class="form-control" id="delcontact" />
					</div>
					<div id="rightcolumn">
						<form:label path="maildelcontact" for="mcd">Mail ID of the Customer Project Delivery Contact </form:label>
						<form:input   required="true" path="maildelcontact" type="email" id="mcd"
							class="form-control" />
					</div>
				</div>
				<br>
				<div class="row">
					<div id="leftcolumn">
						<form:label path="comcontact" for="com">Name of the Customer Project Commercial Contact </form:label>
						<form:input path="comcontact"  required="true"  id="com" class="form-control" />
					</div>

					<div id="rightcolumn">
						<form:label path="mailcomcontact" for="mcom">Mail ID of the Customer Project Commercial Contact </form:label>
						<form:input path="mailcomcontact" type="email"
							 required="true" class="form-control" id="mcom" />
					</div>
				</div>
				<br>
				<div class="row">
					<div id="leftcolumn">
						<form:checkbox path="sowasignedday" id="signed" />
						<b>Sow Not Signed</b>
					</div>
					<div id="rightcolumn">
						<form:label path="sowappday" for="sowappday">SOW Approval Date </form:label>
						<form:input path="sowappday"  required="true" class="form-control" id="datepicker" readonly="true"/>
					</div>

				</div>
				<br>
				<div class="row">
					<div id="leftcolumn">
						<form:label path="sowstartday" for="sowstartday">SOW Start On</form:label>
						<form:input path="sowstartday"  required="true" id="datepicker4"
							class="form-control" readonly="true"/>
					</div>
					<div id="rightcolumn">
						<form:label path="sowstopday" for="sowstopday">SOW Stop Date </form:label>
						<form:input path="sowstopday"  required="true" id="datepicker3"
							class="form-control" readonly="true"/>
					</div>

				</div>
				<br>
				<div class="row">
								
					<br><input type="submit" class="btn btn-primary"
						 value="Submit" />
				</div>
				<c:if test="${ProjectExists=='Yes'}">
					<script>
				userAlreadyExists();
				</script>
				</c:if>
				<c:if test="${projectAprroved=='yes'}">
					<script>
				projectAlreadyAprroved("${approvedBy}");
				</script>
				</c:if>
				<c:if test="${saveOrUpdate=='save'}">
					<script>
				saveOrUpdateMessage("Project is saved successfully");
				</script>
				</c:if>
				<c:if test="${saveOrUpdate=='update'}">
					<script>
				saveOrUpdateMessage("Project is updated successfully");
				</script>
				</c:if>
			</form:form>
		</div>
<div class="col-sm-3" id="indexview">

			<fieldset>
				<h5>
					<b>View All Projects Details</b>
				</h5>
				<a href="showall" class="btn btn-primary" id="viewallbtnindex">View	All</a>
			</fieldset>
		</div>
		
	</div>
</body>
</html>