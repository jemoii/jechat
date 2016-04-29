<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap core CSS -->
<link href="../static/bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="../static/bootstrap/bootstrap-theme.min.css"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="../static/bootstrap/theme.css" rel="stylesheet">
<title>聊天界面...</title>
</head>
<body role="document">
	<div class="container theme-showcase" role="main">
		<div class="page-header">
			<div class="alert alert-success" role="alert">
				欢迎<strong>${uid}</strong>... 当前<strong><span id="size">_</span></strong>人在线
			</div>
		</div>
		<div class="row">
			<div class="col-sm-10">
				<div class="panel panel-info">
					<div class="panel-body" id="message-panel"></div>
				</div>
			</div>
			<!-- /.col-sm-8 -->
		</div>
		<div class="row">
			<div class="col-md-8">
				<table class="table">
					<tbody>
						<tr>
							<th colspan="2"><input type="text" class="form-control"
								id="msg"></th>
						</tr>
						<tr>
							<th><h4>
									<span class="label label-default">@</span>
								</h4></th>
							<th><input type="text" class="form-control" id="at"
								placeholder="私聊时在此处填写对方昵称"></th>
							<th><button type="button" class="btn btn-info"
									onclick="sendMsg()">发送</button></th>
						</tr>
					</tbody>

				</table>
			</div>
			<!-- /.col-sm-8 -->
		</div>
	</div>
	<!-- /container -->

</body>
<!-- Bootstrap core JavaScript -->
<script type='text/javascript' src='../dwr/engine.js'></script>
<script type='text/javascript' src='../dwr/interface/dwrChat.js'></script>
<script src="../static/bootstrap/jquery.min.js"></script>
<script src="../static/bootstrap/bootstrap.min.js"></script>
<script src="../static/bootstrap/docs.min.js"></script>
<script type="text/javascript">
	var uid = '${uid}';
	$(function() {
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		dwrChat.onPageLoad(uid);
		$('#msg').focus();
	});
	// 将无效的userId及时移除
	window.onbeforeunload = function() {
		dwrChat.onPageClose(uid);
		return "";
	};

	function showMsg(message) {
		// console.log(message);
		var obj = eval("(" + message + ")");
		$('#message-panel').append(
				'<label>' + obj.currentId + '</label>：<span>' + obj.content
						+ '</span></br>');
		$('#size').html(obj.currentSize);// 显示在线人数
	}

	function sendMsg() {
		if ($('#msg').val().trim() != "") {
			var idto = {
				userId : uid,
				content : $('#msg').val().trim(),
				at : $('#at').val().trim()
			// 支持私聊
			};
			dwrChat.onSendMessage(idto);
			$('#msg').val("").focus();
			$('#at').val("");

		}
	}

	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			sendMsg();
		}
	})
</script>
<style type="text/css">
label {
	display: inline-block;
	width: 18%;
	color: #09F;
}
</style>
</html>