<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<script type="text/javascript" src="./resources/js/constants.jsp"></script>
<script type="text/javascript" src="./resources/js/confirm.js"></script>

<table class="up">
	<tr>
		<td class="up" id="underlined"><bean:message
				key="news.label.news" /></td>
		<td class="up">&gt;&gt;</td>
		<td><bean:message key="news.label.newsview" /></td>
	</tr>
</table>

<table class="view">
	<tr>
		<td class="label" id="view"><bean:message key="news.label.title" /></td>
		<td class="content"><bean:write name="newsForm"
				property="newsMessage.title" /></td>
	</tr>
	<tr>
		<td class="label" id="view"><bean:message key="news.label.date" /></td>
		<td class="content"><bean:write name="newsForm"
				property="newsMessage.date" formatKey="date.format" /></td>
	</tr>

	<tr>
		<td class="label" id="view"><bean:message key="news.label.brief" /></td>
		<td class="content"><bean:write name="newsForm"
				property="newsMessage.brief" /></td>
	</tr>
	<tr>
		<td class="label" id="view"><bean:message
				key="news.label.content" /></td>
		<td class="content"><bean:write name="newsForm"
				property="newsMessage.content" /></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<div class="buttons" id="view">
				<html:form action="/edit" method="get">
					<table class="lang">
						<tr>
							<td class="lang"><input type="hidden" id="method"
								name="method" /> <input type="hidden" name="newsMessage.id"
								value="${newsForm.newsMessage.id}" /> <input type="button"
								class="button" onclick="editNews()"
								value="<bean:message key="news.button.edit" />" /></td>

							<td class="lang"><input type="hidden"
								name="selectedToDelete[0]" value="${newsForm.newsMessage.id}" />
								<input type="button" class="button" onclick="deleteNews()"
								value="<bean:message key="news.button.delete" />" /></td>
						</tr>
					</table>
				</html:form>
			</div>
		</td>
	</tr>
</table>