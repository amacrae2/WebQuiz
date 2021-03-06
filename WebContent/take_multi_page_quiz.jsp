<%@page import="java.util.*" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Taking Quiz</title>
</head>
<body>
<form action="MultiPageServlet" method="get">
	<%
	// get servlet context, index and array
	ServletContext sc = request.getServletContext();
	int index = (Integer) sc.getAttribute("index");
	String immedi = (String) sc.getAttribute("immedi");
	List<Integer> questionIds = (List<Integer>) sc.getAttribute("QuestionIdArray");
	
	// get the question id 
	int id = questionIds.get(index);
	%>
	<input type="hidden" name="idStr" value=<%=Integer.toString(id)%>>
	<%
	
	// get the statement object and execute query
	Statement stmt = (Statement) sc.getAttribute("StatementObject");
	ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE  question_id="+id);
	
	// get question type
	rs.next();
	int questionType = rs.getInt("type");
	
	// display the question by including question jsp file
	String title;
	String text;
	String choices;
	String url;
	switch (questionType) {
		case 1: 
			title = rs.getString("title");
			text = rs.getString("text");
		%>
			<jsp:include page="question_response_take.jsp">
		    <jsp:param value="<%=id %>" name="id"></jsp:param> 
		    <jsp:param value="<%=title %>" name="title"></jsp:param> 
			<jsp:param value="<%=text %>" name="text"></jsp:param> 
			</jsp:include>
		<%
			break;
		case 2: 
			title = rs.getString("title");
			text = rs.getString("text");
		%>
			<jsp:include page="fill_blank_take.jsp">
		   	<jsp:param value="<%=id %>" name="id"></jsp:param> 
		    <jsp:param value="<%=title %>" name="title"></jsp:param> 
			<jsp:param value="<%=text %>" name="text"></jsp:param> 
			</jsp:include>
		<%
			break;
		case 3: 
			title = rs.getString("title");
			text = rs.getString("text");
			choices = rs.getString("choices");
		%>
			<jsp:include page="multi_choice_take.jsp">
		   	<jsp:param value="<%=id %>" name="id"></jsp:param> 
		    <jsp:param value="<%=title %>" name="title"></jsp:param> 
			<jsp:param value="<%=text %>" name="text"></jsp:param> 
			<jsp:param value="<%=choices %>" name="choices"></jsp:param> 
			</jsp:include>
		<%
			break;
		case 4: 
			title = rs.getString("title");
			text = rs.getString("text");
			url = rs.getString("picture_url");
		%>
			<jsp:include page="picture_response_take.jsp">
		    <jsp:param value="<%=id %>" name="id"></jsp:param> 
		    <jsp:param value="<%=title %>" name="title"></jsp:param> 
			<jsp:param value="<%=text %>" name="text"></jsp:param> 
			<jsp:param value="<%=url %>" name="url"></jsp:param> 
			</jsp:include>
		<%
			break;
		case 5: 
			title = rs.getString("title");
			text = rs.getString("text");
		%>
			<jsp:include page="multi_answer_take.jsp">
		   	<jsp:param value="<%=id %>" name="id"></jsp:param> 
		    <jsp:param value="<%=title %>" name="title"></jsp:param> 
			<jsp:param value="<%=text %>" name="text"></jsp:param> 
			</jsp:include>
		<%
			break;
		case 6: 
			title = rs.getString("title");
			text = rs.getString("text");
		%>
			<jsp:include page="fill_multi_blank_take.jsp">
		   	<jsp:param value="<%=id %>" name="id"></jsp:param> 
		    <jsp:param value="<%=title %>" name="title"></jsp:param> 
			<jsp:param value="<%=text %>" name="text"></jsp:param> 
			</jsp:include>
		<%
			break;
		default: break;	
	}
	
	if (immedi.equals("on")) {
		%>
		<p>Each question will be graded immediately. Click 'Submit' to proceed. <input type="submit" value="Submit"></p>
	<% 
	} else {
	%>
		<p><input type="submit" value="Next"></p>
		<%
	}
	%>
</form>
</body>
</html>