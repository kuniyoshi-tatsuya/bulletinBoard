package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;
import java.util.List;

import beans.Post;
import dao.PostDao;

public class PostService {

	public void register(Post message) {

		Connection connection = null;
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			postDao.insert(connection, message);

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

	public void deletePost(String DeletePostId) {

		Connection connection = null;
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			postDao.delete(connection, DeletePostId);

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

	public List<Post> getMessage() {

		Connection connection = null;
		try {
			connection = getConnection();

			List<Post> ret = PostDao.getUserMessages(connection, LIMIT_NUM);

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

	public List<Post> getCategory() {

		Connection connection = null;
		try {
			connection = getConnection();

			List<Post> ret = PostDao.getCategory(connection);

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

	public Post getMinDate() {

		Connection connection = null;
		try {
			connection = getConnection();

			Post ret = PostDao.getMinDate(connection);

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
