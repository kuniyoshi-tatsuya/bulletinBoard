package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Branch;
import exception.SQLRuntimeException;

public class BranchDao {

	public static List<Branch> getBranch(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM branches");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Branch> ret = toBranchList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public static Branch selectBranch(Connection connection, String branchName) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM branches where name = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, branchName);

			ResultSet rs = ps.executeQuery();
			Branch ret = toBranch(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private static Branch toBranch(ResultSet rs)
			throws SQLException {

		Branch ret = new Branch();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branch branch = new Branch();
				branch.setId(id);
				branch.setName(name);

				ret = branch;
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private static List<Branch> toBranchList(ResultSet rs)
			throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branch branch = new Branch();
				branch.setId(id);
				branch.setName(name);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}