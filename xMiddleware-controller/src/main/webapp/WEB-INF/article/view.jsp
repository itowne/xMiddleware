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
		<title>view</title>
		<link rel="stylesheet" href="${ctx}/resources/weui/css/weui.css" />
		<link rel="stylesheet" href="${ctx}/resources/weui/css/custom.css" />
		<style>
			.weui-article p{
				margin: 0px;
			}
			.clean-brc{
				background-color: #FFFFFF;
			}
			.process-text{
				color: #888;
				margin-right: 1.5em ;
				font-size: 15px;
			}
			.sec-v-n,.sec-vbtn-n{
				display: none;
			}
			.page-desc-date{
				margin-left: 2em;
			}
			.page-desc-aut{
				color:#003580;
			}
		</style>
	</head>
	<body ontouchstart>
		<div class="container" id="container"></div>
		<div class="page article js_show">
			<div class="page__hd">
				<h1 class="page__title">${article.title}</h1>
				<p class="page__desc"><span class="page-desc-aut">${article.author}</span><span class="page-desc-date">${article.createTimeLabel}</span></p>
			</div>
			<form id="bizForm">
			<div class="page__bd">
				<article class="weui-article">
					<section>
						<section>
							${article.content}
						</section>
						<section class="sec-cl-n">
							<div class="weui-cells__title">投票选项</div>
							<div class="weui-cells weui-cells_checkbox">
							</div>
						</section>
						<section class=".sec-vbtn-n">
							<div class="page__bd page__bd_spacing">
								<input type="hidden" name="articleId" value="${article.articleId}"/>
        							<a href="javascript:void(0);" class="weui-btn weui-btn_primary" onclick="doVote()">投票</a>
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
		 <div id="toast" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-icon-success-no-circle weui-icon_toast"></i>
            <p class="weui-toast__content">投票成功!</p>
        </div>
    </div>
    <div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">投票处理中...</p>
        </div>
    </div>
     <div class="js_dialog" id="iosDialog2" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div id="idgText" class="weui-dialog__bd">很抱歉！投票失败请稍后再试.</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:void(0);" class="weui-dialog__btn weui-dialog__btn_primary" onclick="konwClose('iosDialog2')">知道了</a>
                </div>
            </div>
        </div>
         <div class="js_dialog" id="checkVote" style="display: none;">
            <div class="weui-mask"></div>
            <div class="weui-dialog">
                <div class="weui-dialog__bd">请选择投票选项!</div>
                <div class="weui-dialog__ft">
                    <a href="javascript:void(0);" class="weui-dialog__btn weui-dialog__btn_primary" onclick="konwClose('checkVote')">确定</a>
                </div>
            </div>
        </div>
		<script src="${ctx}/resources/weui/js/zepto.min.js"></script>
		<script type="text/javascript">
			var vir = '${vir}';
			if(vir!=""){
				$(".sec-v-n").removeClass("sec-v-n");
				$(".sec-vbtn-n").removeClass("sec-vbtn-n");
				var virObj = eval('(' + vir + ')');
				var htm=voteHtml(virObj);
				$(".weui-cells.weui-cells_checkbox").html(htm);
			}
			function voteHtml(vir){
				var htm="";
				for(var i=0;i<virObj.length;i++){
					htm+='<label class="weui-cell weui-check__label">'
                				+'<div class="weui-cell__hd">'
                    				+'<input type="checkbox" class="weui-check" name="vcrs" value="'+virObj[i].vcr.voteCountRelationId+'"/>'
                    				+'<i class="weui-icon-checked"></i>'
                				+'</div>'
                				+'<div class="weui-cell__bd">'
                    				+'<p><span class="process-text">['+virObj[i].vcr.counter+']</span>'+virObj[i].voteName+'</p>'
                    				+'<div class="weui-progress__bar clean-brc">'
                						+'<div class="weui-progress__inner-bar js_progress" style="width: '+virObj[i].process+'%;"></div>'
            						 +'</div>'
               				 +'</div>'
           					 +'</label>';
				}
				return htm;
			}
			function doVote(){
				var vir = '${vir}';
				if(vir!=""){
					if($("input[type='checkbox']:checked").length==0){
						$("#checkVote").show();
						return false;
					}
				}
				$.ajax({
				type : "POST",
				url : "${ctx}/article/view/doVote",
				dataType : "json",
				async:false,
				data:$("#bizForm").serialize(),
				beforeSend : function (XMLHttpRequest){
					$('#loadingToast').show();
				},
				success :function (data){
					if(data==undefined||data==""){
						$('#iosDialog2').show();
						return;
					}
					if(data.resCode!=1){
						$("#idgText").html(data.resMsg);
						$('#iosDialog2').show();
						return;
					}
				 $('#toast').show();
            			setTimeout(function () {
               		 $('#toast').hide();
               		 window.location.reload();
           		    }, 2000);
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","投票失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){
					$('#loadingToast').hide();
				}
			});
			}
			function konwClose(id){
				$('#'+id).hide();
			}
		</script>
	</body>
</html>