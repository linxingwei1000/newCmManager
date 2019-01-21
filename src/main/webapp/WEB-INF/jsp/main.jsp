<%@page pageEncoding="UTF-8" language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<title>后台主页</title>
<div>
	<img src="<%=request.getContextPath() + "/img/cm/b.jpg"%>"  height="100%" width="100%"/>
</div>

<script type="text/javascript">
$(function(){
	Global.setNavActive("1");
});
</script>

</body>
</html>
