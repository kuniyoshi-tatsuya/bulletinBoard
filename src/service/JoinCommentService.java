package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;
import java.util.List;

import beans.JoinComment;
import dao.JoinCommentDao;

public class JoinCommentService {

	private static final int LIMIT_NUM = 1000;

	public List<JoinComment> joinComments(String startDate, String currentDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			//PostDao postDao = new PostDao();
			List<JoinComment> ret = JoinCommentDao.joinComments(connection, LIMIT_NUM, startDate, currentDate);

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
