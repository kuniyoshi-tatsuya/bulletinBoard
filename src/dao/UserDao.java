package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;
import exception.NoRowsUpdatedRuntimeException;
import exception.SQLRuntimeException;
//import utils.StreamUtil;

public class UserDao {

	public User getLoginUser(Connection connection, String loginId, String password){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ? AND user_exsist = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);
			ps.setString(3, "1");

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(connection);
		}
	}

	public List<User> getUser(Connection connection){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users";
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty()) {
				return null;
			}else {
				return userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<User> getUserAsc(Connection connection, int num){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users ORDER BY department_id DESC limit " + num;
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty()) {
				return null;
			}else {
				return userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public List<User> getSelectedUser(Connection connection, String userId){

		PreparedStatement ps = null;
		try {

			String sql = "SELECT * FROM users Where id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, userId);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty()) {
				return null;
			}else {
				return userList;
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append(" login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(") VALUES (");
			sql.append(" ?"); // loginId
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branchId
			sql.append(", ?"); // departmentId

			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchId());
			ps.setString(5, user.getDepartmentId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, String deleteUserId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM users WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, deleteUserId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User user) {

		int i = 5;

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			if(user.getPassword().length() != 0) sql.append(", password = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getBranchId());
			ps.setString(4, user.getDepartmentId());
			if(user.getPassword().length() != 0){
				ps.setString(5, user.getPassword());
				i = 6;
			}
			ps.setString(i, user.getId());

			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String branchId = rs.getString("branch_id");
				String departmentId = rs.getString("department_id");
				String userExsist = rs.getString("user_exsist");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setUserExsist(userExsist);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void updateUserExsist(Connection connection, String userExsist, String selectExsistId){

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" user_exsist = ?");
			sql.append(" WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, userExsist);
			ps.setString(2, selectExsistId);

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
