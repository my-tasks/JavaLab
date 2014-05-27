<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<table class=header>
	<tr>
		<td class="header" id="title"><bean:message
				key="news.label.management" /></td>
		<td class="header" id="language">
			<table class="lang">
				<tr>
					<td class="lang"><html:link styleClass="lang" action="switchLang?language=en">
							<bean:message key="lang.en" />
						</html:link></td>
					<td class="lang"><html:link styleClass="lang" action="switchLang?language=ru">
							<bean:message key="lang.ru" />
						</html:link></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
