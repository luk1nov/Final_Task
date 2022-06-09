<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="pagecontent"/>
<html>
<head>
    <title><fmt:message key="label.add_new_car"/></title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300;400;500;600;700;800&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/perfectscroll/perfect-scrollbar.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/highlight/styles/github-gist.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/plugins/select2/css/select2.min.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resources/css/main.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

    <link rel="icon" type="image/png" sizes="32x32" href="${pageContext.request.contextPath}/resources/images/neptune.png" />
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/images/neptune.png" />

    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="app align-content-stretch d-flex flex-wrap">
    <%@include file="../admin-sidebar.jsp"%>
    <div class="app-container">
        <%@include file="../admin-header.jsp"%>
        <div class="app-content">
            <div class="content-wrapper">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col">
                            <div class="page-description">
                                <h1><fmt:message key="label.add_new_car"/></h1>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mx-auto">
                            <div class="card">
                                <div class="card-body">
                                    <div class="example-content">
                                        <form class="row g-3" action="/controller" method="POST" enctype="multipart/form-data">
                                            <div class="col-md-6">
                                                <label for="inputBrand" class="form-label"><fmt:message key="label.brand"/></label>
                                                <select name="carBrand" class="js-states form-control" tabindex="-1" id="inputBrand" style="display: none; width: 100%">
                                                    <option value="Audi">Audi</option>
                                                    <option value="BMW">BMW</option>
                                                    <option value="Mercedes">Mercedes</option>
                                                    <option value="Toyota">Toyota</option>
                                                    <option value="Hyundai">Hyundai</option>
                                                    <option value="Mitsubishi">Mitsubishi</option>
                                                    <option value="Volkswagen">Volkswagen</option>
                                                    <option value="Bentley">Bentley</option>
                                                    <option value="Porsche">Porsche</option>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="inputModel" class="form-label"><fmt:message key="label.model"/></label>
                                                <input name="carModel" type="text" class="form-control" id="inputModel">
                                            </div>
                                            <div class="col-md-12">
                                                <label for="inputVin" class="form-label"><fmt:message key="label.vin_code"/></label>
                                                <input name="carVinCode" type="text" class="form-control" id="inputVin" maxlength="17" minlength="17">
                                            </div>
                                            <div class="col-6">
                                                <label for="inputPower" class="form-label"><fmt:message key="label.car_power"/></label>
                                                <input name="carInfoPower" type="text" class="form-control" id="inputPower">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputAcceleration" class="form-label"><fmt:message key="label.car_acceleration"/> 0-100</label>
                                                <input name="carInfoAcceleration" type="text" class="form-control" id="inputAcceleration">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputDrivetrain" class="form-label"><fmt:message key="label.car_drivetrain"/></label>
                                                <select name="carInfoDrivetrain" id="inputDrivetrain" class="form-select">
                                                    <option selected>AWD</option>
                                                    <option>RWD</option>
                                                    <option>FWD</option>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputCarCategory" class="form-label"><fmt:message key="label.category"/></label>
                                                <select name="carCategory" id="inputCarCategory" class="form-select">
                                                    <c:forEach var="category" items="${list}">
                                                        <option value="<c:out value="${category.id}"/>"><c:out value="${category.title}"/></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputPrice" class="form-label"><fmt:message key="label.price"/></label>
                                                <input name="carRegularPrice" type="text" class="form-control" id="inputPrice">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputSalePrice" class="form-label"><fmt:message key="label.sale_price"/></label>
                                                <input name="carSalePrice" type="text" class="form-control" id="inputSalePrice">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="inputActive" class="form-label"><fmt:message key="label.active"/></label>
                                                <select name="carActive" id="inputActive" class="form-select">
                                                    <option selected value="true"><fmt:message key="label.active"/></option>
                                                    <option value="false"><fmt:message key="label.repair"/></option>
                                                </select>
                                            </div>
                                            <div class="col-md-12">
                                                <label for="formFile" class="form-label"><fmt:message key="label.photo"/></label>
                                                <input name="carImage" class="form-control" type="file" id="formFile">
                                            </div>
                                            <input type="hidden" name="command" value="admin_add_new_car">
                                            <div class="col-12">
                                                <input type="submit" class="btn btn-primary" value="<fmt:message key="label.action_add"/>"/>
                                            </div>
                                        </form>
                                    </div>
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
<script src="${pageContext.request.contextPath}/resources/plugins/select2/js/select2.full.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/highlight/highlight.pack.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
<script>
    $('#inputBrand').select2();
</script>
</body>
</html>
