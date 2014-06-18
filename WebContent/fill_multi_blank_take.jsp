<%
	String id = request.getParameter("id");	
	String title = request.getParameter("title");
	String text = request.getParameter("text");
%>
<p>Question Title: </p>
<p><%=title %></p>
<p>Question: </p>
<%
	String display = "";
	String restStr = text;
	
	while(true) {
		// find the index of the first blank	
		int index = restStr.indexOf('_');
		if (index == -1) break;
		
		// store parts before and after the blank as substrings
		String firstStr = restStr.substring(0,index);
		restStr = restStr.substring(index+1);
		
		// add the first part and a blank to the display text
		display += firstStr + "____";		
	}
	
	display += restStr;
%>
<p><%=display %></p>
<p>Type Your Answer: (Please separate your answers for each blank with ';')</p>
<p><textarea rows="4" cols="50" name=<%=id %>></textarea></p>