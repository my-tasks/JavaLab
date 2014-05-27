<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<div
	style="height: 150px; width: 230px; margin-top: 10px; padding-top: 15px; background-color: #cccccc">
	<div
		style="height: 30px; width: 210px; margin-left: 20px; background-color: #888888; text-align: center; color: white;">
		<bean:message key="news.label.news" />
	</div>

	<div
		style="margin-left: 40px; margin-top: 10px; width: 180px; height: 90px; background-color: white;">
		<table style="width: 180px; height: 90px;">
			<tr>
				<td class="menu">
					<ul>
						<li><html:link styleClass="menu" action="view.do?method=list">
								<bean:message key="news.label.newslist" />
							</html:link></li>
						<li><html:link styleClass="menu" action="edit.do?method=add">
								<bean:message key="news.label.addnews" />
							</html:link></li>
					</ul>
				</td>
			</tr>
		</table>
	</div>
</div>