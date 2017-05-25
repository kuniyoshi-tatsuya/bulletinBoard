<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ホーム</title>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body>
		<center>
				<font size="7">わったい</font>
				<a href="./" style="color:black;text-decoration:none"><font size="15" color="green"><b>菜</b></font></a>
				<font size="7">掲示板</font><br>


			<table border="0" width="800">
				<tr>
					<th align="left" valign="middle">
						<a href="./newpost">新規投稿</a>
						<c:if test="${ loginUser.departmentId == 4 }"><a href="./useradmin">ユーザー管理</a></c:if>
					</th>
					<th align="right" valign="middle">
						<c:if test="${ loginUser.departmentId == 4 }"><a href="./useredit?userId=${loginUser.id}" style="color:#0000ff;text-decoration:none"><font size="5"><b>${loginUser.name}</b></font></a></c:if>
						<c:if test="${ loginUser.departmentId != 4 }"><font size="5"><b>${loginUser.name}</b></font></c:if>
						さんがログインしています．<a href="./logout">ログアウト</a>
					</th>
				</tr>
			</table>

			<hr width="800" size="1" align="center">

			<br>
			<table border="0" width="800" ><tr align="left"><th >
				<p style="display:inline;">
					<form action="./" method="get" >
						　　　　カテゴリーが
						<select name="categories">
							<c:if test="${empty selectCategory}">
								<option value="" class="mainTag">全て</option>
							</c:if>
							<c:if test="${!empty selectCategory}">
								<option value="${selectCategory}">${selectCategory}</option>
								<option value="" class="mainTag">全て</option>
							</c:if>

							<c:forEach items="${categories}" var="category">
								<c:if test="${selectCategory != category.category}">
									<option value="${category.category}">${category.category}</option>
								</c:if>
							</c:forEach>
						</select>

						日付が
						<c:if test="${selectMinDate == selectStartDate}">
							<input type="date" name="startDate" id="startDate"/>から
							<input type="date"  name="currentDate" id="currentDate"/>までを
						</c:if>
						<c:if test="${selectMinDate != selectStartDate}">
							<input type="date" name="startDate" id="startDate" value="${selectStartDate}"/>から
							<input type="date"  name="currentDate" id="currentDate" value="${selectCurrentDate}"/>までを
						</c:if>
						<input type="submit" value="絞込み" /><br><br>
					</form>
				<p style="display:inline;">
			</th></tr></table>

			<c:if test="${ not empty errorMessages }">
					<c:forEach items="${errorMessages}" var="message">
						<font color="#ff0000"><c:out value="${message}" /></font><br>
					</c:forEach>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>

			<c:if test="${ not empty adminError }">
				<ul>
					<c:forEach items="${adminError}" var="message">
						<font color="#ff0000"><c:out value="${message}" /></font><br>
					</c:forEach>
				</ul>
			</c:if>
		</center>

		<center>
			<c:if test="${isComment == 1}">コメントに投稿しました</c:if>
			<c:forEach items="${messages}" var="message">
				<c:if test="${message.id == notification}">
				<table border="2" bordercolor="#295890" style="border-collapse:collapse;"><tr><th>対象の投稿</th></tr><tr><td>
					<c:if test="${message.name == null}">
						<b><c:out value="削除されたユーザー" /></b>の投稿
					</c:if>
					<c:if test="${message.name != null}">
						<c:if test="${ loginUser.departmentId == 4 }"><a href="./useredit?userId=${message.userId}" style="color:#0000ff;text-decoration:none"><font size="4"><b>${message.name}</b></font></a></c:if>
						<c:if test="${ loginUser.departmentId != 4 }"><b><c:out value="${message.name}" /></b></c:if>
						さんの投稿
					</c:if>


					<fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm" />
					<table class="messages">
						<tbody>
							<tr>
								<th class="arrow_box">件名</th>
								<td><pre><c:out value="${message.subject}" /></pre></td>
							</tr>
							<tr>
								<th class="arrow_box">本文</th>
								<td><pre><c:out value="${message.text}" /></pre></td>
							</tr>
							<tr>
								<th class="arrow_box">カテゴリー</th>
								<td><pre><c:out value="${message.category}" /></pre></td>
							</tr>

						</tbody>
					</table>

					<c:forEach items="${comments}" var="comment">
						<c:if test="${message.id==comment.postId}">
							<c:if test="${comment.name == null}">
								　　　　　　　<b><c:out value="削除されたユーザー" /></b>のコメント
							</c:if>
							<c:if test="${comment.name != null}">
								<c:if test="${ loginUser.departmentId == 4 }">　　　　　　　<a href="./useredit?userId=${comment.userId}" style="color:#0000ff;text-decoration:none"><font size="4"><b>${comment.name}</b></font></a></c:if>
								<c:if test="${ loginUser.departmentId != 4 }">　　　　　　　<b><c:out value="${comment.name}" /></b></c:if>
								さんのコメント
							</c:if>

							<fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm" />
							<table class="comments">
								<tbody>
									<tr>
										<th class="arrow_box">内容</th>
										<td><pre><c:out value="${comment.text}" /></pre></td>
									</tr>

								</tbody>
							</table>
						</c:if>
					</c:forEach>

					<c:if test="${isComment == 0}">
						<div style="text-align : right">
							<p style="width : 450px;">コメントする</p>
								<form action="./" method="post">
									<textarea style="vertical-align:bottom;" name="commentText" id="commentText" cols="50" rows="10" wrap="hard" onkeyup="countDownLength( 'notification', value, 500 );">${wrongComment}</textarea>
									<input type="hidden" name="postId" value="${message.id}">
									<input type="submit" value="投稿" />
									<c:remove var="wrongComment" scope="session"/><br>
								</form>
							<p id="notification" class="cowntDown">あと500文字</p>
						</div>
					</c:if>
					</td></tr></table>
				</c:if>
			</c:forEach>
			<c:remove var="notification" scope="session"/><br>
			<c:remove var="isComment" scope="session"/><br>
		</center>


		<center><table border="0"><tr><td>
			<c:forEach items="${messages}" var="message">
				<c:if test="${message.name == null}">
					<b><c:out value="削除されたユーザー" /></b>の投稿
				</c:if>
				<c:if test="${message.name != null}">
					<c:if test="${ loginUser.departmentId == 4 }"><a href="./useredit?userId=${message.userId}" style="color:#0000ff;text-decoration:none"><font size="4"><b>${message.name}</b></font></a></c:if>
					<c:if test="${ loginUser.departmentId != 4 }"><b><c:out value="${message.name}" /></b></c:if>
					さんの投稿
				</c:if>

				<fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm" />
				<table class="messages">
					<tbody>
						<tr>
							<th class="arrow_box">件名</th>
							<td><pre><c:out value="${message.subject}" /></pre></td>
						</tr>
						<tr>
							<th class="arrow_box">本文</th>
							<td><pre><c:out value="${message.text}" /></pre></td>
						</tr>
						<tr>
							<th class="arrow_box">カテゴリー</th>
							<td><pre><c:out value="${message.category}" /></pre></td>
						</tr>

					</tbody>
				</table>

				<c:if test="${loginUser.departmentId == 3}">
					<form action="./delete" method="post" class="deleteButton">
						<input type="hidden" name="deletePostId" value="${message.id}">
						<input type="submit" style="color:white;background-color:red;" name="deletePostButton" value="投稿削除" onclick='return confirm("本当に削除してもよろしいですか．");'/><br>
					</form>
				</c:if>


				<c:if test="${loginUser.departmentId == 2 && message.branchId == loginUser.branchId}">
					<form action="./delete" method="post" class="deleteButton">
						<input type="hidden" name="deletePostId" value="${message.id}">
						<input type="submit" style="color:white;background-color:red;" name="deletePostButton" value="投稿削除" onclick='return confirm("本当に削除してもよろしいですか．");'/><br>
					</form>
				</c:if>


				<c:forEach items="${comments}" var="comment">
					<c:if test="${message.id==comment.postId}" >


						<c:if test="${comment.name == null}">
							　　　　　　　<b><c:out value="削除されたユーザー" /></b>のコメント
						</c:if>
						<c:if test="${comment.name != null}">
							　　　　　　　
							<c:if test="${ loginUser.departmentId == 4 }"><a href="./useredit?userId=${comment.userId}" style="color:#0000ff;text-decoration:none"><font size="4"><b>${comment.name}</b></font></a></c:if>
							<c:if test="${ loginUser.departmentId != 4 }"><b><c:out value="${comment.name}" /></b></c:if>
							さんのコメント
						</c:if>

						<fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm" />
						<table class="comments">
							<tbody>
								<tr>
									<th class="arrow_box">内容</th>
									<td><pre><c:out value="${comment.text}" /></pre></td>
								</tr>

							</tbody>
						</table>
						<c:if test="${loginUser.departmentId == 3}">
							<form action="./delete" method="post" class="deleteButton">
								<input type="hidden" name="deleteCommentId" value="${comment.id}">
								<input type="submit" style="color:white;background-color:red;" name="deleteCommentButton" value="コメント削除" onclick='return confirm("本当に削除してもよろしいですか．");'/><br> <br />
							</form>
						</c:if>
						<c:if test="${loginUser.departmentId == 2 && comment.branchId == loginUser.branchId}">
					<form action="./delete" method="post" class="deleteButton">
						<input type="hidden" name="deleteCommentId" value="${comment.id}">
						<input type="submit" style="color:white;background-color:red;" name="deleteCommentButton" value="コメント削除" onclick='return confirm("本当に削除してもよろしいですか．");'/><br>
					</form>
				</c:if>

					</c:if>
				</c:forEach>


				<script type="text/javascript">
					function countDownLength( idn, str, mnum ) {
					   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
					}
				</script>
				<div style="text-align : right">
					<p style="width : 470px;">コメントする</p>
					<form action="./" method="post">
						<textarea style="vertical-align:bottom;" name="commentText" id="commentText" cols="50" rows="10" wrap="hard" onkeyup="countDownLength( ${message.id}, value, 500 );">${wrongComment}</textarea>
						<input type="hidden" name="postId" value="${message.id}">
						<input type="submit" value="投稿"/>
						<c:remove var="wrongComment" scope="session"/><br>
					</form>
					<p id="${message.id}">あと500文字</p>
				</div>

				<hr width="800" size="1" align="center">
			</c:forEach>

			<c:remove var="categoryValue" scope="session"/>
		</td></tr></table></center>
	</body>

</html>