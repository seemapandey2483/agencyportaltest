<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	
		<tiles:insertAttribute name="header" />
	
		
		<tiles:insertAttribute name="menu" />
		
			<tiles:insertAttribute name="body" />
		
		<tiles:insertAttribute name="footer" />
	
</body>
</html>