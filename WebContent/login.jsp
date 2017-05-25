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

		<center>
			<font size="7">わったい</font>
			<font size="15" color="green"><b>菜</b></font>
			<font size="7">掲示板</font><br><br>
			<h3>ログイン画面</h3>


			<c:if test="${ not empty loginErrorMessages }">
				<div class="loginErrorMessages">
						<c:forEach items="${loginErrorMessages}" var="message">
							<font color="#ff0000"><c:out value="${message}" /></font><br>
						</c:forEach>
				</div>
				<c:remove var="loginErrorMessages" scope="session"/>
			</c:if>

			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
						<c:forEach items="${errorMessages}" var="message">
							<font color="#ff0000"><c:out value="${message}" /></font><br>
						</c:forEach>
				</div>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>
		</center>

		<form action="login" method="post">
			<table class="login">
				<tbody>
					<tr>
						<th class="arrow_box">ログインID</th>
						<td><input name="loginId" id="loginId" value="${wrongLoginId}"/></td>
					</tr>
					<tr>
						<th class="arrow_box">パスワード</th>
						<td><input name="password" type="password" id="password"/></td>
						<td><input type="submit"  value="ログイン" /></td>
					</tr>
				</tbody>
			</table>
			<c:remove var="wrongLoginId" scope="session"/>
		</form>

	</body>
</html>