<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="weui-tabbar">
	<a href="${ctx}/view/main/notice" id="notice" class="weui-tabbar__item">
		<span style="display: inline-block;position: relative;">
		<i class="icon fa-clock-o weui-tabbar__icon"></i>
		</span>
		<p class="weui-tabbar__label">活动预告</p>
	</a>
	<a href="${ctx}/view/main/current" id="current" class="weui-tabbar__item">
		<span style="display: inline-block;position: relative;">
		<i class="icon fa-comments weui-tabbar__icon"></i>
		</span>
		<p class="weui-tabbar__label">热门活动</p>
	</a>
	<a href="${ctx}/view/main/history" id="history" class="weui-tabbar__item">
		<span style="display: inline-block;position: relative;">
		<i class="icon fa-paper-plane-o weui-tabbar__icon"></i>
		</span>
		<p class="weui-tabbar__label">往期回顾</p>
	</a>
</div>