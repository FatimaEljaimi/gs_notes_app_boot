<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>


<jsp:include page="../fragments/userheader.jsp"/>
<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">


            <jsp:include page="../fragments/usermenu.jsp"/>

        </div>
    </nav>


    <div>
        <h3>Cadre Admin home page</h3>
        <p>Hello and welcome to your application</p>
        <f:form id = "form" action="/cadreadmin/exportArchiveFile" method="post" modelAttribute="archiveExportModel">
            <div class="col-5 my-4">
                <f:select id="select" class="form-select" aria-label="Default select example" path="idNiveau">
                    <option selected value="0" >---- Choisir le niveau ----</option>
                    <c:forEach items="${niveaux}" var="n">
                        <option value="${n.idNiveau}">${n.titre}</option>
                    </c:forEach>
                </f:select>
            </div>
            <div class="col-5 my-4">
                <f:select id="session" class="form-select" aria-label="Default select example" path="session">
                    <option selected value="0" >---- Choisir la session ----</option>
                    <option value="Normale">Normale</option>
                    <option value="Rattrapage">Rattrapage</option>
                </f:select>
            </div>

			<div class="col-12">
				<button id="submit" type="submit" disabled class="btn btn-primary">export</button>
			</div>

        </f:form>

    </div>


    <jsp:include page="../fragments/userfooter.jsp"/>

