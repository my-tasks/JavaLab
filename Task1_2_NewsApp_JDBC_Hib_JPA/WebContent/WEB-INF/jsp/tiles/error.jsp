<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<table class="up">
	<tr>
		<td class="up" id="underlined"><bean:message
				key="news.label.news" /></td>
		<td class="up">&gt;&gt;</td>
		<td><bean:message key="errors.error" /></td>
	</tr>
</table>

<table class="view">
	<tr>
		<td class="error"><html:errors /></td>
	</tr>
	<tr>
		<td class="list"><input type="button" class="button"
			onclick="document.location.href = './view.do?method=list'"
			value="<bean:message key="news.button.tomain" />" /></td>
	</tr>
</table>