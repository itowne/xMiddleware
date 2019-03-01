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
								内容管理 <small></small>
							</h3>
						</div>

						<div class="title_right">
							<div
								class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
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
										内容列表<small></small>
									</h2>
									<ul class="nav navbar-right panel_toolbox">
										<li><button type="button" class="btn btn-info" onclick="toAdd();"><i class="glyphicon glyphicon-plus"></i> 新增内容</button></li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
								 <p class="text-muted font-13 m-b-30">&nbsp;</p>
									<table id="datatable"
										class="table table-striped table-bordered">
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
	<script type="text/javascript" src="${ctx}/resources/js/dt/dataTableTemp.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/biz/article/article.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#sidebar-menu ul>li").removeClass("current-page");
			$("#sidebar-menu #art-li").addClass("current-page");
		});
		function toAdd(){
			window.location.href="${ctx}/article/toAddArticle"
		}
		function edit(articleId){
			window.location.href="${ctx}/article/toEditArticle/"+articleId;
		}
		function view(articleId){
			window.open("${ctx}/view/current/"+articleId,'_blank');
			//window.location.href="${ctx}/article/view/"+articleId;
		}
		function applyList(articleId){
			window.location.href="${ctx}/artApply/index/"+articleId;
		}
		function del(articleId){
			xConfirm("提示","是否删除内容?",function(){doDeleteArticle(articleId)},function(){});
		}
		function isTop(articleId,isTop){
			if(isTop=="NO"){
				xConfirm("提示","是否取消置顶?",function(){doTop(articleId,isTop)},function(){});
			}else{
				xConfirm("提示","是否将该文章置顶?",function(){doTop(articleId,isTop)},function(){});
			}
		}
		function doDeleteArticle(articleId){
			$.ajax({
				type : "POST",
				url : "${ctx}/article/delArticle/"+articleId,
				dataType : "json",
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","删除内容失败","error");
						return;
					}
					if(data.resCode!=1){
						xAlert("提示","删除内容失败! "+data.resMsg,"error");
						return;
					}
					xAlert("提示","删除内容成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/article/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","删除内容失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			});
		}

		function doTop(articleId,isTop){
			$.ajax({
				type : "POST",
				url : "${ctx}/article/doTop/"+articleId+"/"+isTop,
				dataType : "json",
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","置顶内容失败","error");
						return;
					}
					if(data.resCode!=1){
						xAlert("提示","置顶内容失败! "+data.resMsg,"error");
						return;
					}
					xAlert("提示","操作成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/article/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","置顶内容失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			});
		}
	</script>
</body>
</html>