<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ユーザー編集</title>
	<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<center>
		<font size="7">わったい</font>
			<a href="./" style="color:black;text-decoration:none"><font size="15" color="green"><b>菜</b></font></a>
			<font size="7">掲示板</font><br>
		<h3>ユーザー編集</h3>
	</center>

	<center><table border="0" width="660">
		<tr>
			<td align="left" valign="middle">
				<div class="path">
					<a href="./">ホーム</a>　>　
					<a href="./useradmin">ユーザー管理</a>　>　
					ユーザー編集
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
			<c:remove var="errorMessages" scope="session"/>
		</c:if>


		<form action="useredit" method="post"><br />
			<table  class="signUp">
				<c:forEach items="${selectedUser}" var="user">
					<input type="hidden" name="id" value="${user.id}">
					<input type="hidden" name="selectedLoginId" value="${user.loginId}">
					<input type="hidden" name="previousPassword" value="${user.password}">

					<tbody>
						<tr>
							<th class="arrow_box">ログインID<br>(半角英数字6文字以上20文字以下)</th>
							<td><input name="loginId" value="${user.loginId}" id="loginId"/></td>
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
							<td>
								<input name="name" value="${user.name}" id="name" />
							</td>
							<td>
								<c:if test="${loginUser.id == user.id}">
									<input type="submit" value="登録" />
								</c:if>
							</td>
						</tr>

						<c:if test="${loginUser.id == user.id}">
							<c:forEach items="${branches}" var="branch">
								<c:if test="${user.branchId == branch.id}">
									<input type="hidden" name="branchName" value="${branch.name}">
								</c:if>
							</c:forEach>
							<c:forEach items="${departments}" var="department">
								<c:if test="${user.departmentId == department.id}">
									<input type="hidden" name="departmentName" value="${department.name}">
								</c:if>
							</c:forEach>
						</c:if>

						<c:if test="${loginUser.id != user.id}">
							<tr>
								<th class="arrow_box">支店</th>
								<td>
									<select name="branchName">
										<c:if test="${empty branchValue}">
											<c:forEach items="${branches}" var="branch">
												<c:if test="${user.branchId == branch.id}">
													<option value="${branch.name}">${branch.name}</option>
												</c:if>
											</c:forEach>
											<option value="" class="mainTag">支店</option>
										</c:if>


										<c:if test="${!empty branchValue}">
											<option value="${branchValue}">${branchValue}</option>
											<option value="" class="mainTag">支店</option>
										</c:if>

										<c:forEach items="${branches}" var="branch">
											<c:if test="${branchValue != branch.name && user.branchId != branch.id}">
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
											<c:forEach items="${departments}" var="department">
												<c:if test="${user.departmentId == department.id}">
													<option value="${department.name}">${department.name}</option>
												</c:if>
											</c:forEach>
											<option value="" class="mainTag">部署・役職</option>
										</c:if>
										<c:if test="${!empty departmentValue}">
											<option value="${departmentValue}">${departmentValue}</option>
										<option value="" class="mainTag">部署・役職</option>
										</c:if>

										<c:forEach items="${departments}" var="department">
											<c:if test="${departmentValue != department.name && user.departmentId != department.id}">
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
						</c:if>
					</tbody>

				</c:forEach>
			</table>
		</form>

	</center>
</body>
</html>