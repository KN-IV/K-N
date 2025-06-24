package project_mgmt2.web;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class EditServlet
 */
@WebServlet("/EditServlet2")
public class EditServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> full_name = new ArrayList<>();
		ArrayList<String> staff_id = new ArrayList<>();
		String item_id = request.getParameter("item_id");
		int newItem_id = 0;

		DBManager db = new DBManager();
		try {
			try {
				db.open();
				newItem_id = db.idGet();

				ArrayList<String>[] lists = db.nameGet();
				full_name = lists[0];
				staff_id = lists[1];
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpSession session = request.getSession();

		ArrayList<String> dbResult = (ArrayList<String>) session.getAttribute("dbResult");
		String no = request.getParameter("no");
		if (no != null && !no.isEmpty()) {
			Integer resultListNo = Integer.parseInt(no) - 1;

			String[] data = dbResult.get(resultListNo).split("/");
			request.setAttribute("project_title", data[1]);
			request.setAttribute("status_name", data[2]);
			request.setAttribute("priority", data[3]);
			request.setAttribute("delivery_date", data[4]);
			request.setAttribute("man_hour", data[5]);
			request.setAttribute("staff_name", data[6]);
			request.setAttribute("remarks", data[7]);
		}

		request.setAttribute("full_name", full_name);
		request.setAttribute("staff_id", staff_id);
		request.setAttribute("item_id", item_id);
		request.setAttribute("newItem_id", newItem_id);
		request.getRequestDispatcher("project_edit2.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String itemId = request.getParameter("item_id");
		String projectTitle = request.getParameter("project_title");
		String status = request.getParameter("status");
		String priority = request.getParameter("priority");
		String deliveryDate = request.getParameter("delivery_date");
		String manHour = request.getParameter("man_hour");
		String staffId = request.getParameter("staff_id");
		String remarks = request.getParameter("remarks");

		String resultMessage = "";

		DBManager db = new DBManager();
		try {
			db.open();

			switch (action) {
			case "update":
				try {
					resultMessage = db.update(status, projectTitle, priority, deliveryDate, manHour, staffId, remarks,
							itemId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "delete":
				try {
					resultMessage = db.delete(itemId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case "register":
				try {
					resultMessage = db.insert(status, itemId, projectTitle, priority, deliveryDate, manHour, staffId,
							remarks);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		session.invalidate();
		request.setAttribute("resultMessage", resultMessage);
		request.getRequestDispatcher("search_display2.jsp").forward(request, response);
	}
}
