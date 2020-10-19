<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>회원 조회</title>
</head>
<body>
    <form:form commandName="cmd">
        <p>
            <label>
                from:
                <form:input path="from"/>
            </label>
            <form:errors path="from"/>
            ~
            <label>
                to:
                <form:input path="to"/>
            </label>
            <form:errors path="to"/>
            <input type="submit" value="조회">
        </p>
    </form:form>
        <c:if test="${! empty members}">
            <table>
                <tr>
                    <th>아이디</th>
                    <th>이메일</th>
                    <th>이름</th>
                    <th>가입일</th>
                </tr>
                <c:forEach var="mem" items="${members}">
                    <tr>
                        <td>${mem.id}</td>
                        <td><a href="<c:url value='/member/detail/${mem.id}'/>">${mem.email}</a></td>
                        <td>${mem.name}</td>
                        <td><fmt:formatDate value="${mem.registerDate}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

</body>
</html>
