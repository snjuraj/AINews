package com.example.joboxapplication;

public class NewsArticles {
	private String title;
	private String urlToImage;
	private String description;
	private Integer randomViewType;
	private String publishedAt;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRandomViewType() { return randomViewType; }

	public void setRandomViewType(Integer randomViewType) {
		this.randomViewType = randomViewType;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

}
