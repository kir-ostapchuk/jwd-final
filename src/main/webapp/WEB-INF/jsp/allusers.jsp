<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

    <title>Tiger bet - Online Football Betting</title>
</head>
<body>

    <jsp:include page="header.jsp"/>
    <h2>Users</h2>
    <c:if test="${not empty requestScope.users}">
        <c:forEach var="user" items="${requestScope.users}">
            <li>Username: ${user.name}, role: ${user.role}</li>
            <form action="${pageContext.request.contextPath}/controller?command=update_role" method="post">
                <input type="hidden" name="userName" value="${user.name}" />
                <input type="submit" value="Update role" />
            </form>
            <form action="${pageContext.request.contextPath}/controller?command=rollback_role" method="post">
                <input type="hidden" name="userName" value="${user.name}" />
                <input type="submit" value="Rollback role" />
            </form>
        </c:forEach>
    </c:if>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</body>
</html>
