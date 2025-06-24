<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>じゃんけん…</title>
<style>
body {
	font-size: 36px;
	text-align: center;
	cursor: default;
}

h3 {
	color: gray;
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

input[type=radio] {
	visually: none;
	cursor: pointer;
}

input[type=radio]:checked+label {
	background: gray;
}

.hand-option {
	margin: 20px;
	font-size: 100px;
	cursor: pointer;
}

.hand-option input {
	display: none;
}

.hand-option input:checked+span {
	background: gray;
}

input[type="submit"] {
	margin-top: 20px;
	font-size: 50px;
	cursor: pointer;
	color: gray;
}
</style>
</head>
<body>
	<h3>${sessionScope.currentCount}回目</h3>
	<p1>CPU<br>
	</p1>
	<p2>❔<br>
	<br>
	</p2>
	<p3>あなた<br>
	</p3>
	<form action="JankenJudge2" method="post">
		<label class="hand-option"> <input type="radio" name="hand"
			value="0" id="rock" checked> <span>✊</span></label><br> <label
			class="hand-option"> <input type="radio" name="hand"
			value="1" id="scissors"> <span>✌</span></label> <label
			class="hand-option"> <input type="radio" name="hand"
			value="2" id="paper"> <span>✋</span></label><br> <input
			type="submit" value="結果">
	</form>

	<a href="JankenGame2.jsp">ゲームをやめて最初のページに戻る</a>
</body>
</html>