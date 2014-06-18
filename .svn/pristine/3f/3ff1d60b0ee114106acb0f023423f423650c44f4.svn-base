<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Question</title>
</head>
<body>
<h1>Add Question</h1>
<form action="CreateQuestionServlet" method="post">
	<% 
		String questionType = request.getParameter("question_type");
	%>
		<input type="hidden" name="QuestionType" value=<%=questionType%>>
	<%	
		int type = Integer.parseInt(questionType);
		switch (type) {
			case 1: %><%@include file="question_response.html" %><% break;
			case 2: %><%@include file="fill_blank.html" %><% break;
			case 3: %><%@include file="multi_choice.html" %><% break;
			case 4: %><%@include file="picture_response.html" %><% break;
			case 5: %><%@include file="multi_answer.jsp" %><% break;
			case 6: %><%@include file="fill_multi_blank.html" %><% break;
			default: break;
		}		
	%>
	<p>Next Step Option: <select name="add_or_back">
  		<option value="add">Save and Add Another Question</option>
  		<option value="back">Save and Go Back to Homepage</option>
	</select></p>
	<p><input type="submit" value="Submit"></p>
</form>
</body>
</html>