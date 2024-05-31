<%@ page import="ru.msnih.resumes.model.Resume" %>
<%@ page import="ru.msnih.resumes.model.ContactType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюмов</title>
</head>
<body>
<c:import url="fragments/header.jsp"/>
<section>
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="resume" items="${resumes}">
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.EMAIL)}</td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit">edit</a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete">delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<c:import url="fragments/footer.jsp"/>
</body>
</html>
