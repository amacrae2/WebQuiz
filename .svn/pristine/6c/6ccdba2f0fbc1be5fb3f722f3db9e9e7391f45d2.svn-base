<%
	String id = request.getParameter("id");
	String title = request.getParameter("title");
	String text = request.getParameter("text");
	String choices = request.getParameter("choices");
%>
<p>Question Title: </p>
<p><%=title %></p>
<p>Question: </p>
<p><%=text %></p>
<p>Choices: </p>
<%
	String[] parts = choices.split(";");
	for (int i=0; i<parts.length; i++) {
	%>
		<input type="radio" name=<%=id %> value="<%=parts[i]%>">
		<%=parts[i]%>
		<br>
	<% 		
	}
%>
