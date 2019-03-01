<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="/common/meta.jsp"%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form id="bizForm">
              <h1>后台管理系统</h1>
              <div>
                <input type="text" class="form-control" id="loginName" name="loginName" placeholder="请填写登录名" autocomplete="off"/>
              </div>
              <div>
                <input type="password" class="form-control" id="pwd" name="pwd" placeholder="请填写登录密码" autocomplete="off"/>
              </div>
              <div>
                <a id="login-btn" class="btn btn-default" href="javaScript:void(0)" onClick="userLogin()">登录</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">
                <div class="clearfix"></div>
                <br />
                <div>
                  <h1></h1>
                  <p>©2018 All Rights Reserved. template. Privacy and Terms</p>
                </div>
              </div>
            </form>
          </section>
        </div>
      </div>
    </div>
    <%@ include file="/common/script.jsp"%>
    <script type="text/javascript">
    		function userLogin(){
    			if(!checkForm()){
    				return false;
    			}
    			$.ajax({
				type : "POST",
				url : "${ctx}/doLogin",
				dataType : "json",
				async:false,
				data:$("#bizForm").serialize(),
				beforeSend : function (XMLHttpRequest){
					$("#login-btn").text("登录中...");
					$("#login-btn").attr("disabled",true);
				},
				success :function (data){
					if(data==undefined||data==""){
						xAlert("提示","登录失败","error");
						return;
					}
					if(data.resCode!=1){
						xAlert("提示","登录失败! "+data.resMsg,"error");
						return;
					}
					setTimeout(function(){
						window.location.href="${ctx}/welcome";
					},500)
				} , 
				error :function (XMLHttpRequest, textStatus, errorThrown){
					xAlert("提示","登录失败! 请稍后再试","error");
				},
				complete :function complete(XMLHttpRequest, textStatus){
					if(XMLHttpRequest.readyState==4){
						$("#login-btn").removeAttr("disabled");
					$("#login-btn").text("登录");
					}
				}
			});
    		}
    		function checkForm(){
    			if($("#loginName").val()==""){
    				xAlert("提示","登录名不能为空","error");
    				return false;
    			}
    			if($("#pwd").val()==""){
    				xAlert("提示","登录密码不能为空","error");
    				return false;
    			}
    			return true;
    		}
    </script>
  </body>
</html>

