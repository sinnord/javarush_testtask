<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ToDo List</title>
    <%-- <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet"> --%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <style type="text/css">
        .myrow-container {
            margin: 20px;
        }
    </style>

</head>
<body class=".container-fluid">
<div class="container myrow-container">
    <div class="panel-success">
        <div class="panel-heading">
            <h3 class="panel-title">
                <div align="left"><b>Tasks</b></div>
                <div align="right"><a href="/createTask">Add New Task</a> </div>
            </h3>
        </div>
        <div class="panel-body">
            <div class="panel-heading">
                <h3 class="panel-title">
                    <div align="left">
                        <c:if test="${filter.equals('all')}">
                            All tasks
                        </c:if>
                        <c:if test="${!filter.equals('all')}">
                            <a href="getAllTasks?done=all&page=1">All tasks</a>
                        </c:if>

                        <c:if test="${filter.equals('done')}">
                            Done tasks
                        </c:if>
                        <c:if test="${!filter.equals('done')}">
                            <a href="getAllTasks?done=done&page=1">Done tasks</a>
                        </c:if>

                        <c:if test="${filter.equals('undone')}">
                            Undone tasks
                        </c:if>
                        <c:if test="${!filter.equals('undone')}">
                            <a href="getAllTasks?done=undone&page=1">Undone tasks</a>
                        </c:if>
                    </div>
                    <div align="right">
                        <a href="addTestTasks">Add Test Tasks</a>
                    </div>
                </h3>
            </div>

            <c:if test="${empty tasksList}">
                No tasks found.
            </c:if>
            <c:if test="${not empty tasksList}">
                <table class="table table-hover table-bordered">
                    <thead style="background-color: #bce8f1;">
                    <tr>
                        <th>Id</th>
                        <th>Description</th>
                        <th>Schedule</th>
                        <th>Done</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="task" items="${tasksList}">
                        <tr>
                            <th><c:out value="${task.id}"/></th>
                            <th><c:out value="${task.description}"/></th>
                            <th><fmt:formatDate value="${task.schedule}" pattern="dd/MM/yyyy"/></th>
                            <th><c:if test="${task.done}"><img src="/resources/yes.png" width="32" height="32"/></c:if>
                                <c:if test="${not task.done}"><img src="/resources/no.png" width="32" height="32"></c:if></th>
                            <th><a href="editTask?id=<c:out value='${task.id}'/>">Edit</a> </th>
                            <th><a href="deleteTask?id=<c:out value='${task.id}'/>">Delete</a> </th>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div align="left">
                <c:forEach var="pageNum" items="${pageList}">
                    <c:if test="${pageNum == curPage}">
                        ${pageNum}
                    </c:if>
                    <c:if test="${pageNum != curPage}">
                        <a href="getAllTasks?done=${filter}&page=${pageNum}">${pageNum}</a>
                    </c:if>
                </c:forEach>
                </div>
            </c:if>
        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</body>
</html>
