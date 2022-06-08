<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="util" uri="customtags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="app align-content-stretch d-flex flex-wrap">
    <c:import url="../admin-sidebar.jsp"/>
    <div class="app-container">
        <c:import url="../admin-header.jsp"/>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1>Search results: <c:out value="${search}"/></h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <form action="/controller" method="GET">
                                <div class="col-md-3 mb-3 ms-auto">
                                    <div class="input-group">
                                        <input type="text" name="search" class="form-control" placeholder="Enter name, surname or email" id="inputSearchQuery" value="<c:out value="${search}"/>">
                                        <input type="hidden" name="command" value="admin_search_user">
                                        <span class="input-group-text p-0" id="basic-addon1">
                                            <input type="submit" class="custom-search" value="Search">
                                        </span>
                                    </div>
                                </div>
                            </form>
                            <div class="card">
                                <div class="card-body">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Surname</th>
                                            <th scope="col">Role</th>
                                            <th scope="col">Status</th>
                                            <th scope="col">Email</th>
                                            <th scope="col">Balance</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="user" items="${list}">
                                            <tr>
                                                <td><c:out value="${user.id}"/></td>
                                                <td><c:out value="${user.name}"/></td>
                                                <td><c:out value="${user.surname}"/></td>
                                                <td><c:out value="${user.role}"/></td>
                                                <td>
                                                    <c:choose>
                                                    <c:when test="${user.status == 'INACTIVE'}">
                                                    <span class="badge badge-style-light rounded-pill badge-warning">
                                                        </c:when>
                                                        <c:when test="${user.status == 'ACTIVE'}">
                                                            <span class="badge badge-style-light rounded-pill badge-success">
                                                        </c:when>
                                                        <c:when test="${user.status == 'BLOCKED'}">
                                                            <span class="badge badge-style-light rounded-pill badge-danger">
                                                        </c:when>
                                                        <c:when test="${user.status == 'VERIFICATION'}">
                                                            <span class="badge badge-style-light rounded-pill badge-primary">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge badge-style-light rounded-pill badge-light">
                                                        </c:otherwise>
                                                    </c:choose>
                                                            <c:out value="${user.status}"/>
                                                         </span>
                                                </td>
                                                <td><c:out value="${user.email}"/></td>
                                                <td>
                                                    $<fmt:formatNumber value = "${user.balance}" maxFractionDigits = "2"/>
                                                </td>
                                                <td>
                                                    <div class="btn-group dropstart">
                                                        <button type="button" class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                            Action
                                                        </button>
                                                        <ul class="dropdown-menu">
                                                            <li>
                                                                <form action="/controller" method="POST">
                                                                    <input type="hidden" name="userId" value="<c:out value="${user.id}"/>">
                                                                    <input type="hidden" name="command" value="admin_to_edit_user">
                                                                    <input type="submit" class="dropdown-item" value="Edit">
                                                                </form>
                                                            </li>
                                                            <li>
                                                                <form action="/controller" method="POST">
                                                                    <input type="hidden" name="userId" value="<c:out value="${user.id}"/>">
                                                                    <input type="hidden" name="command" value="admin_delete_user">
                                                                    <input type="submit" class="dropdown-item" value="Delete">
                                                                </form>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Javascripts -->
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>
</html>