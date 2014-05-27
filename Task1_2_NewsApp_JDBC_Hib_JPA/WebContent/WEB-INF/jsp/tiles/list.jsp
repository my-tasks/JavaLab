<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="./resources/js/constants.jsp"></script>
<script type="text/javascript" src="./resources/js/confirm.js"></script>

<table class="up">
	<tr>
		<td class="up" id="underlined"><bean:message
				key="news.label.news" /></td>
		<td class="up">&gt;&gt;</td>
		<td><bean:message key="news.label.newslist" /></td>
	</tr>
</table>

<html:form action="edit" onsubmit="deleteSelected()">
	<html:hidden property="method" value="delete" />
	<table class="list">

		<logic:present name="newsForm" property="newsList">
			<logic:iterate id="item" name="newsForm" property="newsList">
				<tr>
					<td class="list" id="title"><b><bean:message
								key="news.label.title" />:</b> <bean:write name="item"
							property="title" /></td>
					<td class="list" id="date"><bean:write name="item"
							property="date" formatKey="date.format" /></td>
				</tr>
				<tr>
					<td class="list" id="brief"><bean:write name="item"
							property="brief" /></td>
					<td></td>
				<tr>
					<td></td>
					<td class="list" id="select"><bean:define name="item"
							property="id" id="id" />
						<table>
							<tr>
								<td class="select"><html:link
										page="/view.do?method=view&newsMessage.id=${id}">
										<bean:message key="news.label.view" />
									</html:link></td>
								<td class="select"><html:link
										page="/edit.do?method=edit&newsMessage.id=${id}">
										<bean:message key="news.label.edit" />
									</html:link></td>
								<td class="select"><html:multibox
										property="selectedToDelete" value="${id}" /></td>
							</tr>
						</table></td>
				</tr>
			</logic:iterate>
		</logic:present>
		<tr>
			<td></td>
			<td class="list" id="select"><input type="button" class="button"
				onclick="deleteSelected()"
				value="<bean:message key="news.button.delete" />" /></td>
		</tr>
	</table>
</html:form>