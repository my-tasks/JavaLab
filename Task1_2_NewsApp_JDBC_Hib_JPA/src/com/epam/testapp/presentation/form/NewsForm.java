package com.epam.testapp.presentation.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.epam.testapp.model.News;
import com.epam.testapp.util.FormatUtil;

public final class NewsForm extends ActionForm {
	private static final Logger LOGGER = Logger.getLogger(NewsForm.class);
	private News newsMessage = new News();
	private List<News> newsList;
	private Integer[] selectedToDelete;
	private String pattern;

	public News getNewsMessage() {
		return newsMessage;
	}

	public void setNewsMessage(News newsMessage) {
		this.newsMessage = newsMessage;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public Integer[] getSelectedToDelete() {
		return selectedToDelete;
	}

	public void setSelectedToDelete(Integer[] selectedToDelete) {
		this.selectedToDelete = selectedToDelete;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setDateAsString(String dateAsString) {
		getNewsMessage().setDate(
				FormatUtil.getDateFromString(dateAsString, pattern));
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		LOGGER.debug("Resetting the form");
		this.newsMessage = new News();
		this.newsList = null;
		this.selectedToDelete = new Integer[1];
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		LOGGER.debug("Processing form validating");
		ActionErrors errors = new ActionErrors();
		if (this.newsMessage.getTitle() == null
				|| this.newsMessage.getTitle().trim().length() == 0) {
			errors.add("title", new ActionMessage("errors.required"));
		}
		if (this.newsMessage.getTitle().trim().length() > 100) {
			errors.add("title", new ActionMessage("errors.length", 100));
		}
		if (this.newsMessage.getDate() == null) {
			errors.add("date", new ActionMessage("errors.date.format.invalid"));
		}
		if (this.newsMessage.getBrief() == null
				|| this.newsMessage.getBrief().trim().length() == 0) {
			errors.add("brief", new ActionMessage("errors.required"));
		}
		if (this.newsMessage.getBrief().trim().length() > 500) {
			errors.add("brief", new ActionMessage("errors.length", 500));
		}
		if (this.newsMessage.getContent() == null
				|| this.newsMessage.getContent().length() == 0) {
			errors.add("content", new ActionMessage("errors.required"));
		}
		if (this.newsMessage.getContent().trim().length() > 2048) {
			errors.add("content", new ActionMessage("errors.length", 2048));
		}
		return errors;
	}
}
