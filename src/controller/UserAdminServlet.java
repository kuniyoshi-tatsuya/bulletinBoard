package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.Branch;
import beans.Department;
import beans.User;
import service.BranchService;
import service.DepartmentService;
import service.UserService;

@WebServlet(urlPatterns = { "/useradmin" })
public class UserAdminServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		if(!StringUtils.isEmpty(request.getParameter("userExsist"))){
			String userExsist = request.getParameter("userExsist");
			String selectedExsistId = request.getParameter("selectedExsistId");
			new UserService().updateUserExsist(userExsist, selectedExsistId);
		}else if(request.getParameter("userExsist") != null){
			List<String> errorMessage = new ArrayList<String>();
			errorMessage.add("状態の選択をしてください．");
			request.getSession().setAttribute("errorMessages", errorMessage);
		}

		List<User> users = new UserService().getUserAsc();
		List<Branch> branches = new BranchService().getBranch();
		List<Department> departments = new DepartmentService().getDepartment();
		request.setAttribute("users", users);
		request.setAttribute("branches", branches);
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/useradmin.jsp").forward(request, response);
	}

}
