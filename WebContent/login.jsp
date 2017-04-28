<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ログイン</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3>ログイン</h3>

<form action="login" method="post"><br />
	<label for="loginID">ログインID : </label>
	<input name="loginID" id="loginID"/> <br />

	<label for="password">パスワード : </label>
	<input name="password" type="password" id="password"/> <br />

	<input type="submit" value="ログイン" /> <br />
</form>
<a href="./">戻る</a> <br>
</body>
</html>