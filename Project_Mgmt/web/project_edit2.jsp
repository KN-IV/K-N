<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登録/編集画面</title>
<style>
body {
	font-size: 24px;
	background-color: ghostwhite;
	margin: 30px;
}

button {
	font-size: 24px;
	margin: 10px 5px;
}

input {
	height: 24px;
	font-size: 24px;
}

select {
	height: 30px;
	font-size: 24px;
}

.form {
	display: flex;
	align-items: center;
	margin-bottom: 10px;
}

.form label {
	display: inline-block;
	width: 150px;
}

.button_select {
	text-align: right;
}
</style>
</head>
<body>
	<fieldset>
		<%
		ArrayList<String> full_name = (ArrayList<String>) request.getAttribute("full_name");
		ArrayList<String> staff_id = (ArrayList<String>) request.getAttribute("staff_id");
		String item_id = (String) request.getAttribute("item_id");

		String project_title = (String) request.getAttribute("project_title");
		String status_name = (String) request.getAttribute("status_name");
		String priority = (String) request.getAttribute("priority");
		String delivery_date = (String) request.getAttribute("delivery_date");
		String man_hour = (String) request.getAttribute("man_hour");
		String staff_name = (String) request.getAttribute("staff_name");
		String remarks = (String) request.getAttribute("remarks");

		String edit = "", create = "";

		if (item_id != null) {
		%><legend>編集</legend>
		<%
		create = "disabled";
		} else {
		%>
		<legend>新規登録</legend>
		<%
		int newItem_id = (int) request.getAttribute("newItem_id");//ここ以外に置くとNULLになる
		item_id = String.valueOf(newItem_id);
		edit = "disabled";
		}
		%>
		<form action="EditServlet2" method="post" name="edit">
			<div class="form">
				<label>案件ID</label> <span><%=item_id%></span><input type="hidden"
					name="item_id" value="<%=item_id%>">
			</div>
			<div class="form">
				<label>案件名</label> <input type="text" name="project_title"
					<%if (project_title != null && !project_title.isEmpty()) {%>
					value="<%=project_title%>" <%}%>>
			</div>
			<div class="form">
				<label for="status">ステータス</label> <select name="status">
					<option value="" disabled
						<%=status_name != null && !status_name.isEmpty() ? "" : "selected"%>>選択してください</option>
					<option value="in_progress"
						<%="進行中".equals(status_name) ? "selected" : ""%>>進行中</option>
					<option value="complete"
						<%="完了".equals(status_name) ? "selected" : ""%>>完了</option>
					<option value="interrupt"
						<%="中断".equals(status_name) ? "selected" : ""%>>中断</option>
					<option value="trouble"
						<%="トラブル".equals(status_name) ? "selected" : ""%>>トラブル</option>
					<option value="incomplete"
						<%="未完了".equals(status_name) ? "selected" : ""%>>未完了</option>
				</select>
			</div>
			<div class="form">
				<label for="priority">優先度</label> <select name="priority">
					<option value=""
						<%=priority != null && !priority.isEmpty() ? "" : "selected"%>>選択してください</option>
					<%
					for (int i = 1; i <= 10; i++) {
					%>
					<option value="<%=i%>"
						<%=String.valueOf(i).equals(priority) ? "selected" : ""%>><%=i%></option>
					<%
					}
					%>
				</select>
			</div>
			<div class="form">
				<label for="delivery_date">納品予定日</label> <input type="date"
					name="delivery_date"
					<%if (delivery_date != null && !delivery_date.isEmpty()) {%>
					value="<%=delivery_date%>" <%}%>>

			</div>
			<div class="form">
				<label>予定工数</label> <input type="number" step="0.01" min="0"
					name="man_hour" style="width: 150px;" <%if (man_hour != null) {%>
					value="<%=man_hour%>" <%}%>>
			</div>
			<div class="form">
				<label for="staff_name">担当者</label> <select name="staff_id">
					<option value="" selected>選択してください</option>
					<%-- selectedを挿入 --%>
					<%
					for (int i = 0; i < full_name.size(); i++) {
					%><option value="<%=staff_id.get(i)%>"
						<%=full_name.get(i).equals(staff_name) ? "selected" : ""%>><%=full_name.get(i)%></option>
					<%
					}
					%>
				</select>
			</div>
			<div class="form">
				<%--スペース、インデントがあると空白文字として表示される --%>
				<label for="remarks">備考</label>
				<textarea style="font-size: 24px;" cols="80" rows="7" name="remarks"><%=remarks != null && !remarks.trim().isEmpty() ? remarks : ""%></textarea>
			</div>
	</fieldset>
	<div class="button_select">

		<%--return confirm()が動かない？ --%>
		<button type="submit" formaction="search_display2.jsp">キャンセル</button>
		<button <%=edit%> type="submit" name="action" value="update"
			onclick="return check('更新しますか？');">更新</button>
		<button <%=edit%> type="submit" name="action" value="delete"
			onclick="return confirm('削除しますか？')">削除</button>
		<button <%=create%> type="submit" name="action" value="register"
			onclick="return check('登録しますか？');">登録</button>
	</div>
	</form>
	<script>
		//入力チェック
		function check(message) {
			if (edit.project_title.value == "" || edit.status.value == "") {
				alert("案件名とステータスは必ず入力してください")
				return false;
			}
			//確認ダイアログ
			return confirm(message);
		}
	</script>
</body>
</html>