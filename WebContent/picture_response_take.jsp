<%
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String text = request.getParameter("text");
	String url = request.getParameter("url");
%>
<p>Question Title: </p>
<p><%=title %></p>
<p>Picture: </p>
<p><img src=<%=url%> ></p>
<p>Question: </p>
<p><%=text %></p>
<p>Type Your Answer: </p>
<p><textarea rows="4" cols="50" name=<%=id %>></textarea></p>