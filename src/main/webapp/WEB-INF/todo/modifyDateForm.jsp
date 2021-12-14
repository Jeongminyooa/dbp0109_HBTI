<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MODIFY TODO</title>
<link rel="stylesheet" href="<c:url value='/css/mainContainer.css' />"
	type="text/css">
<link rel="stylesheet" href="<c:url value='/css/todo/todo.css' />"
	type="text/css">
<link rel="stylesheet" href="<c:url value='/css/contents.css' />"
	type="text/css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.1/css/all.css">

<script>
function modifyDateTodo() {
    if (form.content.value == "") {
       alert("빈칸을 입력하세요");
       form.content.focus();
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
				<a href="<c:url value='/main'/>" id="text-deco">&lt;HBTI/&gt;</a>
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
		<p id="sub-title">MODIFY TODO</p>
		<!-- TODO LIST 설명 출력 -->
		<div class="contents-split">
			<p id="intro">
				TODO를 추가는 아래 버튼을 이용하세요.<br> 내용을 바꾸어 수정할 수 있습니다.<br> 삭제를
				원한다면 휴지통을 클릭하세요.
			</p>
		</div>
		<div class="contents-split">
			<p id="contents-title"> MODIFY TODO LIST</p>
			   <form name="form" action="<c:url value='/todo/date/modify'/> ">
				<p>
					<div class="modfiy-box">
					<!-- 수정할 todo가 상단에 보임 -->
					<span><input type="hidden" name="select_id"
						value='${selectTodo.todo_id}'></span> 
					<span><input type="text" name="content"
						value='${selectTodo.content}'></span>
					<span><a onClick='modifyDateTodo()' id="a-deco">&nbsp;수정&nbsp;</a></span>
					<span>&nbsp;<a id="a-deco_icon" href="<c:url value="/todo/date/delete">
									<c:param name="todo_id" value='${selectTodo.todo_id}'/>
									<c:param name="select_id" value='${select_id}'/>
									<c:param name="todo_date" value='${selectTodo.todo_date }'/>
									</c:url>"><i class="far fa-trash-alt"></i></a></span></div>
			</form>						
			<div>
			<c:forEach var="todo" items="${todoList}">
				<div class="list-box">
					<!-- is_done 여부에 따라 다르게 보여줌 -->
					<div class="list">${todo.content}</div> 
					<div class="list">
						<a id="a-deco_icon" 
								href="<c:url value="/todo/date/delete">
									<c:param name="todo_id" value='${todo.todo_id }'/>
									<c:param name="select_id" value='${select_id}'/>
									<c:param name="todo_date" value='${todo.todo_date }'/>
									</c:url>"><i class="far fa-trash-alt"></i></a>
					</div>
				</div>
				<div class="split"></div>
			</c:forEach>
			</div>
			
			<p>
			 <a id="a-deco" 
					href="<c:url value="/todo/dateOk">
					<c:param name="date" value='${date}'/>	
					</c:url>">완료</a>
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