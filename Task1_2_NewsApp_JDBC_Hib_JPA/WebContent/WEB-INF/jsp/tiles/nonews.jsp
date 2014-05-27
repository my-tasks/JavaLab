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
		<td><bean:message key="title.nonews" /></td>
	</tr>
</table>

<table class="view">
	<tr>
		<td class="content"><html:errors /></td>
	</tr>
</table>