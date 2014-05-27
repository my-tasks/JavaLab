<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript" src="./resources/js/constants.jsp"></script>
<script type="text/javascript" src="./resources/js/validation.js"></script>

<table class="up">
	<tr>
		<td class="up" id="underlined"><bean:message
				key="news.label.news" /></td>
		<td class="up">&gt;&gt;</td>
		<td><bean:message key="news.label.addnews" /></td>
	</tr>
</table>

<fmt:setLocale value="en" />
<html:form action="save" method="post" onsubmit="return false">
	<input type="hidden"
		name="<%=org.apache.struts.taglib.html.Constants.TOKEN_KEY%>"
		value="<bean:write name="<%=org.apache.struts.Globals.TRANSACTION_TOKEN_KEY%>"/>">
	<input type="hidden" name="newsMessage.id"
		value="${newsForm.newsMessage.id}" />
	<table class="view">
		<tr>
			<td></td>
			<td class="error" id="title_error"><html:errors property="title" /></td>
		</tr>
		<tr>
			<td class="label" id="add"><bean:message key="news.label.title" /></td>
			<td class="content"><input type="text" id="news_title" size="60"
				name="newsMessage.title" value="${newsForm.newsMessage.title}" /></td>
		</tr>
		<tr>
			<td></td>
			<td class="error" id="date_error"><html:errors property="date" /></td>
		</tr>
		<tr>
			<td class="label"><bean:message key="news.label.date" /></td>
			<td class="content"><input type="hidden" id="pattern"
				name="pattern" value='<bean:message key="date.format" />' /> <input
				type="text" id="news_date" name="dateAsString"
				title='<bean:message key="date.format" />'
				name="newsMessage.date"
				value='<bean:write name="newsForm" formatKey="date.format"
						property="newsMessage.date" />' />
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="error" id="brief_error"><html:errors property="brief" /></td>
		</tr>
		<tr>
			<td class="label" id="add"><bean:message key="news.label.brief" /></td>
			<td class="content"><textarea id="news_brief"
					name="newsMessage.brief" rows="7" cols="47"><c:out
						value="${newsForm.newsMessage.brief}" /></textarea></td>
		</tr>
		<tr>
			<td></td>
			<td class="error" id="content_error"><html:errors
					property="content" /></td>
		</tr>
		<tr>
			<td class="label" id="add"><bean:message
					key="news.label.content" /></td>
			<td class="content"><textarea id="news_content"
					name="newsMessage.content" rows="15" cols="47"><c:out
						value="${newsForm.newsMessage.content}" /></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div class="buttons" id="edit">
					<table>
						<tr>
							<td class="lang"><html:hidden property="method" value="save" />
								<button id="saveButton" class="button"
									onclick="validateToSave()">
									<bean:message key="news.button.save" />
								</button></td>

							<td class="lang"><input type="button" class="button"
								onclick="document.location.href='./edit.do?method=cancel&newsMessage.id=${newsForm.newsMessage.id}'"
								value="<bean:message key="news.button.cancel" />" /></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</html:form>