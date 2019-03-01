<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
		<title>view</title>
		<link rel="stylesheet" href="${ctx}/resources/weui/css/weui.css" />
		<link rel="stylesheet" href="${ctx}/resources/weui/css/example.css" />
		<style>
			.page-show {
				opacity: 1;
			}
		</style>
	</head>

	<body ontouchstart>
		<div class="container" id="container"></div>
		<div class="page page-show">
			<div class="weui-msg">
				<div class="weui-msg__icon-area"><i class="weui-icon-warn weui-icon_msg-primary"></i></div>
				<div class="weui-msg__text-area">
					<h2 class="weui-msg__title">温馨提示</h2>
					<p class="weui-msg__desc">抱歉！服务器似乎开小差了 ... 请稍后再试.
					</p>
				</div>
				<div class="weui-msg__opr-area">
					<p class="weui-btn-area">
					</p>
				</div>
				<div class="weui-msg__extra-area">
					<div class="weui-footer">
						<p class="weui-footer__links">
							&nbsp;
						</p>
						<p class="weui-footer__text">Copyright &copy; 2018</p>
					</div>
				</div>
			</div>
		</div>
		<script src="${ctx}/resources/weui/js/zepto.min.js"></script>
	</body>

</html>