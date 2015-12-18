<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/taglib/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Kensite开发平台</title>
	<%@ include file="/WEB-INF/view/taglib/header.jsp" %>
	<%@ include file="/WEB-INF/view/taglib/layer.jsp" %>
	<%@ include file="/WEB-INF/view/taglib/codemirror.jsp" %>
	<link rel="stylesheet" href="${ctx_script}/codemirror/addon/display/fullscreen.css"/>
	<script src="${ctx_script}/codemirror/addon/display/fullscreen.js"></script>
</head>

<body>
	<textarea id="code" name="code">http://www.cnblogs.com/oldphper</textarea>
    <script>
        var editor = CodeMirror.fromTextArea(document.getElementById("code"), {  // 标识到textarea
            value : "http://www.cnblogs.com/oldphper1",  // 文本域默认显示的文本
            mode : "text/html",  // 模式
            theme : "night",  // CSS样式选择
            indentUnit : 2,  // 缩进单位，默认2
            smartIndent : true,  // 是否智能缩进
            tabSize : 4,  // Tab缩进，默认4
            readOnly : false,  // 是否只读，默认false
            showCursorWhenSelecting : true,
            lineNumbers : true,  // 是否显示行号
            extraKeys: {
                "F11": function(cm) {
					cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                },
                "Esc": function(cm) {
					if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
                }
              }
            // .. 还有好多，翻译不完。需要的去看http://codemirror.net/doc/manual.html#config
        });
    </script>
</body>
</html>
