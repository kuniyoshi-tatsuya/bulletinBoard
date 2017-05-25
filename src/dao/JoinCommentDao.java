package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.JoinComment;
import exception.SQLRuntimeException;

public class JoinCommentDao {

	public static List<JoinComment> joinComments(Connection connection, int num,
			String startDate, String currentDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT");
			sql.append(" comments.id as id");
			sql.append(", text");
			sql.append(", insert_date");
			sql.append(", user_id");
			sql.append(", post_id");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(" FROM comments LEFT JOIN");
			sql.append(" users ON");
			sql.append(" comments.user_id = users.id");
			sql.append(" where insert_date >= ?");
			sql.append(" and insert_date <= ?");
			sql.append(" ORDER BY insert_date ASC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, currentDate);

			ResultSet rs = ps.executeQuery();
			List<JoinComment> ret = toJoinCommentList(rs);
			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private static List<JoinComment> toJoinCommentList(ResultSet rs)
			throws SQLException {

		List<JoinComment> ret = new ArrayList<JoinComment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userId = rs.getString("user_id");
				String postId = rs.getString("post_id");
				String name = rs.getString("name");
				String branchId = rs.getString("branch_id");


				JoinComment joinComment = new JoinComment();
				joinComment.setId(id);
				joinComment.setText(text);
				joinComment.setInsertDate(insertDate);
				joinComment.setUserId(userId);
				joinComment.setPostId(postId);
				joinComment.setName(name);
				joinComment.setBranchId(branchId);

				ret.add(joinComment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
