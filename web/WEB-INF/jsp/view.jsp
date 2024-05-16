<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="ru.msnih.resumes.model.Resume" scope="request"/>
    <title>Резюме: ${resume.fullName}</title>
</head>
<body>
<c:import url="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp
        <a href="${pageContext.request.contextPath}/resume?action=edit&uuid=${resume.uuid}">EDIT</a>
    </h1>
    <dl>
        <c:forEach var="contact" items="${resume.contacts}">

            <dt>${contact.key.title}</dt>
            <dd>${contact.value}</dd>
            <hr>
        </c:forEach>
    </dl>


    <button onclick="location.replace('/list')">OK</button>

</section>
<c:import url="fragments/footer.jsp"/>
</body>
</html>
