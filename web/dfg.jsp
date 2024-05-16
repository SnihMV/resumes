<%@ page import="ru.msnih.resumes.model.Resume" %>
<%@ page import="ru.msnih.resumes.model.ContactType" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюмов</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/fragments/header.jsp"/>
<section>

    <table>
        <tr>
            <th>Name</th>
            <th>Email</th>
        </tr>

            <tr>
                <td><%=resume.getFullName()%></td>
                <td><%=resume.getContact(ContactType.EMAIL)%></td>
            </tr>
        
    </table>
</section>
<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp"/>
</body>
</html>
