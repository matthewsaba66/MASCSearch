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
	<%= session.getAttribute("query") %>
	<form action="indietro" method="get">

		<input type="submit" name="submit" value="torna indietro" />

	</form>

	<% ArrayList<Document> list= (ArrayList<Document>)session.getAttribute("hits");
	for (Document d : list){ %><br></br>
	<%	out.print("Url: " + d.get("url"));%>
	<br></br>
	<br></br>
	<%out.print(d.get("title")); %><br></br>
	<br></br>
	<%out.print(d.get("body"));
		}%><br></br>
	<br></br>


</body>
</html>