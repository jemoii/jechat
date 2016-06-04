<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="http://7u2pdv.com1.z0.glb.clouddn.com/favicon.ico">
<!-- Bootstrap core CSS -->
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="http://7u2pdv.com1.z0.glb.clouddn.com/example.theme.css" rel="stylesheet">
<title>聊天首页...</title>
</head>
<body role="document">
	<div class="container theme-showcase" role="main">
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">聊天首页</h3>
					</div>
					<div class="panel-body">
						<div class="input-group col-md-8">
							<label class="input-group-addon" for="username">昵称</label> <input
								type="text" class="form-control" id="username"
								data-toggle="popover" data-trigger="hover" data-placement="top"
								data-html="true"
								data-content="
									1、填写昵称后，点击“开始聊天”进入聊天界面，</br>
									2、支持中文昵称，一对一私聊，文字颜色个性化。">
							<span class="input-group-btn">
								<button type="button" class="btn btn-info" onclick="begin()">开始聊天</button>
							</span>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col-sm-6 -->
		</div>
	</div>
	<!-- /container -->
</body>
<!-- Bootstrap core JavaScript -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(function() {
	$('[data-toggle="popover"]').popover()
})
function begin() {
	if ($('#username').val().trim() != "") {
		location.href = "./user/" + $('#username').val().trim();
	}
}
</script>
</html>