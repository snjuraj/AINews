package com.example.joboxapplication;

import java.util.List;

public class NewsData {
	private String status;
	private List<NewsArticles> articles;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<NewsArticles> getArticles() {
		return articles;
	}

	public void setUrlToImage(List<NewsArticles> articles) {
		this.articles = articles;
	}

}
