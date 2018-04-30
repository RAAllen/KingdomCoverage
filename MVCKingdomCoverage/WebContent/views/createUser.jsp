<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<jsp:include page="partials/head.jsp"></jsp:include>

<body>

	<!-- opening header div -->
	<div>
	
		<h1>create user route test</h1>
		<!-- navbar, page headers will go here -->
		
	<!-- closing header div -->
	</div>

	<!-- opening body container div -->
	<div class="container">
	
		<!-- a form to create a new user will go here -->
		
		<!-- action will need to be changed to actual route, remove this comment once that has been done -->
		<form action="created.do" method="POST">
		
			<!-- some of the values will need to be altered and matched later, remove this comment once this has been done -->
			<label for="userName">Please enter the username you would like for your account:</label>
			<input type="text" name="userName" placeholder="bobdobbs"/>
			
			<label for="password">Please enter a password:</label>
			<input type="text" name="password" placeholder="Wombat1"/>
			
			<input type="submit" value="Submit"/>
		
		</form>
	
	<!-- closing body container div -->
	</div>
	
<jsp:include page="partials/foot.jsp"></jsp:include>
</body>
</html>