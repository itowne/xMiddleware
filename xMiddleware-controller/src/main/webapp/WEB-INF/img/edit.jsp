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
								图片管理 <small></small>
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
													修改图片<small></small>
												</h2>
												<div class="clearfix"></div>
											</div>
											<div class="x_content">
												<br />
												<form id="demo-form2"
													class="form-horizontal form-label-left">
													<div class="form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="first-name">图片名称<span class="required">*</span></label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="text" name="imgName" id="imgName" value="${img.imgName}" class="form-control col-md-7 col-xs-12" maxlength="20"/>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12"
															for="last-name">预览</span>
														</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<img id="img-html" width="100%" src="${ctx}/upload/${img.sysFileName}"/>
														</div>
													</div>
													<div class="form-group">
														<label class="control-label col-md-3 col-sm-3 col-xs-12">选择图片</label>
														<div class="col-md-6 col-sm-6 col-xs-12">
															<input type="file" name="imgFile" id="imgFile" class="form-control col-md-7 col-xs-12"/>
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
																onclick="doSubmit()">修改</button>
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
			$("#sidebar-menu #img-li").addClass("current-page");
		});
		
		function backIndex() {
			window.location.href = "${ctx}/img/index";
		}
		function doSubmit() {
			if(!checkForm()){
				return false;
			}
			var fd = new FormData();
			fd.append("file", $("#imgFile")[0].files[0]);
			fd.append("imgName",$("#imgName").val()); 
			var imgId='${img.imgId}';
			fd.append("imgId",imgId);
			$.ajax({
				type : "POST",
				url : "${ctx}/img/doEditImg",
				async:false,
				cache: false,
				contentType: false,
				processData: false,
				data:fd,
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","修改图片失败","error");
						return;
					}
					var res = eval('(' + data + ')'); 
					if(res.resCode!=1){
						xAlert("提示","修改图片失败! "+res.resMsg,"error");
						return;
					}
					xAlert("提示","修改图片成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/img/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","修改图片失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			});
		}
		function checkForm(){
			if($("#imgName").val()==""){
				xAlert("提示","图片标题不能为空","error");
				return false;
			}
			return true;
		}
	</script>
</body>

</html>