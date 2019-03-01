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
								投票管理 <small></small>
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
                    <h2>新增投票<small></small></h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form id="bizForm" data-parsley-validate class="form-horizontal form-label-left">
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">投票组名<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text"  name="voteTitle" id="voteTitle" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                         <button type="button" class="btn btn-info" onclick="addItem(this);"><i class="glyphicon glyphicon-plus"></i> 新增投票项目</button>
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                          <button type="button" class="btn btn-success" onclick="addVote()">新增</button>
                        	  <button class="btn btn-primary" type="button" onclick="backIndex()">取消</button>
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
			$("#sidebar-menu #vote-li").addClass("current-page");
		});
		
		function backIndex(){
			window.location.href="${ctx}/vote/index";
		}
		function addItem(obj){
			var $itm=$('<div class="form-group">'
				+'<label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name"></label>'
				+'<div class="col-md-6 col-sm-6 col-xs-12" style="border:1px dashed #c3bebe;padding:10px">'
				+'<a href="javascript:void(0)" onclick="delItem(this)" style="position: relative;left:388px;">X</a>'
				+'投票项目 <input type="text" name="subVote" class="form-control"/>'
				+'顺序 <input type="text" name="subVoteIdx" class="form-control"/>'
				+'</div>'
				+'</div>');
			$(obj).parent().parent().after($itm);
		}
		function delItem(obj){
			$(obj).parent().parent().remove();
		}
		function addVote(){
			if(!checkForm()){
				return false;
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/vote/doAddVote",
				dataType : "json",
				async:false,
				data:$("#bizForm").serialize(),
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","新增投票失败","error");
						return;
					}
					if(data.resCode!=1){
						xAlert("提示","新增投票失败! "+data.resMsg,"error");
						return;
					}
					xAlert("提示","新增投票成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/vote/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","新增投票失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			});
		}
		function checkForm(){
			if($("#voteTitle").val()==""){
				xAlert("提示","投票组名称不能为空","error");
				return false;
			}
			var b= true;
			$("input[name='subVoteIdx']").each(function(i,obj){
				if(obj.value==""){
					xAlert("提示","投票项目顺序不能为空","error");
					b=false;
					return false
				}else{
					if(isNaN(obj.value)){
					xAlert("提示","投票项目顺序请填写数字","error");
					b=false;
					return false
					}
				}
			});
			return b;
		}
	</script>
</body>

</html>