<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
session.invalidate();
%>
<html>
<head>
<meta charset="UTF-8">
<title>じゃんけんゲーム</title>
<style>
body {
	text-align: center;
	padding-top: 100px;
	cursor: default;
	font-size: 50px;
}

label {
	
}

input[type=number] {
	font-size: 50px;
	width: 3em;
	max-width: 100%;
	box-sizing: border-box;
	margin: 0.5em 0;
}

button {
	color: gray;
	cursor: pointer;
	font-size: 50px;
}
</style>
</head>
<body>
	<h1>じゃんけんゲーム</h1>
	<label for="count">回数( 1回～ 100回まで)</label>
	<form action="JankenJudge2" method="post">
		<input type="number" name="totalcnt" min="1" max="100" value="1" />回<br>
		<button type="submit">ゲーム開始！</button>
	</form>
</body>
</html>