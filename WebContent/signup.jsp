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
		<link href="./css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<center>
			<font size="7">わったい</font>
			<a href="./" style="color:black;text-decoration:none"><font size="15" color="green"><b>菜</b></font></a>
			<font size="7">掲示板</font><br>
			<h3>新規ユーザー登録</h3>
		</center>

		<center><table border="0" width="660">
			<tr>
				<td align="left" valign="middle">
					<div class="path">
						<a href="./">ホーム</a>　>　
						<a href="./useradmin">ユーザー管理</a>　>　
						新規ユーザー登録
						　<a href="./useradmin">戻る</a>
					</div>

				</td>
			</tr>
		</table></center>

		<hr width="600" size="1" align="center"><br>

		<center>
			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
						<c:forEach items="${errorMessages}" var="message">
							<font color="#ff0000"><c:out value="${message}" /></font><br>
						</c:forEach>
				</div>
				<c:remove var="errorMessages" scope="session"/><br>
			</c:if>
		</center>

		<form action="signup" method="post">
			<table class="signUp">
				<tbody>
					<tr>
						<th class="arrow_box">ログインID<br>(半角英数字6文字以上20文字以下)</th>
						<td><input name="loginId" id="loginId" value="${wrongLoginId}" /></td>
					</tr>
					<tr>
						<th class="arrow_box">パスワード<br>(記号含む半角英数字6文字以上255文字以下)</th>
						<td><input name="password" type="password" id="password"/></td>
					</tr>
					<tr>
						<th class="arrow_box">パスワード<br>(確認用)</th>
						<td><input name="passwordCheck" type="password" id="password"/></td>
					</tr>
					<tr>
						<th class="arrow_box">名前<br>(10文字以下)</th>
						<td><input name="name" id="name" value="${wrongName}"/></td>
					</tr>
					<tr>
						<th class="arrow_box">支店</th>
						<td>
							<select name="branchName">
								<c:if test="${empty branchValue}">
									<option value="" class="mainTag">支店</option>
								</c:if>
								<c:if test="${!empty branchValue}">
									<option value="${branchValue}">${branchValue}</option>
									<option value="" class="mainTag">支店</option>
								</c:if>

								<c:forEach items="${branches}" var="branch">
									<c:if test="${branchValue != branch.name}">
										<option value="${branch.name}">${branch.name}</option>
									</c:if>
								</c:forEach>
								<c:remove var="branchValue" scope="session"/>
							</select>
						</td>
					</tr>
					<tr>
						<th class="arrow_box">部署・役職</th>
						<td>
							<select name="departmentName">
								<c:if test="${empty departmentValue}">
									<option value="" class="mainTag">部署・役職</option>
								</c:if>
								<c:if test="${!empty departmentValue}">
									<option value="${departmentValue}">${departmentValue}</option>
									<option value="" class="mainTag">部署・役職</option>
								</c:if>

								<c:forEach items="${departments}" var="department">
									<c:if test="${departmentValue != department.name}">
										<option value="${department.name}">${department.name}</option>
									</c:if>
								</c:forEach>
								<c:remove var="departmentValue" scope="session"/>
							</select>
							　　<input type="submit" value="登録" />
							<c:remove var="wrongLoginId" scope="session"/>
							<c:remove var="wrongName" scope="session"/>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</body>
</html>