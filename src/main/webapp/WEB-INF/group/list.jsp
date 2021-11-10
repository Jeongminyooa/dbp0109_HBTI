<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>HBTI 그룹 리스트</title>

<!--  MainContainer 스타일시트 -->
<link rel="stylesheet" href="<c:url value='/css/mainContainer.css' />"
	type="text/css">

<link rel="stylesheet" href="<c:url value='/css/groupList.css' />"
	type="text/css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.1/css/all.css">


</head>
<body>
	<div class="page-wrapper">
		<nav class="nav-bar">
			<div class="nav-logo">
				<a href="#" id="text-deco">HBTI</a>
			</div>
			<div class="nav-menu">
				<ul class="menu-ul">
					<li class="menu-li"><a href="#" id="text-deco">ToDo</a></li>
					<li class="menu-li"><a href="#" id="text-deco">Group</a></li>
					<li class="menu-li"><a href="#" id="text-deco">MyPage</a></li>
				</ul>
			</div>
			<div class="nav-logout">
				<a href="#" id="text-deco">Logout</a>
			</div>
		</nav>
	</div>

	<!-- contents -->
	<div class="contents">
		<p id="sub-title">Hello! HBTI World</p>
		<div class="contents-top">
			<p id="intro">
				HBTI에 오신 여러분들 모두 환영합니다.<br> 이곳에서는 나와 같은 HBTI를 가진 사람들과 함께 건강 습관을
				실천할 수 있습니다.<br> 운동 키워드를 통해 검색하여 보다 마음에 맞는 사람들을 찾아보세요!
			</p>

			<div class="search-group">
				<form>
					<input type="text" class="search-txt" name=""
						placeholder="그룹 이름 입력"> <a class="search-btn" href="#">
						<i class="fas fa-search"></i>
					</a>
				</form>
			</div>
		</div>
		<div class="contents-main">
			<p id="contents-title">GROUP LIST</p>
			원하는 그룹에 가입하거나 자신이 그룹을 생성할 수 있습니다.

			<c:forEach var="k" begin="1" end="2">
				<div class="list-row">
					<c:forEach var="i" begin="1" end="3">
						<div class="list-box">
							<table>
								<tr>
									<td colspan='2'><i class="fas fa-chart-pie fa-3x"></i></td>
								</tr>
								<tr>
									<td>그루비룸</td>
									<td style="color: grey;">3/30</td>
								</tr>
								<tr>
									<td colspan='2'>운동만이 살 길이다!</td>
								</tr>
								<tr>
									<td colspan='2'><a href= "<c:url value='/group/join' />" id="a-deco">JOIN</a> </td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</c:forEach>
			
			<div class="page">
			<a id="pageMove" href="<c:url value='/group/list'>
					<c:param name='page_left' value='1' />
					</c:url>"><i class="fas fa-chevron-circle-left"></i></a>
					1 <!-- ${curPage} --> 
					<a id="pageMove" href="<c:url value='/group/list'>
					<c:param name='page_right' value='1' />
					</c:url>"><i class="fas fa-chevron-circle-right"></i></a>
			</div>
			<div class="create">
			<a href="<c:url value='/group/create' />" id="a-deco"><i class="fas fa-plus-square">&nbsp;Create Group&nbsp;</i></a>
			</div>
			
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