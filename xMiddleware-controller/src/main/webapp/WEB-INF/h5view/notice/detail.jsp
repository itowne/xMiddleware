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
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
		<title>${h5Title}</title>
		<link rel="stylesheet" href="${ctx}/resources/weui/css/weui.css" />
		<link rel="stylesheet" href="${ctx}/resources/weui/css/custom.css" />
		<link rel="stylesheet" href="${ctx}/resources/weui/css/icon.css" />
		<style>
			.weui-article p {
				margin: 0px;
			}
			
			.clean-brc {
				background-color: #FFFFFF;
			}
			
			.page-desc-date {
				margin-left: 2em;
			}
			
			.page-desc-aut {
				color: #003580;
			}
			.n-sp{
				color: red;
				line-height: 0.1em;
			}
			.no-author{
			 margin-left: 0em;
			}
		</style>
	</head>

	<body ontouchstart>
		<div class="container" id="container"></div>
		<div class="page tabbar js_show">
			<div class="weui-tab">
				<div class="weui-tab__panel">
					<div class="page article js_show">
						<div class="page__hd">
							<h1 class="page__title">${article.title}</h1>
							<p class="page__desc"><span class="page-desc-aut">${article.author}</span><span class="page-desc-date">${article.createTimeLabel}</span></p>
						</div>
						<form id="bizForm">
							<div class="page__bd">
								<article class="weui-article">
									<section>
										<section id="art-cont">
										</section>
										<section class="sec-cl-n">
											<div class="weui-cells__title">我要报名</div>
											<div class="weui-cells weui-cells_form">
												<div class="weui-cell">
													<div class="weui-cell__hd"><label class="weui-label"><span class="n-sp">*</span>姓名</label></div>
													<div class="weui-cell__bd">
														<input class="weui-input" type="text" name="applyName" id="applyName" maxlength="15" placeholder="请输入姓名" />
													</div>
												</div>
												<div class="weui-cell">
													<div class="weui-cell__hd">
														<label class="weui-label"><span class="n-sp">*</span>手机号</label>
													</div>
													<div class="weui-cell__bd">
														<input class="weui-input" maxlength="11" type="text" name="mobile" id="mobile" placeholder="请输入手机号" />
													</div>
												</div>
											</div>
										</section>
										<section class="sec-vbtn-n">
											<div class="page__bd page__bd_spacing">
												<input type="hidden" name="articleId" value="${article.articleId}" />
												<a href="javascript:void(0);" class="weui-btn weui-btn_primary" onclick="doApply()">确认报名</a>
											</div>
										</section>
									</section>
								</article>
							</div>
						</form>
						<div class="page__ft">
							<div class="weui-footer">
								<p class="weui-footer__text">Copyright &copy; 2018</p>
							</div>
						</div>
					</div>
				</div>
				<%@ include file="/common/h5view/tabBar.jsp"%>
			</div>
		</div>
		<div id="toast" style="display: none;">
			<div class="weui-mask_transparent"></div>
			<div class="weui-toast">
				<i class="weui-icon-success-no-circle weui-icon_toast"></i>
				<p class="weui-toast__content">报名成功!</p>
			</div>
		</div>
		<div id="loadingToast" style="display:none;">
			<div class="weui-mask_transparent"></div>
			<div class="weui-toast">
				<i class="weui-loading weui-icon_toast"></i>
				<p class="weui-toast__content">报名处理中...</p>
			</div>
		</div>
		<div class="js_dialog" id="iosDialog2" style="display: none;">
			<div class="weui-mask"></div>
			<div class="weui-dialog">
				<div id="idgText" class="weui-dialog__bd"></div>
				<div class="weui-dialog__ft">
					<a href="javascript:void(0);" class="weui-dialog__btn weui-dialog__btn_primary" onclick="konwClose('iosDialog2')">确定</a>
				</div>
			</div>
		</div>
		<script src="${ctx}/resources/weui/js/zepto.min.js"></script>
		<script src="${ctx}/resources/weui/js/jweixin-1.0.0.js"></script>
		<script src="${ctx}/resources/weui/js/imgZoom.js"></script>
		<script src="${ctx}/resources/js/underscore/underscore-min.js"></script>
		<script type="text/javascript">
			$(function() {
				var art_cont = '${article.content}';
				$("#art-cont").html(_.unescape(art_cont));
				var author ='${article.author}';
				if(author==""||author==null){
					$(".page-desc-date").addClass("no-author");
				}
				imgZoom();
			});
			
			function checkForm(){
				var applyName=$("#applyName").val();
				var mobile=$("#mobile").val();
				if(applyName==""){
					$("#idgText").html("姓名不能为空");
					$('#iosDialog2').show();
					return false;
				}
				if(mobile==""){
					$("#idgText").html("手机不能为空");
					$('#iosDialog2').show();
					return false;
				}else{
					if(isNaN(mobile)){
						$("#idgText").html("手机号请填写数字");
						$('#iosDialog2').show();
						return false;
					}
					if(mobile.length<11){
						$("#idgText").html("手机号位数不正确");
						$('#iosDialog2').show();
						return false;
					}
				}
				return true;
			}

			function doApply() {
				if(!checkForm()){
					return false;
				}
				$.ajax({
					type: "POST",
					url: "${ctx}/artApply/doAdd",
					dataType: "json",
					async: false,
					data: $("#bizForm").serialize(),
					beforeSend: function(XMLHttpRequest) {
						$('#loadingToast').show();
					},
					success: function(data) {
						if(data == undefined || data == "") {
							$('#iosDialog2').show();
							return;
						}
						if(data.resCode != 1) {
							$("#idgText").html(data.resMsg);
							$('#iosDialog2').show();
							return;
						}
						$('#toast').show();
						$(".sec-cl-n").hide();
						$(".page__bd.page__bd_spacing").hide();
						setTimeout(function() {
							$('#toast').hide();
						}, 2000);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						$("#idgText").html("很抱歉！报名失败请稍后再试.");
						$('#iosDialog2').show();
					},
					complete: function complete(XMLHttpRequest, textStatus) {
						$('#loadingToast').hide();
					}
				});
			}

			function konwClose(id) {
				$('#' + id).hide();
			}
		</script>
	</body>

</html>