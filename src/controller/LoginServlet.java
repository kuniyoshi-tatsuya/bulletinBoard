package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		String loginID = request.getParameter("loginID");
		String password = request.getParameter("password");

		LoginService loginService = new LoginService();
		User user = loginService.login(loginID, password);
		System.out.println(user.getLoginID() + ", " + user.getPassword());

		HttpSession session = request.getSession();
		if (user != null) {
			session.setAttribute("loginUser", user);
			response.sendRedirect("./home.jsp");
		}
		//else エラー処理を加える

	}
}
