package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Department;
import exception.SQLRuntimeException;

public class DepartmentDao {

	public static List<Department> getDepartment(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM departments ORDER BY id DESC");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Department> ret = toDepartmentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public static Department selectDepartment(Connection connection, String departmentName) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM departments where name = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, departmentName);

			ResultSet rs = ps.executeQuery();
			Department ret = toDepartment(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private static Department toDepartment(ResultSet rs)
			throws SQLException {

		Department ret = new Department();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Department department = new Department();
				department.setId(id);
				department.setName(name);

				ret = department;
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private static List<Department> toDepartmentList(ResultSet rs)
			throws SQLException {

		List<Department> ret = new ArrayList<Department>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Department department = new Department();
				department.setId(id);
				department.setName(name);

				ret.add(department);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}