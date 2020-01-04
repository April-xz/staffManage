<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>员工管理系统-修改密码</title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- Font Awesome -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
		<!-- Ionicons -->
		<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
		<!-- Tempusdominus Bbootstrap 4 -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
		<!-- iCheck -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
		<!-- JQVMap -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/jqvmap/jqvmap.min.css">
		<!-- Theme style -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
		<!-- overlayScrollbars -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
		<!-- Daterange picker -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.css">
		<!-- summernote -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.css">
		<!-- Google Font: Source Sans Pro -->
		<link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
	</head>
	<body class="hold-transition sidebar-mini layout-fixed">
		<div class="wrapper">

			<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-white navbar-light">




    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <!-- Messages Dropdown Menu -->
      
      <!-- Notifications Dropdown Menu -->
      
      <li class="nav-item">
        <a class="nav-link" data-widget="control-sidebar" data-slide="true" href="#">

        </a>
      </li>
    </ul>
  </nav>
			<!-- /.navbar -->

			<!-- Main Sidebar Container -->
			<aside class="main-sidebar sidebar-dark-primary elevation-4">
				<!-- Brand Logo -->
				<a href="index3.html" class="brand-link">
					<img src="${pageContext.request.contextPath}/dist/img/AdminLTELogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
					<span class="brand-text font-weight-light">员工管理系统</span>
				</a>

				<!-- Sidebar -->
				<div class="sidebar">
					<!-- Sidebar user panel (optional) -->
					<!--  <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="image">
          <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
        </div>
        <div class="info">
          <a href="#" class="d-block">Alexander Pierce</a>
        </div>
      </div> -->

					<!-- Sidebar Menu -->
					<nav class="mt-2">
						<ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
							<!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
							<li class="nav-item has-treeview menu-open">
								<a href="#" class="nav-link">
									<i class="nav-icon fas fa-tachometer-alt"></i>
									<p>
										绩效考核管理
										<i class="right fas fa-angle-left"></i>
									</p>
								</a>
								<ul class="nav nav-treeview">
									<li class="nav-item">
										<a href="${pageContext.request.contextPath}/clockIn.jsp" class="nav-link">
											<i class="far fa-circle nav-icon"></i>
											<p>员工打卡</p>
										</a>
									</li>
									<li class="nav-item">
										<a href="${pageContext.request.contextPath}/getLeaveState" class="nav-link">
											<i class="far fa-circle nav-icon"></i>
											<p>请假申请</p>
										</a>
									</li>
								</ul>
							</li>
							<li class="nav-item">
								<a href="${pageContext.request.contextPath}/editPassowrd.jsp" class="nav-link">
									<i class="nav-icon fas fa-th"></i>
									<p>
										账号管理
									</p>
								</a>
							</li>
							<li class="nav-item">
								<a href="${pageContext.request.contextPath}/getAllLeaveState" class="nav-link">
									<i class="nav-icon fas fa-th"></i>
									<p>
										代办事项
									</p>
								</a>
							</li>
						</ul>
					</nav>
					<!-- /.sidebar-menu -->
				</div>
				<!-- /.sidebar -->
			</aside>

			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->

				<!-- /.content-header -->

				<!-- Main content -->
				<section class="content">
					
					<!-- /.container-fluid -->
					<div class="container-fluid">
						<div class="row">
							<!-- left column -->
							<div class="col-md-12">
								<!-- general form elements -->
								<div class="card card-primary">
									<div class="card-header">
										<h3 class="card-title">修改密码</h3>
									</div>
									<!-- /.card-header -->
									<!-- form start -->
									<form role="form" action="${pageContext.request.contextPath}/updatePasswore" method="post">
										<div class="card-body">
											<div class="form-group">
												<label for="exampleInputPassword1">密码</label>
												<input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="请输入新密码">
											</div>
										</div>
										<!-- /.card-body -->

										<div class="card-footer" >
											<button type="submit" class="btn btn-primary" onclick="success();">确定修改</button>
										</div>
									</form>
								</div>
								<!-- /.card -->

							</div>
						</div>
						</form>
					</div>
					<!-- /.card-body -->
			</div>
			<!-- /.card -->
		</div>
		<!--/.col (right) -->
		</div>
		<!-- /.row -->
		</div>
		</section>
		<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->


		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Control sidebar content goes here -->
		</aside>
		<!-- /.control-sidebar -->
		</div>
		<!-- ./wrapper -->

		<!-- jQuery -->
		<script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>
		<!-- jQuery UI 1.11.4 -->
		<script src="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.min.js"></script>
		<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
		<script>
			$.widget.bridge('uibutton', $.ui.button)
		</script>
		<!-- Bootstrap 4 -->
		<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
		<!-- ChartJS -->
		<script src="${pageContext.request.contextPath}/plugins/chart.js/Chart.min.js"></script>
		<!-- Sparkline -->
		<script src="${pageContext.request.contextPath}/plugins/sparklines/sparkline.js"></script>
		<!-- JQVMap -->
		<script src="${pageContext.request.contextPath}/plugins/jqvmap/jquery.vmap.min.js"></script>
		<script src="${pageContext.request.contextPath}/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
		<!-- jQuery Knob Chart -->
		<script src="${pageContext.request.contextPath}/plugins/jquery-knob/jquery.knob.min.js"></script>
		<!-- daterangepicker -->
		<script src="${pageContext.request.contextPath}/plugins/moment/moment.min.js"></script>
		<script src="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.js"></script>
		<!-- Tempusdominus Bootstrap 4 -->
		<script src="${pageContext.request.contextPath}/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
		<!-- Summernote -->
		<script src="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.js"></script>
		<!-- overlayScrollbars -->
		<script src="${pageContext.request.contextPath}/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
		<!-- AdminLTE App -->
		<script src="${pageContext.request.contextPath}/dist/js/adminlte.js"></script>
		<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
		<script src="${pageContext.request.contextPath}/dist/js/pages/dashboard.js"></script>
		<!-- AdminLTE for demo purposes -->
		<script src="${pageContext.request.contextPath}/dist/js/demo.js"></script>
		<script type="text/javascript">

            function success() {
                alert("密码修改成功");
            }
		</script>
	</body>
</html>
