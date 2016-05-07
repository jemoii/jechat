<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap core CSS -->
<link href="./static/bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="./static/bootstrap/bootstrap-theme.min.css" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="./static/bootstrap/theme.css" rel="stylesheet">
<title>聊天首页...</title>
</head>
<body role="document">

	<div class="container theme-showcase" role="main">
		<div class="page-header">
			<h3>聊天首页</h3>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12">
								<table class="table">
									<thead>
										<tr>
											<th><h4>
													<span class="label label-default">昵称</span>
												</h4></th>
											<th><input type="text" class="form-control"
												id="username"></th>
											<th><button type="button" class="btn btn-info"
													onclick="begin()">开始聊天</button></th>
										</tr>
									</thead>

								</table>
							</div>
							<!-- /.col-sm-12 -->
						</div>
						<div class="row">
							<div class="col-sm-12">
								<ul class="list-group">
									<li class="list-group-item">1、填写昵称后，点击“开始聊天”进入聊天界面，</li>
									<li class="list-group-item">2、支持中文昵称，一对一私聊。</li>
								</ul>
							</div>
							<!-- /.col-sm-12 -->
						</div>
					</div>
				</div>
			</div>
			<!-- /.col-sm-6 -->
		</div>
		<label>Power by VOLER.ME<a class="github-button"
			href="https://github.com/jemoii/jechat" data-style="mega"
			aria-label="Watch jemoii/jechat on GitHub">Watch</a> <a
			class="github-button" href="https://github.com/jemoii/jechat/fork"
			data-style="mega" aria-label="Fork jemoii/jechat on GitHub">Fork</a></label>
	</div>
	<!-- /container -->

</body>
<!-- Bootstrap core JavaScript -->
<script src="./static/bootstrap/jquery.min.js"></script>
<script src="./static/bootstrap/bootstrap.min.js"></script>
<script src="./static/bootstrap/docs.min.js"></script>
<!-- Github buttons -->
<script async defer id="github-bjs" src="./static/github/buttons.js"></script>
<script type="text/javascript">
	$(function() {
		$('#username').focus();
	});
	function begin() {
		if ($('#username').val().trim() != "") {
			location.href = "./user/" + $('#username').val().trim();
		}
	}
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			begin();
		}
	})
</script>
</html>