package com.epam.testapp.presentation.action;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.actions.DispatchAction;

import com.epam.testapp.database.INewsDAO;
import com.epam.testapp.exception.DoubleRequestException;
import com.epam.testapp.exception.NoConnectionAvailableException;
import com.epam.testapp.exception.NewsDatabaseIsEmptyException;
import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.NewsForm;

public class NewsAction extends DispatchAction {
	private final String LIST = "list";
	private final String VIEW = "view";
	private final String EDIT = "edit";
	private final String ERROR = "error";

	private final String VIEW_REDIRECT = "view.do?method=view";
	private final String LIST_REDIRECT = "view.do?method=list";

	private final String PREVIOUS_PAGE = "previous_page";
	public static final String CURRENT_PAGE = "current_page";
	private static Logger LOGGER = Logger.getLogger(NewsAction.class);
	private INewsDAO newsDAO;

	/*
	 * This method sets to the form newly loaded list of all news messages from
	 * the Database.
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException,
			NoConnectionAvailableException, NewsDatabaseIsEmptyException {
		String target = LIST;
		NewsForm newsForm = (NewsForm) form;
		LOGGER.debug("Processing loading news messages list from the Database...");
		List<News> list = newsDAO.getList();
		savePageUrl(request);
		if (list.size() > 0) {
			newsForm.setNewsList(list);
			LOGGER.debug("The list of news messages is loaded");
			return mapping.findForward(target);
		} else {
			throw new NewsDatabaseIsEmptyException();
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException,
			NoConnectionAvailableException, NewsNotFoundException {
		String target = VIEW;
		NewsForm newsForm = (NewsForm) form;
		LOGGER.debug("Processing loading news mewssage with ID: "
				+ newsForm.getNewsMessage().getId());
		News news = newsDAO.fetchById(newsForm.getNewsMessage().getId());
		newsForm.setNewsMessage(news);
		savePageUrl(request);
		return mapping.findForward(target);
	}

	/*
	 * This method sets the News ID to -1. It is necessary to show further that
	 * the form contains a new news message what means that the creating method
	 * (not modifying) will be required to process saving.
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException,
			NoConnectionAvailableException {
		String target = EDIT;
		NewsForm newsForm = (NewsForm) form;
		LOGGER.debug("Preparing NewsForm for new News message creation");
		newsForm.getNewsMessage().setDate(new Date(System.currentTimeMillis()));
		savePageUrl(request);
		saveToken(request);
		return (mapping.findForward(target));
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException,
			NoConnectionAvailableException, NewsNotFoundException {
		String target = EDIT;
		NewsForm newsForm = (NewsForm) form;
		Integer id = newsForm.getNewsMessage().getId();
		LOGGER.debug("Processing loading the News message for editing");
		News news = newsDAO.fetchById(id);
		newsForm.setNewsMessage(news);
		LOGGER.debug("The News message is loaded for further editing");
		savePageUrl(request);
		saveToken(request);
		return (mapping.findForward(target));
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException,
			NoConnectionAvailableException, DoubleRequestException {
		Integer id = null;
		if (isTokenValid(request, true)) {
			NewsForm newsForm = (NewsForm) form;
			News news = newsForm.getNewsMessage();
			id = news.getId();
			if (id == 0) {
				LOGGER.debug("Processing saving the news message to the Database");
				id = newsDAO.create(news);
			} else {
				LOGGER.debug("Processing modifiyng the news message");
				newsDAO.modify(news);
			}
		} else {
			throw new DoubleRequestException();
		}
		savePageUrl(request);
		ActionRedirect redirect = new ActionRedirect(VIEW_REDIRECT);
		redirect.addParameter("newsMessage.id", id);
		return redirect;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SQLException,
			NoConnectionAvailableException {
		NewsForm newsForm = (NewsForm) form;
		LOGGER.debug("Getting the list of news messages IDs to begin the process of deleting");
		Integer[] selected = newsForm.getSelectedToDelete();
		if (selected != null) {
			LOGGER.debug("Processing the deleting operation.");
			newsDAO.removeById(selected);
		}
		return new ActionRedirect(LIST_REDIRECT);
	}

	public ActionForward error404(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.404"));
		saveErrors(request, errors);
		savePageUrl(request);
		return (mapping.findForward(ERROR));
	}
	public ActionForward error(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("errors.unknown"));
		saveErrors(request, errors);
		savePageUrl(request);
		return (mapping.findForward(ERROR));
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		NewsForm newsForm = (NewsForm) form;
		Integer id = null;
		id = newsForm.getNewsMessage().getId();
		if (id == null || id == -1) {
			return new ActionRedirect(LIST_REDIRECT);
		} else {
			Object pathAttr = request.getSession().getAttribute(PREVIOUS_PAGE);
			if (pathAttr != null) {
				String path = pathAttr.toString();
				ActionForward toPage = new ActionForward(path, true);
				return toPage;
			} else {
				return mapping.findForward(ERROR);
			}
		}
	}

	/*
	 * Method sets URLs of CURRENT and PREVIOUS pages. It prevents rewriting
	 * page URLs after page refreshing or switching language. Keeping attribute
	 * CURRENT_PAGE is necessary to process the Locale switching with
	 * opportunity to forward to the same page after switching. Keeping
	 * PREVIOUS_PAGE attribute is necessary to be able to process method
	 * "cancel" which is used to return to the previous page.
	 */
	public void savePageUrl(HttpServletRequest request) {

		// getting the current page URL:
		StringBuilder currentPage = new StringBuilder(request.getAttribute(
				org.apache.struts.Globals.ORIGINAL_URI_KEY).toString());
		String queryString = request.getQueryString();
		if (queryString != null) {
			currentPage.append("?" + queryString);
		}
		HttpSession session = request.getSession();

		// checking CURRENT_PAGE attribute
		if (session.getAttribute(CURRENT_PAGE) != null) {
			// prevention of rewriting pages URL in case of double requesting
			if (!currentPage.toString().equals(
					session.getAttribute(CURRENT_PAGE).toString())) {
				String previousPage = session.getAttribute(CURRENT_PAGE)
						.toString();
				session.setAttribute(PREVIOUS_PAGE, previousPage);
			}
		}
		session.setAttribute(CURRENT_PAGE, currentPage);
	}

	public INewsDAO getNewsDAO() {
		return newsDAO;
	}

	public void setNewsDAO(INewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}
}
