package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CommentService;
import service.PostService;
import service.UserService;

@WebServlet(urlPatterns = { "/delete" })
public class DeleteServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		if(request.getParameter("deletePostId") != null){
			String deletePostId = request.getParameter("deletePostId");
			new PostService().deletePost(deletePostId);
			response.sendRedirect("./");
		}

		if(request.getParameter("deleteCommentId") != null){
			String deleteCommentId = request.getParameter("deleteCommentId");
			new CommentService().deleteComment(deleteCommentId);
			response.sendRedirect("./");
		}

		if(request.getParameter("deleteUserId") != null){
			String deleteUserId = request.getParameter("deleteUserId");
			new UserService().deleteUser(deleteUserId);
			response.sendRedirect("./useradmin");
		}
	}
}