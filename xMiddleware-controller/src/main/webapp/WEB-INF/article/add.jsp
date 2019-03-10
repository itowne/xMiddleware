<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/common/meta.jsp"%>
<link href="${ctx}/resources/editor/summernote.css" rel="stylesheet">
<title>${title}</title>
<script type="text/javascript">
	var vdos = initVdo();
	function initVdo(){
			var vdos = '${vdos}';
			var defaultOpt="<option value=''>请选择</option>"
			var vdosObj =eval('(' + vdos + ')');
			var opt="";
			for(var i=0;i<vdosObj.length;i++){
				opt+="<option value='"+vdosObj[i].sysFileName+"'>"+vdosObj[i].vdoName+"</option>";
			}
			return opt;
		}
</script>
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
                    <h2>新增内容<small></small></h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    <form id="bizForm" data-parsley-validate class="form-horizontal form-label-left">
                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">内容标题<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text"  name="title" id="title" class="form-control col-md-7 col-xs-12">
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">内容作者
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                          <input type="text"  name="author" id="author" class="form-control col-md-7 col-xs-12" value="福建省三明市图书馆">
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">活动开始时间<span class="required">*</span>
                        </label>
                        <div class="col-md-4 col-sm-4 col-xs-6 xdisplay_inputx form-group has-feedback">
                           <input type="text" name="startTime" class="form-control has-feedback-left" id="stt">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">活动结束时间<span class="required">*</span>
                        </label>
                        <div class="col-md-4 col-sm-4 col-xs-6 xdisplay_inputx form-group has-feedback">
                           <input type="text" name="endTime" class="form-control has-feedback-left" id="edt">
                            <span class="fa fa-calendar-o form-control-feedback left" aria-hidden="true"></span>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">投票关联</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                           <select id="voteSelect" name="voteId" id="voteId" class="form-control">
                          </select>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">列表小图标<span class="required">*</span></label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                           <select id="imgSelect" name="imgId" class="form-control">
                          </select>
                        </div>
                        </div>
                        <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">是否置顶</label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                           <select id="isTop" name="isTop"  class="form-control">
                           <option value="NO">否</option>
                           <option value="YES">是</option>
                          </select>
                        </div>
                      </div>
                       <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">
                        	正文内容
                        </label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
  						<textarea id="summernote" name="editordata"></textarea>
                        </div>
                      </div>
                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                        	  <input type="hidden" name="content" id="sContent"/>
                          <button type="button" class="btn btn-success" onclick="doAdd()">新增</button>
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
	<script type="text/javascript" src="${ctx}/resources/editor/summernote.js"></script>
	<script type="text/javascript" src="${ctx}/resources/editor/summernote-zh-CN.js"></script>
	<script type="text/javascript">
		function backIndex(){
			window.location.href="${ctx}/article/index";
		}
		$(document).ready(function() {
			$("#sidebar-menu ul>li").removeClass("current-page");
			$("#sidebar-menu #art-li").addClass("current-page");
			initVote();
			initImg();
  			$('#summernote').summernote({
  				 toolbar: [
    				['style', ['bold', 'italic', 'underline', 'clear']],
				['font', ['strikethrough', 'superscript', 'subscript']],
				['fontsize', ['fontsize','fontname']],
				['color', ['color']],
				['para', ['style','ul', 'ol', 'paragraph']],
				['height', ['height']],
				['Insert',['picture','video','link','table','hr']]
				  ],
  				lang: 'zh-CN',
  				height: 400,
  				focus: true,
  				callbacks:{
  					onImageUpload: function(files, editor, $editable) {
						sendFile(files[0],editor,$editable);
  					}
  				}
  			});
  			initDaterangepicker();
		});
	function sendFile(file, editor, $editable){
		var filename = false;
		try{
		filename = file['name'];
		} catch(e){
			filename = false;
		}
		if(!filename){
			$(".note-alarm").remove();
		}
		//以上防止在图片在编辑器内拖拽引发第二次上传导致的提示错误
		data = new FormData();
		data.append("file", file);
		data.append("key",filename); //唯一性参数
		$.ajax({
		data: data,
		type: "POST",
		url: "${ctx}/article/fileUpload",
		cache: false,
		contentType: false,
		processData: false,
		success: function(data) {
			if(data==""||data==null){
				xAlert("提示","添加图片失败! 响应异常","error");
				return;
			}
			var res = eval('(' + data + ')'); 
			if(res.resCode!='1'){
				xAlert("提示","添加图片失败!["+res.resMsg+"]","error");
	       		return;
	   		}
	   		$('#summernote').summernote('insertImage',res.resMsg);
		},
		error:function(){
			alert("上传失败！");
			return;
			}
	  });
	 }
		function initDaterangepicker(){
		$('#edt,#stt').daterangepicker({
			   locale: {
			   	applyLabel: '确定',
                cancelLabel: '取消',
				format: 'YYYY-MM-DD HH:mm',
				daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
       			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
       			firstDay: 1
			  },
			  timePicker: true,
			  showDropdowns: true,
			  singleDatePicker: true,
			  timePicker24Hour: true
			}, function(start, end, label) {
			});
	}
		function initVote(){
			var votes = '${votes}';
			var defaultOpt="<option value=''>无</option>"
			var votesObj =eval('(' + votes + ')');
			var opt="";
			for(var i=0;i<votesObj.length;i++){
				opt+="<option value='"+votesObj[i].voteId+"'>"+votesObj[i].voteTitle+"</option>";
			}
			$("#voteSelect").html(defaultOpt+opt);
		}
		function initImg(){
			var imgs = '${imgs}';
			var defaultOpt="<option value=''>请选择</option>"
			var imgsObj =eval('(' + imgs + ')');
			var opt="";
			for(var i=0;i<imgsObj.length;i++){
				opt+="<option value='"+imgsObj[i].imgId+"'>"+imgsObj[i].imgName+"</option>";
			}
			$("#imgSelect").html(defaultOpt+opt);
		}
		
		function doAdd(){
			if(!checkForm()){
				return fasle;
			}
			//var xssCont=filterXSS($('#summernote').summernote('code'));
			var xssCont=$('#summernote').summernote('code');
			$("#sContent").val(xssCont);
			$.ajax({
				type : "POST",
				url : "${ctx}/article/doAddArticle",
				dataType : "json",
				async:false,
				data:$("#bizForm").serialize(),
				beforeSend : function (XMLHttpRequest){
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","新增内容失败","error");
						return;
					}
					if(data.resCode!=1){
						xAlert("提示","新增内容失败! "+data.resMsg,"error");
						return;
					}
					xAlert("提示","新增内容成功","success");
					setTimeout(function(){
						window.location.href="${ctx}/article/index";
					},3000)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","新增内容失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){}
			});
		}
		
		function checkForm(){
			if($("#title").val()==""){
				xAlert("提示","内容标题不能为空","error");
				return false;
			}
			/* if($("#author").val()==""){
				xAlert("提示","内容作者不能为空","error");
				return false;
			} */
			if($("#stt").val()==""){
				xAlert("提示","内容开始时间不能为空","error");
				return false;
			}
			if($("#edt").val()==""){
				xAlert("提示","内容结束时间不能为空","error");
				return false;
			}
			if($("#imgSelect").val()==""){
				xAlert("提示","请选择一个列表小图标","error");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>