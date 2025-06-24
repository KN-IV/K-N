package janken2.web;

import java.io.IOException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class JankenJudge2
 */
@WebServlet("/JankenJudge2")
public class JankenJudge2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		//totalCntを受け取る初回のみ動作
		if (request.getParameter("totalcnt") != null) {
			session.setAttribute("totalCount", Integer.parseInt(request.getParameter("totalcnt")));
			session.setAttribute("currentCount", 1);
			session.setAttribute("win", 0);
			session.setAttribute("lose", 0);
			session.setAttribute("draw", 0);
			request.getRequestDispatcher("HandSelect2.jsp").forward(request, response);
			return;
		}

		int currentCount = (int) session.getAttribute("currentCount");
		int totalCount = (int) session.getAttribute("totalCount");
		int win = (int) session.getAttribute("win");
		int lose = (int) session.getAttribute("lose");
		int draw = (int) session.getAttribute("draw");

		String playerHand = request.getParameter("hand");

		int player = Integer.parseInt(playerHand);//String型の整数をint型に変換
		int cpu = new Random().nextInt(3);
		int result = (player - cpu + 3) % 3;

		switch (result) {
		case 0:
			draw = (int) session.getAttribute("draw");
			break;
		case 1:
			session.setAttribute("lose", lose - 1);
			lose = (int) session.getAttribute("lose");
			break;
		case 2:
			session.setAttribute("win", win + 1);
			win = (int) session.getAttribute("win");
			break;
		}

		request.setAttribute("playerHand", getHandName(player));
		request.setAttribute("cpuHand", getHandName(cpu));
		request.setAttribute("result", getResultText(result));

		String currentResult = currentCount + "回目　あなた：" + getHandName(player)
				+ "　CPU：" + getHandName(cpu) + "　勝敗：" + getResultText(result) + "　点数：" + (win + lose);

		String totalResult = (String) session.getAttribute("totalResult");
		if (totalResult == null)
			totalResult = "";
		else
			totalResult += "<br>";

		totalResult += currentResult;

		session.setAttribute("totalResult", totalResult);

		if (currentCount >= totalCount) {
			//request.setAttribute("totalResult", totalResult);
			request.getRequestDispatcher("totalResult.jsp").forward(request, response);
		} else {
			session.setAttribute("currentCount", currentCount + 1);
			request.getRequestDispatcher("JankenResult2.jsp").forward(request, response);
		}
	}

	private String getHandName(int hand) {
		return switch (hand) {
		case 0 -> "✊";
		case 1 -> "✌";
		case 2 -> "✋";
		default -> "Error";
		};
	}

	private String getResultText(int result) {
		return switch (result) {
		case 0 -> "あいこ";
		case 1 -> "負け";
		case 2 -> "勝ち";
		default -> "Error";
		};
	}

}
