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
								用户管理 <small></small>
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
                    <h2>修改用户<small></small></h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form id="bizForm" data-parsley-validate class="form-horizontal form-label-left">
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">登录名<span class="required">*</span> </span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text"  readonly="readonly" class="form-control col-md-7 col-xs-12" value="${user.loginName}">
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">姓名<span class="required">*</span> </span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text"  name="realName" class="form-control col-md-7 col-xs-12" value="${user.realName}" maxlength="20"/>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">密码<span class="required">*</span> </span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="password"  name="pwd" required="required" class="form-control col-md-7 col-xs-12" value=${user.pwd}>
                        </div>
                      </div>
                    <c:if test="${isAdmin=='1'}">
					<div class="form-group">
					<label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">角色<span class="required">*</span> </span>
					</label>
					<div class="col-md-6 col-sm-6 col-xs-12">
					<select  name="userRole" id="userRole" class="form-control">
						<c:forEach items="${roleAdmin}" var="role">
    							<option value="${role.key}">${role.value}</option>
						</c:forEach>
						</select>
					</div>
					</div>
					</c:if>
                      <div class="form-group">
                        <label for="middle-name" class="control-label col-md-3 col-sm-3 col-xs-12">手机<span class="required">*</span> </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input class="form-control col-md-7 col-xs-12" id="mobile" type="text" name="mobile" value="${user.mobile}" maxlength="11">
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                        	<input type="hidden" name="userId" value="${user.userId}"/>
                          <button type="button" class="btn btn-success" onclick="doEdit()">修改</button>
                        	  <button class="btn btn-primary" type="button" onclick="toBack()">取消</button>
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
			$("#sidebar-menu #user-li").addClass("current-page");
			initRole();
		});
		function initRole(){
			var userRole = '${user.userRole}';
			$("#userRole").val(userRole);
		}
		function doEdit(){
			if(!checkForm()){
				return false;
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/user/doEditUser",
				dataType : "json",
				async:false,
				data:$("#bizForm").serialize(),
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","修改用户失败","error");
						return;
					}
					if(data.resCode!=1){
						xAlert("提示","修改用户失败! "+data.resMsg,"error");
						return;
					}
					xAlert("提示","修改用户成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/user/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","修改用户失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			//请求完成
			});
		}
		function toBack(){
			window.location.href="${ctx}/user/index";
		}
		function checkForm(){
			if($("#loginName").val()==""){
				xAlert("提示","登录名不能空","error");
				return false;
			}
			if($("#realName").val()==""){
				xAlert("提示","姓名不能空","error");
				return false;
			}
			if($("#pwd").val()==""){
				xAlert("提示","密码不能空","error");
				return false;
			}
			var mobile=$("#mobile").val();
			if(mobile==""){
				xAlert("提示","手机不能空","error");
				return false;
			}else{
				if(isNaN(mobile)) {
						xAlert("提示", "手机号请填写数字", "error");
						return false;
					}
					if(mobile.length < 11) {
						xAlert("提示", "手机号位数不正确", "error");
						return false;
					}
			}
			return true;
		}
	</script>
</body>

</html>