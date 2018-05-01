<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-lg navbar-dark" style="position: fixed; width: 100%; margin-bottom: 15px; text-align: center; background-color: rgba(0,0,0,.45);">
	
	<a class="companyNameSmall navbar-brand" href="index.do">Kingdom Coverage</a>
	
	
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor02" aria-controls="navbarColor02"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="companyNameSmaller collapse navbar-collapse" id="navbarColor02">
		<ul class="navbar-nav mr-auto">
		
<<<<<<< HEAD
			<c:if test="${sessionScope.agentSession != null}">
			<li class="nav-item active"><a class="nav-link" href="logoutAgent.do">Logout
					<span class="sr-only">(current)</span>
			</a></li>
			</c:if>
=======
>>>>>>> 3d14a67251b957f28d90730cb8329856c734eb41
			
			<li class="nav-item">
				<c:if test="${sessionScope.insuredSession != null}">
					<a class="nav-link" href="createPlan.do">Add Plans</a>
				</c:if>
				<c:if test="${sessionScope.agentSession == null && sessionScope.insuredSession == null}">
					<a class="nav-link" href="index.do">Plans</a>
				</c:if>
			</li>
		</ul>
			<c:if test="${sessionScope.agentSession != null}">
			<a class="nav-link nav-item active" href="logoutAgent.do" style="float:right;">Logout
					<span class="sr-only">(current)</span>
			</a>
			</c:if>
	</div>
</nav>