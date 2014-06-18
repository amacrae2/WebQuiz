<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Question Type</title>
</head>
<body>
<h1>Select Question Types</h1>
<form action="create_question.jsp" method="post">
	<p><select name="question_type">
  		<option value="1">Question-Response</option>
  		<option value="2">Fill in the Blank</option>
  		<option value="3">Multiple Choice</option>
  		<option value="4">Picture-Response</option>
  		<option value="5">Multiple-Answer</option>
  		<option value="6">Multiple-Blank</option>
	</select></p>
	<p><input type="submit" value="Next"></p>
</form>
</body>
</html>