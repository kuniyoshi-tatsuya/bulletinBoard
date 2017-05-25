package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;
import java.util.List;

import beans.JoinPost;
import dao.JoinPostDao;

public class JoinPostService {

	private static final int LIMIT_NUM = 1000;

	public List<JoinPost> joinPosts(String startDate, String currentDate, String messageCategory) {

		Connection connection = null;
		try {
			connection = getConnection();

			List<JoinPost> ret = JoinPostDao.joinPosts(connection, LIMIT_NUM, startDate, currentDate, messageCategory);

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
