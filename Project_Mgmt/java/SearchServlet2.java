package project_mgmt2.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SearchServlet2")
public class SearchServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String projectTitle = request.getParameter("project_title");
		session.setAttribute("project_title", projectTitle);
		String status = request.getParameter("status");
		session.setAttribute("status_name", status);
		String startDeliveryDate = request.getParameter("start_delivery_date");
		session.setAttribute("start_delivery_date", startDeliveryDate);
		String endDeliveryDate = request.getParameter("end_delivery_date");
		session.setAttribute("end_delivery_date", endDeliveryDate);
		String name = request.getParameter("name");
		session.setAttribute("staff_name", name);

		ArrayList<String> dbResult = new ArrayList<>();
		DBManager db = new DBManager();
		try {
			db.open();
			dbResult = db.select(projectTitle, status, startDeliveryDate, endDeliveryDate, name);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		session.setAttribute("dbResult", dbResult);
		request.getRequestDispatcher("search_display2.jsp").forward(request, response);
	}
}
