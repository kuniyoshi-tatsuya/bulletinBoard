package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.Comment;
import exception.SQLRuntimeException;

public class CommentDao {

	public static List<Comment> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM comments ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			List<Comment> ret = toCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append(" text");
			sql.append(", insert_date");
			sql.append(", user_id");
			sql.append(", post_id");
			sql.append(") VALUES (");
			sql.append(" ?"); // loginId
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(", ?"); // password
			sql.append(", ?"); // departmentId
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, comment.getText());
			ps.setString(2, comment.getUserId());
			ps.setString(3, comment.getPostId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, String deleteCommentId) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, deleteCommentId);
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

	private static List<Comment> toCommentList(ResultSet rs)
			throws SQLException {

		List<Comment> ret = new ArrayList<Comment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userId = rs.getString("user_id");
				String postId = rs.getString("post_id");

				Comment comment = new Comment();
				comment.setId(id);
				comment.setText(text);
				comment.setInsertDate(insertDate);
				comment.setUserId(userId);
				comment.setPostId(postId);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
