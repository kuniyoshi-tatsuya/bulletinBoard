package service;

import static utils.CloseableUtil.*;
import static utils.DButil.*;

import java.sql.Connection;

import beans.User;
import dao.UserDao;
import utils.CipherUtil;

public class LoginService {

	public User login(String loginId, String password){

		Connection connection = null;
		try {
			connection = getConnection();
			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getLoginUser(connection, loginId, encPassword); //sql文

			return user;
		} catch (RuntimeException e) {
			throw e;
		} catch (Error e) {
			throw e;
		} finally {
			close(connection);
		}
	}

}
