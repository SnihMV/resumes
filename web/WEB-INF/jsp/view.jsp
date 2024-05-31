<%@ page import="ru.msnih.resumes.model.TextSection" %>
<%@ page import="ru.msnih.resumes.model.ListSection" %>
<%@ page import="ru.msnih.resumes.model.OrganizationSection" %>
<%@ page import="ru.msnih.resumes.util.HtmlUtil" %>
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
    <div>
        <h1>${resume.fullName}&nbsp
            <a href="${pageContext.request.contextPath}/resume?action=edit&uuid=${resume.uuid}">EDIT</a>
        </h1>
        <c:forEach var="contactType" items="${resume.contacts}">
            <dl>
                <dt>${contactType.key.title}</dt>
                <dd>${contactType.value}</dd>
            </dl>
        </c:forEach>
    </div>
    <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <c:set var="sectionType" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
                <jsp:useBean id="section" type="ru.msnih.resumes.model.Section"/>
                <tr>
                    <td colspan="2">
                        <h2><a name="${sectionType.name()}">${sectionType.title}</a></h2>
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${sectionType == 'OBJECTIVE' || sectionType == 'PERSONAL'}">
                        <tr>
                            <td colspan="2">
                                <%=((TextSection) section).getContent()%>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${sectionType == 'ACHIEVEMENTS' || sectionType == 'QUALIFICATIONS'}">
                        <tr>
                            <td colspan="2">
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection)section).getList()%>">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${sectionType=='EDUCATION' || sectionType=='EXPERIENCE'}">
                        <c:forEach var="org" items="<%=((OrganizationSection)section).getOrganizations()%>">
                            <tr>
                                <td colspan="2">
                                    <c:choose>
                                        <c:when test="${org.link.url==null}"><h3>${org.link.name}</h3></c:when>
                                        <c:otherwise><h3><a href="http://${org.link.url}">${org.link.name}</a>
                                        </h3></c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${org.positions}">
                                <jsp:useBean id="position" type="ru.msnih.resumes.model.Organization.Position"/>
                                <tr>
                                    <td><%=HtmlUtil.getTimeRange(position)%>
                                    </td>
                                    <td>
                                        <b>${position.title}</b><br>
                                            ${position.description}
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
        </c:forEach>
    </table>
<hr>
    <button onclick="location.replace('/list')">OK</button>
</section>
<c:import url="fragments/footer.jsp"/>
</body>
</html>
