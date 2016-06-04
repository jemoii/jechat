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
<style type="text/css">
html {
	font: 12px/1 Tahoma, Helvetica, Arial, "\5b8b\4f53", sans-serif;
}
</style>
<title>聊天界面...</title>
</head>
<body role="document">
	<div class="container" role="main">
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading">
						欢迎<strong>${uid}</strong>... 当前<strong><span id="size">_</span></strong>人在线
					</div>
					<div class="panel-body" id="message_panel"></div>
				</div>
			</div>
			<!-- /.col-sm-6 -->
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<input type="text" class="form-control" id="msg">
				</div>
				<div class="input-group">
					<label class="input-group-addon" for="at">@</label> <input
						type="text" class="form-control" id="at"
						placeholder="私聊时在此处填写对方昵称"><span class="input-group-btn">
						<button type="button" class="btn btn-info" onclick="sendMsg()">发送</button>
					</span>
				</div>
			</div>
			<!-- /.col-sm-4 -->
		</div>
	</div>
	<!-- /container -->
</body>
<script type='text/javascript' src='../dwr/engine.js'></script>
<script type='text/javascript' src='../dwr/interface/dwrChat.js'></script>
<!-- Bootstrap core JavaScript -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="http://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript">
var uid = '${uid}';
var colorId = Math.ceil(Math.random()*4);
$(function() {
	dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setNotifyServerOnPageUnload(true);
	dwrChat.onPageLoad(uid);
});
// 将无效的userId及时移除
window.onbeforeunload = function() {
	window.unloadTimer = setInterval("dwrChat.onPageLoad(uid);clearInterval(window.unloadTimer);", 500);
	dwrChat.onPageClose(uid);
	return "";
};

function showMsg(message) {
	// console.log(message);
	var obj = eval("(" + message + ")");
	$('#message_panel').append(obj.currentId + '：' + obj.content + '</br>');
	$('#size').html(obj.currentSize); // 显示在线人数
}

function sendMsg() {
	if ($('#msg').val().trim() != "") {
		var idto = {
			userId: uid,
			content: $('#msg').val().trim(),
			at: $('#at').val().trim(),
			colorId: colorId
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
</html>