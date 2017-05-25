package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import beans.Comment;
import beans.JoinComment;
import beans.JoinPost;
import beans.Post;
import beans.User;
import service.CommentService;
import service.JoinCommentService;
import service.JoinPostService;
import service.PostService;
import service.UserService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		String startDate = getStartDate();
		String currentDate = getCurrentDate();
		List<User> usersBranch = new UserService().getUser();
		request.setAttribute("usersBranch", usersBranch);

		String messageCategory = request.getParameter("categories");
		if(!StringUtils.isEmpty(request.getParameter("startDate"))) startDate = request.getParameter("startDate");
		if(!StringUtils.isEmpty(request.getParameter("currentDate"))) currentDate = request.getParameter("currentDate");
		List<JoinPost> messages = new JoinPostService().joinPosts(startDate, currentDate, messageCategory);
		List<JoinComment> comments = new JoinCommentService().joinComments(startDate, currentDate);

		List<Post> categories = new PostService().getCategory();
		request.setAttribute("categories", categories);

		if(messageCategory == null){
			request.setAttribute("selectCategory", null);
			String selectMinDate = getStartDate();
			request.setAttribute("selectMinDate", selectMinDate);
		}else{
			request.setAttribute("selectCategory", messageCategory);
		}
		request.setAttribute("selectStartDate", startDate);
		request.setAttribute("selectCurrentDate", currentDate);
		if(messages != null) request.setAttribute("messages", messages);
		if(comments != null) request.setAttribute("comments", comments);
		request.getRequestDispatcher("/home.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		String commentText = request.getParameter("commentText");
		String postId = request.getParameter("postId");

		request.getSession().setAttribute("notification", postId);

		if(isValid(request, commentText)){
			User user = (User)request.getSession().getAttribute("loginUser");
			Comment comment = new Comment();
			comment.setText(commentText);
			comment.setPostId(postId);
			comment.setUserId(user.getId());
			new CommentService().register(comment);
			request.getSession().setAttribute("isComment", "1");
			response.sendRedirect("./");
		}
		else{
			request.getSession().setAttribute("isComment", "0");
			response.sendRedirect("./");
		}

	}

	private boolean isValid(HttpServletRequest request, String commentText){

		List<String> errorMessages = new ArrayList<String>();
		if(commentText.length() > 500) errorMessages.add("コメントは500文字以下で入力してください");
		if(commentText.isEmpty()) errorMessages.add("空のままコメントすることはできません");

		if(!errorMessages.isEmpty()){
			request.getSession().setAttribute("errorMessages", errorMessages);
			request.getSession().setAttribute("wrongComment", commentText);
			return false;
		}else return true;
	}

	public static String getCurrentDate(){

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String str = dateFormat.format(date);

		return Pattern.compile("/").matcher(str).replaceAll("-");
	}

	public static String getStartDate(){

		Post minDatePost = new PostService().getMinDate();
		Date dateMin = minDatePost.getInsertDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String str = dateFormat.format(dateMin);

		return Pattern.compile("/").matcher(str).replaceAll("-");
	}
}