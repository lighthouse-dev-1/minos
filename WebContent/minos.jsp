<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.nulabinc.backlog4j.IssueType" %>
<%@page import="org.json.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="css/minos.css">
	<script type="text/javascript" src="script/minos.js"></script>
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<title>Minos</title>
</head>
<body class="wrap">
	<div class="header">
    <span>minos</span>
  </div>

  <form id="uploadExcelForm" action = "main?isExcel=true" method = "post" enctype = "multipart/form-data">
  	<div id="drag-drop-area">
			<p class="drag-drop-info">Drag & Drop Files</p>
			<p>OR</p>
			<p>
				<input id="fileInput" type="file" name="file" />
			</p>
			<p>
				<input type="submit" id="button" value="Upload" />
			</p> 
		</div>
	</form>

<% 
	JSONObject 	issueTypeList = new JSONObject(request.getAttribute("issueTypeList").toString());
	JSONArray 	arr = null;
	arr = issueTypeList.getJSONArray("data");
	
	JSONObject ExcelTask = null;
	JSONObject task 		 = null;
	int taskCnt = 1;
	
	if(request.getAttribute("task") != null){
		task = new JSONObject(request.getAttribute("task").toString()); 
		taskCnt = task.length();
	}
%>
  <form class="taskForm" method="post" action="main" onsubmit="return confirm('Do you really want to submit the form?');">
  	<input type="text" id="taskCnt" name="taskCnt" readonly="readonly" value="<%=taskCnt%>" style="display: none;"/>
 <% 
	if(task != null){ 		
 		for(int i=0; i < task.length(); i++){
 	 		String summary     = "";
 	 		String description = "";
 	 		String issueType   = "";
 	 		String priority    = "";
 	 		
 			ExcelTask = task.getJSONObject(String.valueOf(i));
 			
 			if( ExcelTask.has("0") ){
 				summary = ExcelTask.getString("0");
 			}
 			if( ExcelTask.has("1") ){
 				description = ExcelTask.getString("1");
 			}
 			if( ExcelTask.has("2") ){
 				issueType = ExcelTask.getString("2");
 			}
 			if( ExcelTask.has("3") ){
 				priority = ExcelTask.getString("3");
 			} 			
 %>
    <div class="task">
    	<div class="task_del">
        <input type="button" value="X" class="del_btn" onclick="delTask(this);">
      </div>

      <div class="title">
        <div class="menu">Summary</div>
        <input name="summary" class="input_title" value="<%=summary%>" required />
      </div>
      <hr>
      
      <div class="content">
      	<div class="menu">
          Description
        </div>
        <div class="ticket_properties-value">
        	<textarea name="description" class="textarea_editer"><%=description%></textarea>
        </div>
      </div>
      <hr>
      
    	<div class="issueType">
      	<div class="menu">Issue type</div>
      	<select name="issueTypeName" class="issueType selectBox">         
<% 
				for(int j =0; j<arr.length(); j++) { 
     			if(issueType.equals(arr.getJSONObject(j).getString("name")) ){
%>
     				<option value=<%= arr.getJSONObject(j).getString("name") %> selected="selected"><%= arr.getJSONObject(j).getString("name") %></option>
<% 				} else {
%>
     				<option value=<%= arr.getJSONObject(j).getString("name") %>><%= arr.getJSONObject(j).getString("name") %></option>
<%
     			}
				} 
%>    		
				</select>
			</div>
			<hr>
			
      <div class="priority">
      	<div class="menu">Priority</div>
        <select name="priorityName" class="priority selectBox">          
<%
     		if(priority.equals("High")) {
%>
					<option value="High" selected="selected">High</option>
<% 			} else {
%>
					<option value="High">High</option>
<%	
				}
				if(priority.equals("Normal")) {
%>
					<option value="Normal" selected="selected">Normal</option>
<%
				} else {
%>
					<option value="Normal">Normal</option>
<%     			
     		}
				if(priority.equals("Low")) {
%>
					<option value="Low" selected="selected">Low</option>
<%
				} else {
%>
					<option value="Low">Low</option>
<%	
				}
%>         
				</select>
			</div>
		</div>
<% 
		} 
	} else {
%> 	
		<div class="task" id=1>
			<div class="task_del">
				<input type="button" value="X" class="del_btn" onclick="delTask(this);">
			</div>

			<div class="title">
				<div class="menu">Summary</div>
				<input name="summary" class="input_title" required />
			</div>
			<hr>
				
			<div class="content">
				<div class="menu">
					Description
				</div>
				<div class="ticket_properties-value">
					<textarea name="description" class="textarea_editer"></textarea>
				</div>
			</div>
			<hr>
			
			<div class="issueType">
				<div class="menu">Issue type</div>
				<select name="issueTypeName" class="issueType selectBox">
					<% for(int j =0; j<arr.length(); j++) { %>
							<option value=<%= arr.getJSONObject(j).getString("name") %>><%= arr.getJSONObject(j).getString("name") %></option>
					<% } %>
				</select>
			</div>
			<hr>
			
			<div class="priority">
				<div class="menu">Priority</div>
				<select name="priorityName" class="priority selectBox">
					<option value="High">	  High	 </option>
					<option value="Normal"> Normal </option>
					<option value="Low">	  Low	   </option>
				</select>
			</div>
		</div>
 <%	
	}
 %>
    
   	<div class="add">
    	<input type="button" class="add_button" onclick="addTask();" value="+ Add task" />
  	</div> 
  
  	<div class="save">
    	<input type="submit" id="submit" value="SAVE" />
  	</div>
  </form>
</body>
</html>