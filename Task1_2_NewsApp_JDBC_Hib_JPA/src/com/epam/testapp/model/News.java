package com.epam.testapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyToOne;

@NamedQuery(name = "getNewsList", query = "SELECT n FROM News n ORDER BY n.date DESC, n.id DESC")
@Entity
@Table(name = "NEWS")
public final class News implements Serializable {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "NEWS_ID")
	private Integer id;
	@Column(name = "NEWS_TITLE", nullable = false)
	private String title;
	@Column(name = "NEWS_BRIEF", nullable = false)
	private String brief;
	@Column(name = "NEWS_CONTENT", nullable = false)
	private String content;
	@Temporal(TemporalType.DATE)
	@Column(name = "NEWS_DATE", nullable = false)
	private Date date;

	public News() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", brief=" + brief
				+ ", content=" + content + ", date=" + date + "]";
	}

}
