<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ユーザー管理</title>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<center>
			<font size="7">わったい</font>
			<a href="./" style="color:black;text-decoration:none"><font size="15" color="green"><b>菜</b></font></a>
			<font size="7">掲示板</font><br>
			<h3>ユーザー管理</h3>
		</center>

		<center><table border="0" width="860">
			<tr>
				<td align="left" valign="middle">
					<div class="path">
						<a href="./">ホーム</a>　>　
						ユーザー管理
						　<a href="./">戻る</a>
					</div>

				</td>
			</tr>
		</table></center>
		<hr width="800" size="1" align="center"><br>

		<center>
			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
						<c:forEach items="${errorMessages}" var="message">
							<font color="#ff0000"><c:out value="${message}" /></font><br>
						</c:forEach>
				</div>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>
		</center>

		<table border="1" class="newUser">
			<tbody>
				<tr>
					<th>ユーザー新規登録</th>

					<td class="arrow_box"><input type="button" onclick="location.href='./signup'"value="登録"></td>
				</tr>
			</tbody>
		</table><br>

		<center>
			<table border="1" class="messages">
				<tr><th>ログインID</th><th>名前</th><th>支店</th><th>部署・役職</th><th>編集</th><th>停止/復活</th><th>削除</th></tr>

				<c:forEach items="${users}" var="user">
					<tr>
						<td><c:out value="${user.loginId}"/></td>
						<td><c:out value="${user.name}"/></td>
						<td>
							<c:forEach items="${branches}" var="branch">
								<c:if test="${branch.id == user.branchId}">
									<c:out value="${branch.name}"/>
								</c:if>
							</c:forEach>
						</td>

						<td>
							<c:forEach items="${departments}" var="department">
								<c:if test="${department.id == user.departmentId}">
									<c:out value="${department.name}"/>
								</c:if>
							</c:forEach>
						</td>

						<td>
						<center>
							<form action="useredit" method="get">
								<input type="hidden" name="userId" value="${user.id}">
								<input type="submit" value="編集" />
							</form>
						</center>
						</td>

						<c:if test="${loginUser.id != user.id}">
							<td>
							<center>
								<form action="useradmin" method="get">
									<c:if test="${user.userExsist == 1}">
										<input type="hidden" name="selectedExsistId" value="${user.id}">
										<input type="hidden" name="userExsist" value="0">
										<input type="submit" style="color:white;background-color:red;" value="停止する" onclick='return confirm("本当によろしいですか．");'/>
									</c:if>
									<c:if test="${user.userExsist == 0}">
										<input type="hidden" name="selectedExsistId" value="${user.id}">
										<input type="hidden" name="userExsist" value="1">
										<input type="submit" value="復活させる" onclick='return confirm("本当によろしいですか．");'/>
									</c:if>
								</form>
							</center>
							</td>
						</c:if> <c:if test="${loginUser.id == user.id}"><td></td></c:if>

						<c:if test="${loginUser.id != user.id}">
							<td>
							<center>
								<form action="./delete" method="post">
									<input type="hidden" name="deleteUserId" value="${user.id}">
									<input type="submit" style="color:white;background-color:red;" name="deleteUserButton" value="削除" onclick='return confirm("本当に削除してもよろしいですか．");'/>
								</form>
							</center>
							</td>
						</c:if> <c:if test="${loginUser.id == user.id}"><td></td></c:if>



					</tr>
				</c:forEach>
			</table>
		</center>
	</body>
</html>