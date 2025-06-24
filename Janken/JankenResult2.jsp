<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ぽんっ！</title>
<style>
body {
	text-align: center;
	cursor: default;
	font-size: 36px;
}

.draw {
	background-color: #dcdcdc;
}

.win {
	background-color: #ffb6c1;
}

.lose {
	background-color: #87cefa;
}

.default {
	background-color: #ffffff;
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

button {
	color: gray;
	cursor: pointer;
	font-size: 30px;
}
</style>
</head>
<c:set var="bodyClass" value="default" />
<c:if test="${result=='あいこ'}">
	<c:set var="bodyClass" value="draw" />
</c:if>
<c:if test="${result=='勝ち'}">
	<c:set var="bodyClass" value="win" />
</c:if>
<c:if test="${result=='負け'}">
	<c:set var="bodyClass" value="lose" />
</c:if>

<body class="${bodyClass}">

	<h3>
		${sessionScope.currentCount-1}回目の結果<br>
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
	</p5>
	<form action="HandSelect2.jsp" method="post">
		<button type="submit">次の勝負へ</button>
	</form>

	<a href="JankenGame2.jsp">ゲームをやめて最初のページに戻る</a>
</body>
</html>