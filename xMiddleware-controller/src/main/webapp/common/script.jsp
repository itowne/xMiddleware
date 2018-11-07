<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jQuery -->
<script src="${ctx}/resources/js/jquery/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
<!-- daterangepicker -->
<script src="${ctx}/resources/js/moment/moment.min.js"></script>
<script src="${ctx}/resources/js/daterangepicker/daterangepicker.js"></script>
<!-- Custom Theme Scripts -->
<script src="${ctx}/resources/js/custom/custom.js"></script>
<!-- Datatables -->
<script src="${ctx}/resources/js/dt/jquery.dataTables.min.js"></script>
<script src="${ctx}/resources/js/pnotify/pnotify.js"></script>
<script src="${ctx}/resources/js/jquery-confirm/jquery-confirm.js"></script>
<script type="text/javascript">
	function xAlert(title, text, type) {
		new PNotify({
			title: title,
			text: text,
			type: type,
			styling: 'bootstrap3'
		});
	}

	function xConfirm(title, content, doOk, doCancle) {
		$.confirm({
			title: title,
			content: content,
			type: 'default',
			buttons: {
				ok: {
					text: "确认",
					btnClass: 'btn-primary',
					//keys: ['enter'],
					action: function() {
						doOk();
					}
				},
				cancel: {
					text: "取消",
					action: function() {
						doCancle();
					}
				}
			}
		});
	}

	function loginOut() {
		xConfirm("提示", "确认退出系统?", doLoginOut, function() {});
	}

	function doLoginOut() {
		$.ajax({
			type: "POST",
			url: "${ctx}/loginOut",
			dataType: "json",
			beforeSend: function(XMLHttpRequest) {},
			success: function(data) {
				if(data == undefined || data == "") {
					xAlert("提示", "登出失败", "error");
					return;
				}
				if(data.resCode != 1) {
					xAlert("提示", "登出失败! " + data.resMsg, "error");
					return;
				}
				setTimeout(function() {
					window.location.href = "${ctx}/login";
				}, 300)
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				xAlert("提示", "登出失败! 请稍后再试", "error");
			},
			complete: function complete(XMLHttpRequest, textStatus) {}
		});
	}
</script>