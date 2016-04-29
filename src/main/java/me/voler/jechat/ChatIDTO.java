package me.voler.jechat;

import java.io.Serializable;

public class ChatIDTO implements Serializable {

	private static final long serialVersionUID = -7226321550364580634L;

	private String userId;
	private String content;
	private String at; // 一对一私聊

	public ChatIDTO() {

	}

	public ChatIDTO(String userId, String content, String at) {
		this.userId = userId;
		this.content = content;
		this.at = at;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

}
