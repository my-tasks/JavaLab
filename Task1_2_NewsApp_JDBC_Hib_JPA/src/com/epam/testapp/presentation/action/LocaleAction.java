package com.epam.testapp.presentation.action;

import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.action.DynaActionForm;

public final class LocaleAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		String lang = dynaForm.getString("language");
		Locale locale = new Locale(lang);
		setLocale(request, locale);
		String pageFrom = request.getSession()
				.getAttribute(NewsAction.CURRENT_PAGE).toString();
		ActionForward toPage = new ActionForward(pageFrom, true);
		return toPage;
	}
}
