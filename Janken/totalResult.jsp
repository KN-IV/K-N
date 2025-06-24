<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>戦績</title>
<style>
body {
	font-size: 36px;
	text-align: center;
	cursor: default;
}

h3 {
	
}

p1 {
	color: blue;
}

p2 {
	font-size: 100px;
}

p3 {
	color: red;
}

p4 {
	font-size: 100px;
}
</style>
</head>
<body>
	<h3>
		${sessionScope.currentCount}回目の結果<br>
	</h3>
	<p1>CPU<br>
	</p1>
	<p2>${cpuHand}<br>
	<br>
	</p2>
	<p3>あなた<br>
	</p3>
	<p4>${playerHand}<br>
	</p4>
	<p5>結果：${result}<br>
	<br>
	<br>
	<p6>⇩</p6> <br>
	<br>
	</p5>
	<h2>戦績</h2>
	<br>
	<p><%=session.getAttribute("totalResult")%></p>
	<br>
	<a href="JankenGame2.jsp">最初のページに戻る</a>
</body>
</html>