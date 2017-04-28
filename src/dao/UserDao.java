package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;
//import exception.NoRowsUpdatedRuntimeException;
import exception.SQLRuntimeException;
//import utils.StreamUtil;

public class UserDao {

	public User getUser(Connection connection, String loginID, String password){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM user WHERE loginID = ? AND password = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginID);
			ps.setString(2, password);

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


	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {

				String loginID = rs.getString("loginID");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String branch = rs.getString("branch");
				String department = rs.getString("department");
				String userExsist = rs.getString("user");

				User user = new User();
				user.setLoginID(loginID);
				user.setPassword(password);
				user.setName(name);
				user.setBranch(branch);
				user.setDepartment(department);
				user.setUser(userExsist);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
