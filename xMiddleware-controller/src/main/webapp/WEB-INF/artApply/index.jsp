<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>

	<head>
		<%@ include file="/common/meta.jsp"%>
		<title>${title}</title>
	</head>

	<body class="nav-md">
		<div class="container body">
			<div class="main_container">
				<div class="col-md-3 left_col">
					<div class="left_col scroll-view">
						<%@ include file="/common/welcome-nav.jsp"%>
						<!-- /menu profile quick info -->
						<br />
						<!-- sidebar menu -->
						<%@ include file="/common/menu.jsp"%>
						<!-- /sidebar menu -->
						<!-- /menu footer buttons -->
						<%@ include file="/common/menuFooter.jsp"%>
						<!-- /menu footer buttons -->
					</div>
				</div>
				<!-- top navigation -->
				<%@ include file="/common/nav.jsp"%>
				<!-- /top navigation -->

				<!-- page content -->
				<div class="right_col" role="main">
					<div class="">
						<div class="page-title">
							<div class="title_left">
								<h3>
								报名管理 <small></small>
							</h3>
							</div>

							<div class="title_right">
								<div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
									<div class="input-group"></div>
								</div>
							</div>
						</div>

						<div class="clearfix"></div>

						<div class="row">
							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="x_panel">
									<div class="x_title">
										<h2>
										<c:choose>
   											<c:when test="${artTitle!=''}"> 
   												[${artTitle}]主题-报名列表<small></small>
   											</c:when>
   											<c:otherwise> 
   												报名列表<small></small>
   											</c:otherwise>
										</c:choose>
									</h2>
										<ul class="nav navbar-right panel_toolbox">
											<li><button type="button" class="btn btn-info" onclick="toBack();"><i class="glyphicon glyphicon-arrow-left"></i> 返回内容列表</button></li>
										</ul>
										<div class="clearfix"></div>
									</div>
									<div class="x_content">
										<p class="text-muted font-13 m-b-30">&nbsp;</p>
										<table id="datatable" class="table table-striped table-bordered">
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /page content -->
				<!-- footer content -->
				<%@ include file="/common/footer.jsp"%>
				<!-- /footer content -->
			</div>
		</div>
		<%@ include file="/common/script.jsp"%>
		<script type="text/javascript">
			var articleId='${articleId}';
			$(function() {
				$("#sidebar-menu ul>li").removeClass("current-page");
				$("#sidebar-menu #art-li").addClass("current-page");
			});

			function toBack() {
				window.location.href = "${ctx}/article/index";
			}
		</script>
		<script type="text/javascript" src="${ctx}/resources/js/dt/dataTableTemp.js"></script>
		<script type="text/javascript" src="${ctx}/resources/js/biz/artApply/artApply.js"></script>
	</body>
</html>