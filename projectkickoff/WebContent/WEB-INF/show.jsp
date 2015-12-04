<%@page import="mypack.Project"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page session="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link href="resources//bootstrap/css/bootstrap-theme.css" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/bootstrap-theme.css.map" rel="stylesheet"
	type="text/css" />
<link href="resources//WEB-INF/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/bootstrap-themecss" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/bootstrapcss.map" rel="stylesheet"
	type="text/css" />
<link href="resources/bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
	<link href="resources/bootstrap/css/formapp.css" rel="stylesheet"
	type="text/css" />
<style>

#header {
	background-image: url("resources/bootstrap/images/bg-header.png");

    height: auto;
}


</style>	
<script>

function projectIsVarifiedBy(verifiedBy){
	
	alert("Project is already verfied by "+verifiedBy);
	window.close();
	
}

function projectIsVarified(verifiedBy){
	alert("Project is verfied successfully");
	window.close();
}

</script>

</head>
<body>
<c:if test="${ifExists=='notFound'}">
<script type="text/javascript">
alert("No project found in database with provided information.!")
</script>
</c:if>
<c:if test="${ifExists=='invalidURL'}">
<script type="text/javascript">
alert("Please enter valid RedmineURL.!")
</script>
</c:if>
  <div id="header" class="col-sm-12">    
   <a href="http://www.optimusinfo.com/" target="_blank"><img src="resources/bootstrap/images/optimusinfologo.png" id="logoset"></img> </a>  
  <h3 style="color:white;float:left;"><b>Project Information</b></h3>
   <a href="/projectkickoff/" class="btn btn-primary" id="createbtn">Create a Project</a>

  <a href="showall" class="btn btn-primary" id="viewallbtn">View All Projects</a></div>
		
	<br><br><br><div class="container"style="width: 70%;margin-left: 7%;">
				
		<%-- <c:if test="${project.size()==0}">
		
		<h1 style="text-align:left">No Project Found here</h1>
			</c:if>		
 --%>
<c:if test="${verified=='Show'}" >	
		<fieldset>
			<div id="CSSTableGenerator" style='width:128%;'>
			<c:if test="${project==Null}">
		
		<h1 style="text-align:left">No Project Found here</h1>
			</c:if>		
			
			<c:if test="${project!=Null}">	
				<%-- <c:forEach items="${project}" var="project"> --%>
					<table class="table table-striped">
	<tr><th><b>Field Name</b></th>
						<th>Details</th>
					</tr>
					<tr>
						<tr><td><b>Name of the project:</b></td>
						<td><c:out value="${project.natureofproject}" /></td>
					</tr>
					<tr>
<td><b>Unique project identification number:</b></td>
 <td><c:out value="${project.projectIdentificationNumber}" /></td>
</tr>					
<tr><td>
<b>Name of the potential opportunity :</b></td>
<td><c:out value="${project.potentialopp}" />
	</td></tr>				
<tr><td>					
<b>Sales person :</b></td><td> 
<c:out value="${project.salesperson}" />
		</td>			
</tr>
<tr><td>
	<b>Project scope summary:</b></td>
<td>	 
<c:out value="${project.projectsumm}" />
					</td></tr>
<tr><td>				
<b>Any project risks or implied customer commitments:</b></td>
<td> 
<c:out value="${project.projectrisk}" /></td></tr>
<tr><td>					
<b>Payment terms:</b></td><td>
<c:out value="${project.paymentterms}" /></td>
</tr>
<tr><td>
<b>
	Total SOW dollar value :</b></td><td>
	<c:out value="${project.sowdolar}" /></td>
					</tr>
<tr><td>					
<b>Hourly billing rate :</b></td><td>
<c:out value="${project.hourlybillingrate}" />
					</td></tr>
<tr><td>
<b>SOW Approved on :</b></td><td> 
<c:out value="${project.sowappday}" /></td></tr>
<tr><td>
<b>SOW Start on :</b></td><td> 
<c:out value="${project.sowstartday}" /></td></tr>
<tr><td>
<b>SOW Stop on :</b></td><td> 
<c:out value="${project.sowstopday}" /></td></tr>		
<tr><td>			
<b>Location where the signed SOW soft copy is saved :</b></td><td>
<c:out value="${project.sowcopy}" /></td></tr>
			
<tr><td>
	<b>Name of the customer project delivery contact :</b>
</td><td>	<c:out value="${project.delcontact}" /></td>
</tr>					
<tr><td>	
<b>Mail ID of the customer project delivery contact:</b></td><td> 
	<c:out value="${project.maildelcontact}" /></td></tr>
					
<tr><td>					
<b>Name of the customer project commercial contact :</b></td><td>
	<c:out value="${project.comcontact}" /></td></tr>
				
<tr><td>
	<b>Mail ID of the customer project commercial contact :</b></td>
	<td>
	<c:out value="${project.mailcomcontact}" />
	</td></tr>
		<c:if test="${project.approve==true}">
     <td style="float:right">
 <button class="btn btn-primary"  style="width:95%;margin-top:2%" disabled>Edit </button> 

</td>
		</c:if>
	<c:if test="${project.approve==false}">
     <td>
    <a href="edit?primaryId=${project.id}">  <button  class="btn btn-primary" style="margin-left: 100%;">Edit </button>   
  </a>
</td>
		</c:if>
	</table>	<br><br><br><br>
				<%-- </c:forEach> --%>
				</c:if>
			</div>
		</fieldset>
</c:if>

<c:if test="${verified=='Yes'}" >
<script>
projectIsVarifiedBy("${verifiedBy}");
</script>
</c:if>

<c:if test="${verified=='No'}" >
<script>
projectIsVarified("${verifiedBy}");
</script>
</c:if>
	</div>
</body>
</html>