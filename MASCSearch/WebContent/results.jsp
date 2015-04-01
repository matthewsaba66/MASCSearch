<%@page import="helper.ContaParole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.lucene.document.Document"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Risultati</title>
</head>
<body>
	Risultati per:
	<%=session.getAttribute("originalQuery")%>
	<form action="indietro" method="get">

		<input type="submit" name="submit" value="torna indietro" />

	</form>

	Trovati
	<%=session.getAttribute("totalHitCount")%>
	risultati in:
	<%=session.getAttribute("searchDuration")%>
	millisecondi
	<br></br>
  	<% String query = (String)session.getAttribute("originalQuery");
 	long count = ContaParole.wordCount(query);
 	if ((Integer)session.getAttribute("totalHitCount")<15 && count == 1) {%>
	Forse stavi cercando: <% out.print(session.getAttribute("suggestedQuery")); }%>
	<% 
		ArrayList<Document> list = (ArrayList<Document>) session
				.getAttribute("hits");
		for (Document d : list) {
	%><br></br>
	<%
		out.print("Url: " + d.get("url"));
	%>

	<br></br>
	<%
		out.print(d.get("title"));
	%>
	<br></br>
	<% 
	String body = d.get("body").replaceAll("[^\\w\\s]", "");
	/* int index = body.indexOf((String)session.getAttribute("originalQuery"));
	int delta = 0;
	int limInf = index - delta;
	int limSup = index + delta;
	
	String result = "..."+ body.substring(limInf, limSup)+"...";
		out.print(result);*/
		out.print(body);
		}
	%><br></br>
	<br></br>


</body>
</html>