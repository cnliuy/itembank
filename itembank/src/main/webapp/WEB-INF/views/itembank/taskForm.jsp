<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>任务管理</title>
	<link href="${ctx}/static/bootstrap/2.3.2/css/editor.css" type="text/css" rel="stylesheet" />
	
</head>

<body>
	<!-- 
	<form id="inputForm" action="${ctx}/task/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}"/>
		<fieldset>
			<legend><small>管理任务</small></legend>
			<div class="control-group">
				<label for="task_title" class="control-label">任务名称:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"  value="${task.title}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="description" class="control-label">任务描述:</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${task.description}</textarea>
				</div>
			</div>				
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#task_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
	 -->
	
	
	<!-- add new -->
	<form id="inputForm" action="${ctx}/task/${action}" method="post" class="form-horizontal">
	<div>

		
		<div class="container">
		  <div class="hero-unit">
		    <hr/>
		    <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor">
		      <div class="btn-group">
		        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="icon-font"></i><b class="caret"></b></a>
		          <ul class="dropdown-menu">
		          </ul>
		        </div>
		      <div class="btn-group">
		        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
		          <ul class="dropdown-menu">
		          <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
		          <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
		          <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
		          </ul>
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="icon-bold"></i></a>
		        <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
		        <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="icon-strikethrough"></i></a>
		        <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="icon-underline"></i></a>
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="icon-list-ul"></i></a>
		        <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="icon-list-ol"></i></a>
		        <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i class="icon-indent-left"></i></a>
		        <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="icon-indent-right"></i></a>
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="icon-align-left"></i></a>
		        <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="icon-align-center"></i></a>
		        <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="icon-align-right"></i></a>
		        <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="icon-align-justify"></i></a>
		      </div>
		      <div class="btn-group">
				  <a class="btn dropdown-toggle" data-toggle="dropdown" title="Hyperlink"><i class="icon-link"></i></a>
				    <!--  
				    <div class="dropdown-menu input-append">
				    	
					    <input class="span2" placeholder="URL" type="text" data-edit="createLink"/>
					    <button class="btn" type="button">Add</button>
					   
		        	</div> 
		        -->
		        <a class="btn" data-edit="unlink" title="Remove Hyperlink"><i class="icon-cut"></i></a>
		
		      </div>
		      
		      <div class="btn-group">
		        <a class="btn" title="Insert picture (or just drag & drop)" id="pictureBtn"><i class="icon-picture"></i></a>
		        <input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage" />
		      </div>
		      <div class="btn-group">
		        <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="icon-undo"></i></a>
		        <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
		      </div>
		      <input type="text" data-edit="inserttext" id="voiceBtn" x-webkit-speech="">
		    </div>		
		    <div id="editor">
		      Go ahead&hellip;
		    </div>
		  </div>
	
		</div>
		
	
	
	</div>
	<input type="button" value="提交" id="submit"/> 
	</form>

	
<script>
  $(function(){
    function initToolbarBootstrapBindings() {
      var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 
            'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
            'Times New Roman', 'Verdana'],
            fontTarget = $('[title=Font]').siblings('.dropdown-menu');
      $.each(fonts, function (idx, fontName) {
          fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
      });
      $('a[title]').tooltip({container:'body'});
    	$('.dropdown-menu input').click(function() {return false;})
		    .change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
        .keydown('esc', function () {this.value='';$(this).change();});

      $('[data-role=magic-overlay]').each(function () { 
        var overlay = $(this), target = $(overlay.data('target')); 
        overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
      });
      $('#voiceBtn').hide();
      // if ("onwebkitspeechchange"  in document.createElement("input")) {
      //   var editorOffset = $('#editor').offset();
      //   $('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editor').innerWidth()-35});
      // } else {
      //   $('#voiceBtn').hide();
      // }
    };
    initToolbarBootstrapBindings();  
    $('#editor').wysiwyg();
    window.prettyPrint && prettyPrint();
    
    
	//liuy add start
    $("#submit").click(
			function() {
				var htmlinfo = $('#editor').html();
				alert(htmlinfo);
				jQuery.ajax( {				
					type: 'POST',
					//contentType : 'multipart/form-data',
					url : '${ctx}/task/cccc',
					data : {val:htmlinfo},
					//dataType : 'html',
					success: function(data) {
						alert("新增成功！");
					},
					error: function(data) {
						alert("error")
					}
				});
			});   
  	});  
	//liuy add stop
</script>	



	
	
	
	
	
</body>
</html>
