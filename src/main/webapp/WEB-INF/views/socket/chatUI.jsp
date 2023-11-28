<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Title</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
	<div class="mt-4 p-5 bg-primary text-white rounded">
		<h1>Chat</h1>
		<div class="mb-3 mt-3">
			<label for="nickName" class="form-label">닉네임 : </label>
			<input type="text" class="form-control" id="nickName" placeholder="Enter nickName" name="nickName">
		</div>
		<!-- 접속하기-->
		<button type="button" class="btn btn-success">접속하기</button>
		<button type="button" class="btn btn-secondary">연결끊기</button>
	</div>
</div>
</body>
</html>
