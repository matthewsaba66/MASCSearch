<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MASC Search!!</title>

<style type="text/css">
.classname {
	-moz-box-shadow: inset -6px -6px 11px -3px #bee2f9;
	-webkit-box-shadow: inset -6px -6px 11px -3px #bee2f9;
	box-shadow: inset -6px -6px 11px -3px #bee2f9;
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #63b8ee
		), color-stop(1, #468ccf));
	background: -moz-linear-gradient(center top, #63b8ee 5%, #468ccf 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#63b8ee',
		endColorstr='#468ccf');
	background-color: #63b8ee;
	-webkit-border-top-left-radius: 25px;
	-moz-border-radius-topleft: 25px;
	border-top-left-radius: 25px;
	-webkit-border-top-right-radius: 0px;
	-moz-border-radius-topright: 0px;
	border-top-right-radius: 0px;
	-webkit-border-bottom-right-radius: 25px;
	-moz-border-radius-bottomright: 25px;
	border-bottom-right-radius: 25px;
	-webkit-border-bottom-left-radius: 0px;
	-moz-border-radius-bottomleft: 0px;
	border-bottom-left-radius: 0px;
	text-indent: 0px;
	border: 1px solid #3866a3;
	display: inline-block;
	color: #14396a;
	font-family: Impact;
	font-size: 17px;
	font-weight: normal;
	font-style: normal;
	height: 39px;
	line-height: 39px;
	width: 97px;
	text-decoration: none;
	text-align: center;
	text-shadow: 1px 1px 0px #7cacde;
}

.classname:hover {
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #468ccf
		), color-stop(1, #63b8ee));
	background: -moz-linear-gradient(center top, #468ccf 5%, #63b8ee 100%);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#468ccf',
		endColorstr='#63b8ee');
	background-color: #468ccf;
}

.classname:active {
	position: relative;
	top: 1px;
}

form {
	width: 650px;
	margin: 0 auto;
}

.element {
	margin: auto 0px;
	padding: 0px;
	text-shadow: 0px 0px 5px rgba(0, 0, 0, 0.79);
	color: #0080ff;
	font-size: 53px;
}

.inputbox {
	margin: auto 0px;
	padding: 0px;
	font-size: 16px;
	width: 300px;
	border-color: #cccccc;
	padding: 7px;
	border-width: 5px;
	border-radius: 12px;
	border-style: inset;
	box-shadow: 8px 2px 8px 0px rgba(42, 42, 42, .75);
}

.css-input:focus {
	outline: none;
}
</style>

</head>
<body>
	<div align="center">
		</br>
		<h1 class="element">MASCSearch</h1>
		</br>
		<form action="processaQuery" method="get" align="center" class="form">
			<input type="text" class="inputbox" name="query"> </br> </br>
			<div align="center">
				<button type="submit" name="submit" class="classname" value="Search">
					Search   </button>
			</div>
		</form>
	</div>
</body>
</html>