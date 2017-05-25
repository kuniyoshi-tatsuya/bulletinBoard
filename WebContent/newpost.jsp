<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>新規投稿画面</title>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
	</head>

	<body class="newPost">
		<center>
			<font size="7">わったい</font>
			<a href="./" style="color:black;text-decoration:none"><font size="15" color="green"><b>菜</b></font></a>
			<font size="7">掲示板</font><br>

			<h3>新規投稿画面</h3>
		</center>


		<center><table border="0" width="660">
			<tr>
				<td align="left" valign="middle">
					<div class="path">
						<a href="./">ホーム</a>　>　
						新規投稿画面
						　<a href="./">戻る</a>
					</div>
				</td>
			</tr>
		</table></center>
		<hr width="600" size="1" align="center"><br>

		<center>
			<c:if test="${ not empty errorMessages }">
				<c:forEach items="${errorMessages}" var="message">
					<font color="#ff0000"><c:out value="${message}" /></font><br>
				</c:forEach>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>
		</center>

		<script type="text/javascript">
			function countDownLengthSubject( idn, str, mnum ) {
			   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
			}

			function countDownLengthText( idn, str, mnum ) {
			   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
			}

			function countDownLengthCategory( idn, str, mnum ) {
			   document.getElementById(idn).innerHTML = "あと" + (mnum - str.length) + "文字";
			}
		</script>

		<form action="newpost" method="post">
			<table class="newPost" border="0">
				<tbody>
					<tr>
						<th class="arrow_box">件名</th>
						<td>
							<textarea name="subject" id="subject" cols="50" rows="2" wrap="hard" onkeyup="countDownLengthSubject( 'subjectLength', value, 50 );">${wrongSubject}</textarea>
							<p id="subjectLength" class="cowntDown"  style="width: 373px;">あと50文字</p>
						</td>
					</tr>
					<tr>
						<th class="arrow_box">本文</th>
						<td>
							<textarea name="text" id="text" cols="50" rows="16" wrap="hard" onkeyup="countDownLengthText( 'textLength', value, 1000 );">${wrongText}</textarea>
							<p id="textLength" class="cowntDown"  style="width: 373px;">あと1000文字</p>
						</td>
					</tr>
					<tr>
						<th class="arrow_box">カテゴリーを選択</th>
						<td>
							<select name="categories">
								<c:if test="${empty categoryValue}">
									<option value="" class="mainTag">カテゴリー</option>
								</c:if>
								<c:if test="${!empty categoryValue}">
									<option value="${categoryValue}">${categoryValue}</option>
									<option value="" class="mainTag">カテゴリー</option>
								</c:if>

								<c:forEach items="${categories}" var="exsistCategory">
									<c:if test="${categoryValue != exsistCategory.category}">
										<option value="${exsistCategory.category}">${exsistCategory.category}</option>
									</c:if>
								</c:forEach>
								<c:remove var="categoryValue" scope="session"/>
							</select>
						</td>
					</tr>
					<tr>
						<th class="arrow_box">カテゴリーを追加</th>
						<td>
							<label for="category">カテゴリーを追加</label>
							<input name="category" id="category" value="${wrongCategory}" onkeyup="countDownLengthCategory( 'categoryLength', value, 10 );"/>
							<p id="categoryLength" class="cowntDown"  style="width: 273px;">あと10文字</p>
						</td>
						<td><input type="submit" value="投稿" style="text-align: left;" class="newPostButton"/></td>
					</tr>
				</tbody>
			</table>

			<c:remove var="wrongSubject" scope="session"/>
			<c:remove var="wrongText" scope="session"/>
			<c:remove var="wrongCategory" scope="session"/>
		</form>

	</body>
</html>