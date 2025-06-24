package project_mgmt2.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {

	private String connectionUrl = "jdbc:sqlserver://host;databaseName=DBName;"
			+ "user=username;password=pass;encrypt=false";
	private Connection con = null;
	private PreparedStatement stmt = null;
	private Statement stmt2 = null;
	private ResultSet rs = null;

	public void open() throws SQLException {
		try {
			con = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> select(String project_title, String status, String start_delivery_date,
			String end_delivery_date, String name) throws SQLException {

		ArrayList<String> dbResult = new ArrayList<>();
		ArrayList<String> params = new ArrayList<>();

		StringBuilder SQL = new StringBuilder("SELECT item_id,project_title,"
				+ "status_name,priority,delivery_date,man_hour,"
				+ "last_name + first_name as full_name,project_table.remarks "
				+ "FROM project_table LEFT JOIN staff_master ON contact_id = staff_id "
				+ "LEFT JOIN status_table ON project_table.status_id = status_table.status_id "
				+ " WHERE ");

		try {
			//WHERE a = b AND c = d...; WHEREとANDの間に検索条件(条件式)を入れる必要性を判断
			boolean flg = false;

			//検索時に値が入力されていればSQL文を追加
			//案件名
			if (project_title != null && !project_title.isEmpty()) {
				SQL.append("project_title LIKE ?");
				params.add("%" + project_title + "%");
				flg = true;
			}
			//ステータス　status_table上のstatusの値を取得
			if (status != null && !status.isEmpty()) {
				if (flg == true) {
					SQL.append(" AND ");
				}
				int statusId = 0;

				switch (status) {
				case "in_progress" -> statusId = 1;
				case "complete" -> statusId = 2;
				case "interrupt" -> statusId = 3;
				case "trouble" -> statusId = 4;
				case "incomplete" -> statusId = 5;
				}

				SQL.append("project_table.status_id LIKE ?");
				params.add("%" + statusId + "%");
				flg = true;
			}
			//納品予定日、範囲
			if (start_delivery_date != null && !start_delivery_date.isEmpty()) {
				if (flg == true) {
					SQL.append(" AND ");
				}
				SQL.append("delivery_date >= ?");
				params.add(start_delivery_date);
				flg = true;
			}
			if (end_delivery_date != null && !end_delivery_date.isEmpty()) {
				if (flg == true) {
					SQL.append(" AND ");
				}
				SQL.append("delivery_date <= ?");
				params.add(end_delivery_date);
				flg = true;
			}
			//担当者　staff_master上のlast_name、first_nameを取得したい
			if (name != null && !name.isEmpty()) {
				if (flg == true) {
					SQL.append(" AND ");
				}
				//フルネーム(スペースが含まれている)ならsplit
				/*スペースなしフルネームでもelse文の部分一致に引っかかるから
				 * そもそもこの処理自体不要ではないだろうか...*/
				if (name.contains(" ") || name.contains("　")) {
					String[] splitName = name.trim().split("[ 　]+");
					if (splitName.length >= 2) {
						String lastName = splitName[0];
						String firstName = splitName[1];
						SQL.append("last_name LIKE ? and first_name LIKE ?");
						params.add("%" + lastName + "%");
						params.add("%" + firstName + "%");
					}
				} else {
					//last_name + first_nameの中にname(姓と名の片方もしくは両方)が含まれているか
					SQL.append("last_name + first_name LIKE ?");
					params.add("%" + name + "%");
					flg = true;
				}
			}

			stmt = con.prepareStatement(SQL.toString());
			for (int i = 0; i < params.size(); i++) {
				stmt.setString(i + 1, params.get(i)); //index 0 は範囲外
			}

			rs = stmt.executeQuery();

			while (rs.next()) {
				String item_idResult = rs.getString("item_id");
				if (item_idResult == null || item_idResult.isEmpty()) {
					item_idResult = " ";
				}

				String project_titleResult = rs.getString("project_title");
				if (project_titleResult == null || project_titleResult.isEmpty()) {
					project_titleResult = " ";
				}

				String status_nameResult = rs.getString("status_name");
				if (status_nameResult == null || status_nameResult.isEmpty()) {
					status_nameResult = " ";
				}

				String priorityResult = rs.getString("priority");
				if (priorityResult == null || priorityResult.isEmpty()) {
					priorityResult = " ";
				}

				String delivery_dateResult = rs.getString("delivery_date");
				if (delivery_dateResult == null || delivery_dateResult.isEmpty()) {
					delivery_dateResult = " ";
				}

				String man_hourResult = rs.getString("man_hour");
				if (man_hourResult == null || man_hourResult.isEmpty()) {
					man_hourResult = " ";
				}

				String full_nameResult = rs.getString("full_name");
				if (full_nameResult == null || full_nameResult.isEmpty()) {
					full_nameResult = " ";
				}

				String remarksResult = rs.getString("remarks");
				if (remarksResult == null || remarksResult.isEmpty()) {
					remarksResult = " ";
				}

				dbResult.add(item_idResult + "/"
						+ project_titleResult + "/"
						+ status_nameResult + "/"
						+ priorityResult + "/"
						+ delivery_dateResult + "/"
						+ man_hourResult + "/"
						+ full_nameResult + "/"
						+ remarksResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dbResult;
	}

	public int idGet() throws Exception {
		int newItemId = 0;
		try {
			stmt2 = con.createStatement();
			String SQL1 = new String("SELECT MAX(item_id) AS item_id FROM project_table");
			ResultSet rs = stmt2.executeQuery(SQL1);
			while (rs.next()) {
				newItemId = rs.getInt("item_id") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newItemId;
	}

	public ArrayList<String>[] nameGet() throws Exception {
		ArrayList<String> full_name = new ArrayList<>();
		ArrayList<String> staff_id = new ArrayList<>();

		try {
			stmt2 = con.createStatement();
			String SQL2 = new String("SELECT last_name + first_name AS full_name,staff_id FROM staff_master");
			ResultSet rs = stmt2.executeQuery(SQL2);
			while (rs.next()) {
				full_name.add(rs.getString("full_name"));
				staff_id.add(rs.getString("staff_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new ArrayList[] { full_name, staff_id };
	}

	public String insert(String status, String itemId, String projectTitle, String priority, String deliveryDate,
			String manHour, String staffId, String remarks) throws Exception {
		String resultMessage = "";
		try {
			int statusId = 0;
			switch (status) {
			case "in_progress" -> statusId = 1;
			case "complete" -> statusId = 2;
			case "interrupt" -> statusId = 3;
			case "trouble" -> statusId = 4;
			case "incomplete" -> statusId = 5;
			}
			//この書き方だとSQLインジェクションの危険性があるかもしれない
			//ちょっと試してみたい
			StringBuilder SQL = new StringBuilder("INSERT INTO project_table "
					+ "(item_id, project_title, status_id, priority, "
					+ "delivery_date, man_hour,contact_id, project_table.remarks)"
					+ "VALUES (");
			SQL.append(itemId);
			if (projectTitle != null && !projectTitle.isEmpty()) {
				SQL.append(",'" + projectTitle + "'");
			}
			if (statusId >= 1 && statusId <= 5) {
				SQL.append("," + statusId);
			}
			if (priority != null && !priority.isEmpty()) {
				SQL.append("," + priority);
			} else {
				SQL.append(",NULL");
			}
			if (deliveryDate != null && !deliveryDate.isEmpty()) {
				SQL.append(",'" + deliveryDate + "'");
			} else {
				SQL.append(",NULL");
			}
			if (manHour != null && !manHour.isEmpty()) {
				SQL.append("," + manHour);
			} else {
				SQL.append(",NULL");
			}
			if (staffId != null && !staffId.isEmpty()) {
				SQL.append(",'" + staffId + "'");
			} else {
				SQL.append(",NULL");
			}
			if (remarks != null && !remarks.isEmpty()) {
				SQL.append(",'" + remarks + "'");
			} else {
				SQL.append(",NULL");
			}
			SQL.append(")");
			stmt = con.prepareStatement(SQL.toString());
			stmt.executeUpdate();

			resultMessage = "登録しました";

		} catch (SQLException e) {
			resultMessage = "Error";
		}
		return resultMessage;
	}

	public String update(String status, String projectTitle, String priority, String deliveryDate, String manHour,
			String staffId, String remarks, String itemId) {
		String resultMessage = "";
		try {
			int statusId = 0;
			switch (status) {
			case "in_progress" -> statusId = 1;
			case "complete" -> statusId = 2;
			case "interrupt" -> statusId = 3;
			case "trouble" -> statusId = 4;
			case "incomplete" -> statusId = 5;
			}
			StringBuilder SQL = new StringBuilder("UPDATE project_table SET ");

			if (projectTitle != null && !projectTitle.isEmpty()) {
				SQL.append("project_title = '" + projectTitle + "'");
			}
			if (statusId >= 1 && statusId <= 5) {
				SQL.append(",status_id = " + statusId);
			}
			if (priority != null && !priority.isEmpty()) {
				SQL.append(",priority = " + priority);
			} else {
				SQL.append(",priority = NULL");
			}
			if (deliveryDate != null && !deliveryDate.isEmpty()) {
				SQL.append(",delivery_date = '" + deliveryDate + "'");
			} else {
				SQL.append(",delivery_date = NULL");
			}
			if (manHour != null && !manHour.isEmpty()) {
				SQL.append(",man_hour = " + manHour);
			} else {
				SQL.append(",man_hour = NULL");
			}
			if (staffId != null && !staffId.isEmpty()) {
				SQL.append(",contact_id = '" + staffId + "'");
			} else {
				SQL.append(",contact_id = NULL");
			}
			if (remarks != null && !remarks.isEmpty()) {
				SQL.append(",remarks = '" + remarks + "'");
			} else {
				SQL.append(",remarks = NULL");
			}
			SQL.append(" WHERE item_id = " + itemId);
			stmt = con.prepareStatement(SQL.toString());
			stmt.executeUpdate();

			resultMessage = "更新しました";

		} catch (SQLException e) {
			resultMessage = "Error";
		}
		return resultMessage;
	}

	public String delete(String itemId) throws Exception {
		String resultMessage = "";
		try {
			StringBuilder SQL = new StringBuilder("DELETE FROM project_table WHERE ");
			SQL.append("item_id = " + itemId);
			stmt = con.prepareStatement(SQL.toString());
			stmt.executeUpdate();
			resultMessage = "削除しました";

		} catch (Exception e) {
			resultMessage = "Error";
		}
		return resultMessage;
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt2 != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs = null;
			stmt2 = null;
			stmt = null;
			con = null;
		}
	}
}
