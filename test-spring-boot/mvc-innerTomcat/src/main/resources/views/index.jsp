<%@ page language='java' contentType='text/html; charset=utf-8'
         pageEncoding='UTF-8' %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<c:set var='root' value='${pageContext.request.contextPath}'></c:set>
<!DOCTYPE>
<html>
<head>
    <title>功能展示</title>
    <!-- 加载CSS -->
    <%-- <link href='${ctx}/static/css/menu.css' rel='stylesheet'> --%>
</head>
<body>
<div class="col-md-12">
    <div class="portlet green box">
        <div class="portlet-title">
            <div class="caption">
                <i class="fa fa-cogs"></i>Checkable Tree
            </div>
            <div class="tools">
                <a href="javascript:;" class="collapse">
                </a>
                <a href="#portlet-config" data-toggle="modal" class="config">
                </a>
                <a href="javascript:;" class="reload">
                </a>
                <a href="javascript:;" class="remove">
                </a>
            </div>
        </div>
        <div class="portlet-body">
            <div id="menuTree" class="tree-demo">
            </div>
        </div>
    </div>
</div>
<!-- 加载JS -->
<script type='text/javascript' src='${root}/static/plugins/jquery-3.2.1.min.js'></script>
<script type='text/javascript' src='${root}/static/plugins/bootstrap-3.3.7-dist/js/bootstrap.min.js'></script>
<script type='text/javascript' src='${root}/static/views/index.js'></script>
</body>
</html>
