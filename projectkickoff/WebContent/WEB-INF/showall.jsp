
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true" %> 
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
	<script src="resources/bootstrap/js/validation.js"></script>
	<style>


#header {
	background-image: url("resources/bootstrap/images/bg-header.png");
    height: auto;
}

td
{
font-size:15.4px;}

</style>
<script>
/* Method to check if search field is empty */
function search(){
	if (document.find.name.value.trim()== "") {
		alert("Enter search text please");
		document.find.name.focus();
		return false;
	}

}
</script>

</head>

<body>
    <div id="header" class="container" style="width:100%" >  
	<a href="http://www.optimusinfo.com/" target="_blank"><img src="resources/bootstrap/images/optimusinfologo.png" id="logoset"></img></a>     
		<h3 style="color:white;float:left;"><b>Project List</b></h3>
		
		<a href="/projectkickoff/" class="btn btn-primary" id="createbtn">Create a Project</a>
		<a href="showall" class="btn btn-primary" id="viewallbtn" style="margin-left:0px;margin-right:0px;">View All Projects</a>
		 <form role="form"  action="show" method="get" name="find" id=formsearch  onsubmit="return search()">
<div style="width:320px">
			<input class="form-control" type="text" name="name"
							placeholder="e.g., project or customer name" id="searchbutton1" > <input
							class="btn btn-primary" type="submit" value="Search" id="showallbtn"> 
</div>
	</form>				</div>  
			<div class="container"
		style="width: 100%;float:left">
	</div>

 <c:if test="${projectList=='middle'||projectList=='start'||projectList=='last'||projectList=='only'}">
 <div class="container" id="CSSTableGenerator" style='width:75%;margin-left: 10%;margin-top: 3%;'>
 
		<table class="table table-responsive" id="tableCSS">
		<tr><td  style="width:40%;"><b>Name of Project</b></td>
		<td  style="width:40%"><b>Customer Name</b></td>
		<td style="text-align:center;width:200px"><b>Verification</b></td></tr>
<c:forEach items="${project}" var="item">
<tr><td><a href="showid?id=${item.id}"><c:out value="${item.natureofproject}" /></a>
					</td>

<td> <a href="showid?id=${item.id}"><c:out value="${item.delcontact}" /></a>
					</td>
     
      <c:if test="${item.approve==true}">
     <td style="text-align:center"> Verified</td>
 
		</c:if>
		 <c:if test="${item.approve==false}">
     <td style="text-align:center">

    <a href="edit?primaryId=${item.id}"><img src="resources/bootstrap/images/file_edit.png" id="editbtn"></img>   
  </a>
</td>
		</c:if>
		
					</tr>
					
	</c:forEach>
	<tr><td></td><td>
	<div class="container" style='width: 76%'>
		<c:if test="${projectList=='start'}">
			<a href="showall?page=next" style="float: right"> NEXT</a>
		</c:if>
		<c:if test="${projectList=='middle'}">

			<a href="showall?page=previous" style="float: left"> PREVIOUS </a>
			<a href="showall?page=next" style="float: right"> NEXT</a>
		</c:if>
		<c:if test="${projectList=='last'}">
			<a href="showall?page=previous" style="float: left"> PREVIOUS</a>
		</c:if>
		<c:if test="${projectList=='only'}">
			
		</c:if>
		</div>
		</td>
	
	</tr>
	</table>	

</div></c:if>
	
</body>

</html>