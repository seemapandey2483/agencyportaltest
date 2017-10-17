<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:useBean id="myDate" class="java.util.Date"/>
<title>jQuery jTable Setup in java</title>
<!-- Include one of jTable styles. -->
<link href="css/lightcolor/blue/jtable.css" rel="stylesheet" type="text/css" />

<!-- <link href="css/metro/blue/jtable.css" rel="stylesheet" type="text/css" /> -->
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<!-- Include jTable script file. -->
<script src="<spring:url value="js/jquery-1.8.2.js"/>" type="text/javascript"></script>
<script src="<spring:url value="js/jquery-ui-1.10.3.custom.js"/>" type="text/javascript"></script>
<script src="<spring:url value="js/jquery.jtable.js"/>" type="text/javascript"></script>
