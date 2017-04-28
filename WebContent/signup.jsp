<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規ユーザー登録</title>
</head>
<body>
<h3>新規ユーザー登録</h3>
<form action="signup" method="post"><br />
	<label for="loginID">ログインID : </label>
	<input name="loginID" id="loginID"/> <br />

	<label for="password">パスワード : </label>
	<input name="password" type="password" id="password"/> <br />

	<label for="password">確認用 : </label>
	<input name="password" type="password" id="password"/> <br />

	<label for="name">名前 : </label>
	<input name="name" id="name"/> <br />

	<label for="branch">支店 : </label>
	<input name="branch" id="branch"/> <br />

	<label for="department">部署・役職 : </label>
	<input name="department" id="department"/> <br />

	<input type="submit" value="登録" /> <br />
</form>

</body>
</html>