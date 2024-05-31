<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.msnih.resumes.model.*" %>
<%@ page import="ru.msnih.resumes.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/editResume.js" defer></script>
    <jsp:useBean id="resume" class="ru.msnih.resumes.model.Resume" scope="request"/>
    <title>Edit ${resume.fullName}'s resume </title>
</head>
<body>
<c:import url="fragments/header.jsp"/>
<form method="post" action="resume" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <div id="fullName">
        <h3>Имя:</h3>
        <input type="text" name="fullName" value="${resume.fullName}" size="45" required>
    </div>

    <hr/>
    <div id="contacts">
        <h3>Контакты:</h3>
        <div class="cont_div">
            <c:forEach var="contactType" items="${ContactType.values()}">
                <dl>
                    <dt>${contactType.title}</dt>
                    <dd><input type="text" name="${contactType}" value="${resume.getContact(contactType)}" size="35">
                    </dd>
                </dl>
            </c:forEach>
        </div>
    </div>
    <hr>
    <c:forEach var="sectionType" items="<%=SectionType.values()%>">
        <c:set var="section" value="${resume.getSection(sectionType)}"/>
        <jsp:useBean id="section" type="ru.msnih.resumes.model.Section"/>
        <div class="section_div" id="${sectionType}">
            <h2><span>${sectionType.title}</span>
                <c:choose>
                <c:when test="${sectionType=='OBJECTIVE'}">
            </h2>
            <input type="text" name="${sectionType}" value="<%=section%>">
            </c:when>
            <c:when test="${sectionType=='PERSONAL'}">
                </h2>
                <textarea name="${sectionType}"><%=section%></textarea>
            </c:when>
            <c:when test="${sectionType=='ACHIEVEMENTS'||sectionType=='QUALIFICATIONS'}">
                <input type="button" class="add_item" name="${sectionType}" value="Добавить"/>
                </h2>
                <ul id="${sectionType}ul">
                    <c:forEach var="item" items="<%=((ListSection)section).getList()%>" varStatus="counter">
                        <li><input type="text" name="${sectionType}" value="${item}">
                            <input type="button" class="rm_item" value="Minus"/>
                        </li>
                    </c:forEach>
                </ul>

            </c:when>
            <c:when test="${sectionType=='EDUCATION'|| sectionType=='EXPERIENCE'}">
                <input type="button" class="add_org" value="add Org"/>
                </h2>
                <div class="org_container">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>"
                               varStatus="orgCounter">
                        <div class="org_div" id="${sectionType}.${orgCounter.index}">
                            <dl>
                                <dt>Название учреждения:</dt>
                                <dd><input class="org_name" type="text" name="${sectionType}.${orgCounter.index}.name"
                                           value="${org.link.name}" required>
                                </dd>
                            </dl>
                            <dl>
                                <dt>Сайт учреждения:</dt>
                                <dd><input class="org_url" type="text" name="${sectionType}.${orgCounter.index}.url}"
                                           value="${org.link.url}">
                                </dd>
                            </dl>
                            <h3><span>Занимаемые позиции:</span>&nbsp
                                <input class="add_pos" type="button" value="add pos"/></h3>
                            <div class="pos_container">
                                <c:forEach var="position" items="${org.positions}" varStatus="posCounter">
                                    <jsp:useBean id="position" type="ru.msnih.resumes.model.Organization.Position"/>
                                    <div class="pos_div" id="${sectionType}.${orgCounter.index}.${posCounter.index}">
                                        <dl>
                                            <dt>Position</dt>
                                            <dd><input class="pos_title" type="text"
                                                       name="${sectionType}.${orgCounter.index}.${posCounter.index}.title"
                                                       value="${position.title}" required></dd>
                                        </dl>
                                        <dl>
                                            <dt>Description</dt>
                                            <dd>
                                            <textarea class="pos_description"
                                                      name="${sectionType}.${orgCounter.index}.${posCounter.index}.description"
                                                      rows="5"
                                                      cols="75">${position.description}
                                            </textarea>
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Since</dt>
                                            <dd><input class="pos_start" type="text" required
                                                       name="${sectionType}.${orgCounter.index}.${posCounter.index}.startDate"
                                                       value="<%=position.getStartDate()!=null?DateUtil.format(position.getStartDate()):""%>"
                                                       placeholder="MM-yyyy"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Until</dt>
                                            <dd><label
                                                    title="Оставьте поле пустым, если продолжаете занимать эту должность">
                                                <input type="text" class="pos_end"
                                                       name="${sectionType}.${orgCounter.index}.${posCounter.index}.endDate"
                                                       value="<%= position.getEndDate()==null? "": DateUtil.format(position.getEndDate())%>"
                                                       placeholder="MM-yyyy">
                                            </label></dd>
                                        </dl>
                                        <input class="rm_pos" type="button" value="Удалить позицию"/>
                                    </div>
                                </c:forEach>
                            </div>
                            <input class="rm_org" type="button" value="Удалить организацию"
                                   style="display: block; margin: auto"/>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            </c:choose>
        </div>
        <br>
    </c:forEach>
    <button type="submit">Сохранить</button>
</form>
<button onclick="location.replace('/list')">Отмена</button>
<c:import url="fragments/footer.jsp"/>
</body>
</html>
