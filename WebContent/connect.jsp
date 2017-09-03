<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="css/minos.css">
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<title>Minos</title>
</head>
<body class="wrap">
	<div class="header">
  	<span>minos</span>
  </div>
	<form id="connectForm" method="post" action="main?isConnected=true">
		<div class="title">
    	<div class="menu">Name Space</div>
      <input name="nameSpace" class="input_title" required />
		</div>
		
    <div class="title">
    	<div class="menu">Project Key</div>
      <input name="projectKey" class="input_title" required />
		</div>
		
    <div class="title">
    	<div class="menu">API Key</div>
      <input name="apiKey" class="input_title" required />
    </div>
      
		<div class="save">
    	<input type="submit" id="button" value="CONNECT" />
  	</div>
	</form>
</body>
</html>