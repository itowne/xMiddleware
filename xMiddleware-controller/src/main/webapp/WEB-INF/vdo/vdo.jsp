<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<style>
			.vdo-d{
				width:100%;
				height:230px;
			}
			.vdo{
				width:100%;
				height:100%;
				object-fit: fill;
			}
		</style>
	</head>
	<body>
		<div class="vdo-d">
			<video controls class="vdo"  preload="none" poster="${ctx}/resources/images/play.jpeg" x5-video-player-type="h5" x5-video-player-fullscreen="true" webkit-playsinline="true" playsinline="true" src="${ctx}/upload/vdo/${fileName}"></video>
		</div>
	</body>

</html>