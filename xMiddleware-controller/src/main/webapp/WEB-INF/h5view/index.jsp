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
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
<title>${h5Title}</title>
<link rel="stylesheet" href="${ctx}/resources/weui/css/weui.css" />
<link rel="stylesheet" href="${ctx}/resources/weui/css/custom.css" />
<link rel="stylesheet" href="${ctx}/resources/weui/css/icon.css" />
<style type="text/css">
.ft-cl {
	color: #000000;
}
.weui-cells_blank{
	margin-top: 8em;
    margin-bottom: 6em;
    padding-left: 15px;
    padding-right: 15px;
    color: #999999;
}
.opc{
	filter:alpha(opacity=60);  
     -moz-opacity:0.6;  
     -khtml-opacity: 0.6;  
     opacity: 0.6;  
}
</style>
</head>
<body ontouchstart>
	<div class="container" id="container"></div>
	<div class="page tabbar js_show">
		<div class="weui-tab">
			<div class="weui-tab__panel">
				<div class="weui-panel__hd ft-cl">阅读导航</div>
				<div class="weui-panel__bd">
				<div class="weui-cells_blank"></div>
					<a href="javascript:void(0);" class="weui-btn weui-btn_default opc" onclick="toNotice();">进入活动预告
						>>></a><a href="javascript:void(0);"
						class="weui-btn weui-btn_default opc" onclick="toCurrent();">进入热门活动 >>></a> <a
						href="javascript:void(0);" class="weui-btn weui-btn_default opc" onclick="toHistory();">进入往期回顾
						>>></a>
				</div>
				<div class="weui-cell weui-cell_access weui-cell_link"></div>
			</div>
		</div>
	</div>
	</div>
	<script src="${ctx}/resources/weui/js/zepto.min.js"></script>
	<script type="text/javascript">
		function toNotice() {
			window.location.href = "${ctx}/view/main/notice";
		}
		function toCurrent() {
			window.location.href = "${ctx}/view/main/current";
		}
		function toHistory() {
			window.location.href = "${ctx}/view/main/history";
		}
	</script>
</body>
</html>