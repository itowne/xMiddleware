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
								视频管理 <small></small>
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
										<small></small>
									</h2>
									<ul class="nav navbar-right panel_toolbox">
										<li></li>
									</ul>
									<div class="clearfix"></div>
								</div>
								<div class="x_content">
									<p class="text-muted font-13 m-b-30">&nbsp;</p>
									<div class="col-md-12 col-sm-12 col-xs-12">
										<div class="x_panel">
											<div class="x_title">
												<h2>
													新增视频<small></small>
												</h2>
												<div class="clearfix"></div>
											</div>
											<div class="x_content">
												<br />
												<form id="demo-form2"
													class="form-horizontal form-label-left">
													<div class="form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="first-name">视频名称<span class="required">*</span></label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="vdoName" id="vdoName" autocomplete="off" class="form-control col-md-7 col-xs-12" maxlength="20"/>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12">选择视频<span class="required">*</span></label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="file" name="vdoFile" id="vdoFile" class="form-control col-md-7 col-xs-12"/>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="last-name"></span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
														</div>
													</div>
													<div class="form-group">
														<label for="middle-name"
															class="control-label col-md-3 col-sm-3 col-xs-12"></label>
														<div class="col-md-6 col-sm-6 col-xs-12">
														</div>
													</div>
													<div class="ln_solid"></div>
													<div class="form-group">
														<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
															<button type="button" class="btn btn-success"
																onclick="doSubmit()">新增</button>
															<button class="btn btn-primary" type="button"
																onclick="backIndex()">取消</button>
														</div>
													</div>

												</form>
											</div>
										</div>
									</div>
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
		$(function(){
			$("#sidebar-menu ul>li").removeClass("current-page");
			$("#sidebar-menu #vdo-li").addClass("current-page");
		});
		
		function backIndex() {
			window.location.href = "${ctx}/vdo/index";
		}
		function doSubmit() {
			if(!checkForm()){
				return false;
			}
			var fd = new FormData();
			fd.append("file", $("#vdoFile")[0].files[0]);
			fd.append("vdoName",$("#vdoName").val()); 
			$.ajax({
				type : "POST",
				url : "${ctx}/vdo/doAddVdo",
				async:false,
				cache: false,
				contentType: false,
				processData: false,
				data:fd,
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","新增视频失败","error");
						return;
					}
					var res = eval('(' + data + ')'); 
					if(res.resCode!=1){
						xAlert("提示","新增视频失败! "+res.resMsg,"error");
						return;
					}
					xAlert("提示","新增视频成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/vdo/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","新增视频失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			});
		}
		function checkForm(){
			if($("#vdoName").val()==""){
				xAlert("提示","视频标题不能为空","error");
				return false;
			}
			if($("#vdoFile").val()==""){
				xAlert("提示","请选择一个视频","error");
				return false;
			}
			return true;
		}
	</script>
</body>

</html>