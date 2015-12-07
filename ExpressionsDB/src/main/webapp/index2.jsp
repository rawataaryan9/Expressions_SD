<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript"></script>
<script>
var listActions;

$(document).ready(function(){
	
	$.ajax({
		type:"Get",
		url: "${pageContext.request.contextPath}/snapdeal/getAllActions",
		success: function(response){
			listActions = response;
		},
		error: function(e){
			alert("something went wrong "+e);
		}
	});
	$.ajax({
		type:"Get",
		url: "${pageContext.request.contextPath}/snapdeal/allexpressions",
		success: function(response){
			setExpressionData(response);
		},
		error: function(e){
			alert("something went wrong "+e);
		}
	});
	
});

function setExpressionData(response){
	if(response!=null){
		for(data in response){
			var str = "<tr><td>"+response[data].id+"</td><td><pre><textarea rows='3' cols='30' disabled>"+response[data].namespace+"."+response[data].referenceName+"</textarea></pre></td><td><pre><textarea rows = '3' cols = '20' disabled>"+response[data].expression+"</textarea></pre></td><td><pre><textarea rows = '3' cols = '20' disabled>"+response[data].executionMode+"</textarea></pre></td><td><pre><textarea rows = '3' cols = '20' disabled>"+response[data].createdBy+"</textarea></pre></td><td><pre><textarea rows = '3' cols = '20' disabled>"+response[data].createdTime+"</textarea></pre></td>"; 
			str+="<td><select class='actionClass' id='action"+response[data].id+"'><option value=''>----Select an Action----</option>";
			
			for(var i=0;i<listActions.length;i++){
				str+="<option value='"+listActions[i]+"'>"+listActions[i]+"</option>";		
			}
			str+="</select></td><td><input type='button' value='Submit' class='submitButton' id='submitBtn"+response[data].id+"' disabled/></td></tr>";
			$("#exprTable").append(str);
		}
	}
	else{
		$("#exprTable").append("<tr><td colspan='5'>No Results Found</td></tr>");
	}
	
	submitInfo();
	$(".actionClass").change(function(){
		var str = jQuery(this).attr("id");
		var elId = str.substring(str.length-1,str.length);
		if($("#"+str).val()!=''){
			$("#submitBtn"+elId).attr("disabled", false);
		}else{
			$("#submitBtn"+elId).attr("disabled", true);
		}
	});
}
function actionSettings(){
	
}
function submitInfo(){
	$(".submitButton").click(function(event){
		var str = jQuery(this).attr("id");
		var elementId = str.substring(str.length-1,str.length);
		var rowId = $("#exprTable tr").eq(elementId);
		var exprId = rowId.find('td').eq(0).text();
		var exprName = rowId.find('td').eq(1).text();
		var exprValue = rowId.eq(elementId).find('td').eq(2).text();
		var exprAction = $("#action"+elementId).val();
		if(exprAction==''){
			alert("Please select an action");
			return;
		}else{
			$.ajax({
				type:"POST",
				data: {"expressionId":exprId,"name":exprName,"expression":exprValue,"action":exprAction},
				url: "${pageContext.request.contextPath}/snapdeal/handleActions",
				success: function(response){
					alert("Sent");
				},
				error: function(e){
					alert("something went wrong "+e);
				}
			});
		}
		$("#action"+elementId).val('');
		$("#submitBtn"+elementId).attr("disabled",true);
	});
}
</script>
</head>
<body>
<br>
<h1 align="center">VIEW EXPRESSIONS</h1>
<table align="center" style="margin-left: 50px;margin-top: 50px" cellpadding="10px" border="1px" id="exprTable">
<tr>
<td><b>S.No.</b></td>
<td><b>NAME</b></td>
<td><b>EXPRESSION</b></td>
<td><b>EXECUTION MODE</b></td>
<td><b>CREATED_BY</b></td>
<td><b>CREATED_TIME</b></td>
<td><b>ACTION</b></td>
<td><b></b></td>
</tr>
</table>

</body>
</html>