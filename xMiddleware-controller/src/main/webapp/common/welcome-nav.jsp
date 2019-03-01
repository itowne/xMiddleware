<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
	pageEncoding="UTF-8"%>
<div class="navbar nav_title" style="border: 0;">
	<a href="javaScript:void(0)" class="site_title"><i class="fa fa-cloud"></i> <span>后台管理平台</span></a>
</div>
<div class="clearfix"></div>
<!-- menu profile quick info -->
<div class="profile clearfix">
	<div class="profile_pic">
		<img src="${ctx}/resources/images/user.png" alt="..." class="img-circle profile_img">
	</div>
	<div class="profile_info">
		<span>你好,${userRoleLabel}</span>
		<h2>${userLoginName}</h2>
	</div>
</div>