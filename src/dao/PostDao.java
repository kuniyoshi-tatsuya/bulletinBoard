package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Post;
import exception.SQLRuntimeException;

public class PostDao {

	public static List<Post> getUserMessages(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM posts ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Post> ret = toPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void insert(Connection connection, Post post) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO posts ( ");
			sql.append(" subject");
			sql.append(", text");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", user_id");
			sql.append(") VALUES (");
			sql.append(" ?"); // login
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", ?"); // department
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, post.getSubject());
			ps.setString(2, post.getText());
			ps.setString(3, post.getCategory());
			ps.setString(4, post.getUserId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, String deletePostId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM posts WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, deletePostId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

//	public void update(Connection connection, User user) {
//
//		PreparedStatement ps = null;
//		try {
//			StringBuilder sql = new StringBuilder();
//			sql.append("UPDATE user SET");
//			sql.append("  account = ?");
//			sql.append(", name = ?");
//			sql.append(", email = ?");
//			sql.append(", password = ?");
//			sql.append(", description = ?");
//			sql.append(", update_date = CURRENT_TIMESTAMP");
//			if (user.getIcon() != null) {
//				sql.append(", icon = ?");
//			}
//			sql.append(" WHERE");
//			sql.append(" id = ?");
//			sql.append(" AND");
//			sql.append(" update_date = ?");
//
//			ps = connection.prepareStatement(sql.toString());
//
//			ps.setString(1, user.getAccount());
//			ps.setString(2, user.getName());
//			ps.setString(3, user.getEmail());
//			ps.setString(4, user.getPassword());
//			ps.setString(5, user.getDescription());
//			if (user.getIcon() == null) {
//				ps.setInt(6, user.getId());
//				ps.setTimestamp(7,
//						new Timestamp(user.getUpdateDate().getTime()));
//			} else {
//				ps.setBinaryStream(6, new ByteArrayInputStream(user.getIcon()));
//				ps.setInt(7, user.getId());
//				ps.setTimestamp(8,
//						new Timestamp(user.getUpdateDate().getTime()));
//			}
//
//			int count = ps.executeUpdate();
//			if (count == 0) {
//				throw new NoRowsUpdatedRuntimeException();
//			}
//		} catch (SQLException e) {
//			throw new SQLRuntimeException(e);
//		} finally {
//			close(ps);
//		}
//
//	}

	private static List<Post> toPostList(ResultSet rs)
			throws SQLException {

		List<Post> ret = new ArrayList<Post>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userId = rs.getString("user_id");


				Post post = new Post();
				post.setId(id);
				post.setSubject(subject);
				post.setText(text);
				post.setCategory(category);
				post.setInsertDate(insertDate);
				post.setUserId(userId);

				ret.add(post);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public static List<Post> getCategory(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM posts GROUP BY category");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Post> ret = toPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public static Post getMinDate(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM posts WHERE insert_date = (");
			sql.append("SELECT MIN(insert_date) FROM posts)");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			Post ret = toPost(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private static Post toPost(ResultSet rs)
			throws SQLException {

		Post ret = new Post();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userId = rs.getString("user_id");


				Post post = new Post();
				post.setId(id);
				post.setSubject(subject);
				post.setText(text);
				post.setCategory(category);
				post.setInsertDate(insertDate);
				post.setUserId(userId);

				ret = post;
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
