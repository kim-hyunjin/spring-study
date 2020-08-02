<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="login.title" /></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="row d-flex justify-content-center align-items-center w-100 h-100">

    <form:form commandName="loginCommand" cssClass="col-lg-3">
        <div class="d-flex flex-column">
            <form:errors/>
            <div class="form-group">
                <label>
                <spring:message code="email"/>:<br/>
                </label>
                <form:input path="email" cssClass="form-control"/>
                <form:errors path="email" cssClass="text-danger"/>
            </div>
            <div class="form-group">
                <label>
                <spring:message code="password"/>:<br/>
                </label>
                <form:password path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="text-danger"/>
            </div>
            <label>
                <spring:message code="rememberEmail"/>:
                <form:checkbox path="rememberEmail"/>
            </label>
            <input type="submit" value="<spring:message code="login.btn"/>" class="btn btn-primary">
        </div>
    </form:form>
    </div>
</div>
</body>
</html>
