package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;
import java.util.List;

import beans.Department;
import dao.DepartmentDao;

public class DepartmentService {


	public List<Department> getDepartment() {

		Connection connection = null;
		try {
			connection = getConnection();

			//PostDao postDao = new PostDao();
			List<Department> ret = DepartmentDao.getDepartment(connection);

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

	public Department selectDepartment(String departmentName) {

		Connection connection = null;
		try {
			connection = getConnection();

			//PostDao postDao = new PostDao();
			Department ret = DepartmentDao.selectDepartment(connection, departmentName);

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
