<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>検索/一覧画面</title>
<style>
body {
	font-size: 24px;
	background-color: ghostwhite;
	margin: 30px;
}

button {
	font-size: 24px;
}

input {
	height: 24px;
	font-size: 24px;
}

select {
	height: 30px;
	font-size: 24px;
}

.submit {
	text-align: right;
}

.form-group {
	margin-bottom: 10px;
	font-size: 24px;
}

table {
	width: 100%;
}

.item {
	background-color: #aaf;
	text-align: center;
}

.contents {
	background-color: white;
	text-align: center;
}

.name {
	width: 300px;
}
</style>
</head>
<body
	<%String project_title = (String) session.getAttribute("project_title");
String status_name = (String) session.getAttribute("status_name");
String start_delivery_date = (String) session.getAttribute("start_delivery_date");
String end_delivery_date = (String) session.getAttribute("end_delivery_date");
String staff_name = (String) session.getAttribute("staff_name");

String resultMessage = (String) request.getAttribute("resultMessage");
if (resultMessage != null && !resultMessage.isEmpty()) {%>
	onload="alert('<%=resultMessage%>');" <%}%>>
	<div class="submit">
		<button onclick="location.href='EditServlet2'">案件を新規登録</button>
	</div>
	<fieldset>
		<legend>検索条件</legend>
		<form action="SearchServlet2" method="post" name="search">
			<div class="form-group">
				<label for="project_title"> 案件名 &emsp;</label><input type="text"
					class="name" name="project_title"
					value="<%=project_title != null && !project_title.isEmpty() ? project_title : ""%>">
				<label for="delivery_date"> &emsp;納品予定日</label><input type="date"
					name="start_delivery_date"
					value="<%=start_delivery_date != null && !start_delivery_date.isEmpty() ? start_delivery_date : ""%>">
				～ <input type="date" name="end_delivery_date"
					value="<%=end_delivery_date != null && !end_delivery_date.isEmpty() ? end_delivery_date : ""%>">
				<label for="status"> &emsp;ステータス</label> <select name="status">
					<option value=""
						<%=status_name != null && !status_name.isEmpty() ? "" : "selected"%>>選択してください</option>
					<option value="in_progress"
						<%="in_progress".equals(status_name) ? "selected" : ""%>>進行中</option>
					<option value="complete"
						<%="complete".equals(status_name) ? "selected" : ""%>>完了</option>
					<option value="interrupt"
						<%="interrupt".equals(status_name) ? "selected" : ""%>>中断</option>
					<option value="trouble"
						<%="trouble".equals(status_name) ? "selected" : ""%>>トラブル</option>
					<option value="incomplete"
						<%="incomplete".equals(status_name) ? "selected" : ""%>>未完了</option>
				</select>
			</div>
			<div class="form-group">
				<%-- 苗字、名前、フルネームで検索可能。フルネームは空白の有無に関わらず検索可能 --%>
				<label for="name">担当者名</label> <input type="text" class="name"
					name="name"
					value="<%=staff_name != null && !staff_name.isEmpty() ? staff_name : ""%>">
			</div>
			<div class="submit">
				<button type="submit" onclick="return check();">検索</button>
			</div>
		</form>
	</fieldset>
	<script>
		//入力チェック
		function check() {
			if (search.project_title.value == ""
					&& search.start_delivery_date.value == ""
					&& search.end_delivery_date.value == ""
					&& search.status.value == "" && search.name.value == "") {
				alert("検索条件を一つ以上入力してください")
				return false;
			} else {
				return true;
			}
		}
	</script>
	<hr>
	<%
	String errorMessage = (String) request.getAttribute("errorMessage");
	ArrayList<String> result = (ArrayList<String>) session.getAttribute("dbResult");

	if (errorMessage != null) {
	%><p style="color: red;"><%=errorMessage%></p>
	<%
	} else if (result != null) {
	%>
	<table border=1>
		<tr class="item">
			<th>&emsp;&emsp;</th>
			<th>No</th>
			<th>案件名</th>
			<th>ステータス</th>
			<th>優先度</th>
			<th>納品予定日</th>
			<th>予定工数</th>
			<th>担当者</th>
		</tr>
		<%
		int no = 0;
		for (String str : result) {
			String[] list = str.split("/");
			String item_id = list[0];
			no++;
		%><tr class="contents">
			<th>
				<button
					onclick="location.href='EditServlet2?item_id=<%=item_id%>&no=<%=no%>'">修正</button>
			</th>
			<td><%=no%></td>
			<%
			for (int i = 1; i < list.length - 1; i++) {
			%>
			<td><%=list[i]%></td>
			<%
			}
			%>
		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	%>
</body>
</html>