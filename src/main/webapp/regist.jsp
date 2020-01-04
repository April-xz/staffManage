<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>员工管理系统</title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<!-- Font Awesome -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
		<!-- Ionicons -->
		<!-- <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css"> -->
		<!-- icheck bootstrap -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
		<!-- Theme style -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
		<!-- Google Font: Source Sans Pro -->
		<!-- <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet"> -->
	</head>
	<body class="hold-transition login-page">
		<div class="login-box">
			<div class="login-logo">
				员工管理系统
			</div>
			<!-- /.login-logo -->
			<div class="card">
				<div class="card-body login-card-body">
					<p class="login-box-msg">员工注册</p>

					<form action="${pageContext.request.contextPath}/regist" method="post">
						<div class="input-group mb-3">
							<input type="text" name="id" class="form-control" placeholder="编号">
							<div class="input-group-append">
								<div class="input-group-text">

								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<input type="text" name="name" class="form-control" placeholder="姓名">
							<div class="input-group-append">
								<div class="input-group-text">

								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<input type="text" name="age" class="form-control" placeholder="年龄">
							<div class="input-group-append">
								<div class="input-group-text">
						
								</div>
							</div>
						</div>
						<div class="input-group mb-3">
							<input type="text" name="tel" class="form-control" placeholder="联系方式">
							<div class="input-group-append">
								<div class="input-group-text">
						
								</div>
							</div>
						</div>
						<div class="form-group">
							<label>选择部门</label>
							<select class="form-control" name="department">
								<c:forEach items="${list}" var="li">
									<option value="${li.departmentName}">${li.departmentName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<div class="form-check"> 
								<input checked="checked" class="form-check-input" type="radio" name="sex" value="男">
								<label class="form-check-label">男</label>
							</div>
							<div class="form-check">
								<input class="form-check-input" type="radio" name="sex" value="女">
								<label class="form-check-label">女</label>
							</div>

						</div>

						<div class="row">
							
							<!-- /.col -->
							<div class="col-12">
								<button type="submit" class="btn btn-primary btn-block">注册</button>
								<c:if test="${flag==true}">
									<a href="${pageContext.request.contextPath}/login.jsp"><button type="button" class="btn btn-primary btn-block">登录</button></a>
								</c:if>
							</div>

							<!-- /.col -->
						</div>
						
						<div class="row">
							
							<!-- /.col -->
							<div class="col-12" align="center" style="color: #DC3545;">
								<h3>${msg }</h3>
							</div>
							<!-- /.col -->
						</div>
					</form>

				
				<!-- /.login-card-body -->
			</div>
		</div>
		<!-- /.login-box -->

		<!-- jQuery -->
		<script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>
		<!-- Bootstrap 4 -->
		<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
		<!-- AdminLTE App -->
		<script src="${pageContext.request.contextPath}/dist/js/adminlte.min.js"></script>

	</body>
</html>
