package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;
import java.util.List;

import beans.Branch;
import dao.BranchDao;

public class BranchService {


	public List<Branch> getBranch() {

		Connection connection = null;
		try {
			connection = getConnection();

			//PostDao postDao = new PostDao();
			List<Branch> ret = BranchDao.getBranch(connection);

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



	public Branch selectBranch(String branchName) {

		Connection connection = null;
		try {
			connection = getConnection();

			//PostDao postDao = new PostDao();
			Branch ret = BranchDao.selectBranch(connection, branchName);

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
