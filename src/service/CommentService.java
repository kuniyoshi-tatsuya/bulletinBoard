package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;
import java.util.List;

import beans.Comment;
import dao.CommentDao;

public class CommentService {

	public void register(Comment message) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, message);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void deleteComment(String deleteCommentId) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.delete(connection, deleteCommentId);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	private static final int LIMIT_NUM = 1000;

	public List<Comment> getMessage() {

		Connection connection = null;
		try {
			connection = getConnection();

			//CommentDao commentDao = new CommentDao();
			List<Comment> ret = CommentDao.getUserComments(connection, LIMIT_NUM);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}
