package dao;

import static utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import beans.JoinPost;
import exception.SQLRuntimeException;

public class JoinPostDao {

	public static List<JoinPost> joinPosts(Connection connection, int num,
			String startDate, String currentDate, String messageCategory) {
		PreparedStatement ps = null;
		Matcher m1 = Pattern.compile("^[^ -~｡-ﾟ]+$").matcher(startDate);
		Matcher m2 = Pattern.compile("^[^ -~｡-ﾟ]+$").matcher(currentDate);

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT");
			sql.append(" posts.id as id");
			sql.append(", subject");
			sql.append(", text");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", user_id");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(" FROM POSTS LEFT JOIN");
			sql.append(" users ON");
			sql.append(" posts.user_id = users.id");
			sql.append(" where insert_date >= ?");
			sql.append(" and insert_date <= ?");
			if(!StringUtils.isEmpty(messageCategory)) sql.append(" and category = ?");
			sql.append(" ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, startDate);
			ps.setString(2, currentDate);
			if(!StringUtils.isEmpty(messageCategory)) ps.setString(3, messageCategory);

			ResultSet rs = ps.executeQuery();
			List<JoinPost> ret = toJoinPostList(rs);
			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private static List<JoinPost> toJoinPostList(ResultSet rs)
			throws SQLException {

		List<JoinPost> ret = new ArrayList<JoinPost>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userId = rs.getString("user_id");
				String name = rs.getString("name");
				String branchId = rs.getString("branch_id");


				JoinPost joinPost = new JoinPost();
				joinPost.setId(id);
				joinPost.setSubject(subject);
				joinPost.setText(text);
				joinPost.setCategory(category);
				joinPost.setInsertDate(insertDate);
				joinPost.setUserId(userId);
				joinPost.setName(name);
				joinPost.setBranchId(branchId);

				ret.add(joinPost);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
