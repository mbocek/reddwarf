<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><decorator:title/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="-1" />
	<%@ include file="/WEB-INF/layout/includes/style.jsp"%>
	<decorator:head />
</head>
	<body>
		<div id="header">
			<%@ include file="/WEB-INF/layout/includes/header.jsp"%>
		</div>

		<div id="body">
			<decorator:body />
		</div>

		<div id="footer">
			<%@ include file="/WEB-INF/layout/includes/footer.jsp"%>
			<%@ include file="/WEB-INF/layout/includes/copyright.jsp"%>
		</div>	
	</body>
</html>