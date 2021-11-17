<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TODO LIST</title>
<link rel="stylesheet" href="<c:url value='/css/mainContainer.css' />"
	type="text/css">
<link rel="stylesheet" href="<c:url value='/css/todo/todo.css' />"
	type="text/css">
<link rel="stylesheet" href="<c:url value='/css/contents.css' />"
	type="text/css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.1/css/all.css">

<script>
function searchDate() {
	if(form.todo_date.value == "") {
		alert("날짜를 입력하세요");
		form.todo_date.focus();
		return false;
	}
	form.submit();
}

</script>
</head>
<body>
	<div class="page-wrapper">
		<nav class="nav-bar">
			<div class="nav-logo">
				<a href="#" id="text-deco">&lt;HBTI/&gt;</a>
			</div>
			<div class="nav-menu">
				<ul class="menu-ul">
					<li class="menu-li"><a href="<c:url value='/todo/view'/>" id="text-deco">ToDo</a></li>
					<li class="menu-li"><a href="<c:url value='/group/main' />"
						id="text-deco">Group</a></li>
					<li class="menu-li"><a href="<c:url value='/user/myPage' />" id="text-deco">MyPage</a></li>
				</ul>
			</div>
			<div class="nav-logout">
				<a href="<c:url value='/user/logout'/>" id="text-deco">Logout</a>
			</div>
		</nav>
	</div>


	<div class="contents">
		<p id="sub-title">TODO 날짜 검색</p>
		<div class="contents-split">
					<c:if test='${empty todoList}'>
						<p id="intro"> 해당 날짜에 TODO LIST가 없습니다.!</p>
					</c:if>
					<c:forEach var="todo" items="${todoList}" varStatus="status">
					<c:if test="${status.first}">
						<p id="contents-title">
				 		<fmt:formatDate value="${todo.todo_date}" pattern="yy년 MM월 dd일" /> : TODO LIST
					</p>
					</c:if>
						<div class="list-box">
							<!-- is_done 여부에 따라 다르게 보여줌 -->
							<div class="list">
								<c:if test="${todo.is_done eq 1}">
									<a id="check"
										href="<c:url value="/todo/doCheck">
										<c:param name="todo_id" value='${todo.todo_id }'/>
										</c:url>"><i
										class="fas fa-check-square"></i></a>
								</c:if>
								<c:if test="${todo.is_done eq 0}">
									<a id="check"
										href="<c:url value="/todo/doNotCheck">
										<c:param name="todo_id" value='${todo.todo_id }'/>
										</c:url>"><i
										class="far fa-square"></i></a>
								</c:if>
							</div>
							<div class="list">${todo.content}</div>
							<div class="list">
								<a id="a-deco_icon"
									href="<c:url value='/todo/modifyForm'>
								<c:param name="todo_id" value='${todo.todo_id}'/>
								</c:url>">
									<i class="fas fa-pencil-alt"></i>
								</a>
							</div>
						</div>
						<div class="split"></div>
					</c:forEach>
				<p>

		</div>
	</div>

	<footer>
		<ul>
			<li><p>copyright ⓒ 2021 All rights reserved by 코딩궁딩.</p></li>
			<li><a class="text-deco" href="mailto:leuns36@naver.com"
				target="_top"> ✉Mail me : leuns36@naver.com </a></li>
		</ul>
	</footer>
</body>
</html>