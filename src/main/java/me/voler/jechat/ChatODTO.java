package me.voler.jechat;

import java.io.Serializable;

public class ChatODTO implements Serializable {

	private static final long serialVersionUID = -4856396522843194668L;

	private String currentId;// 发送方
	private String content;
	private int currentSize;// 当前有效用户数量

	public ChatODTO() {

	}

	public ChatODTO(String currentId, String content, int currnetSize) {
		this.currentId = currentId;
		this.content = content;
		this.currentSize = currnetSize;
	}

	public String getCurrentId() {
		return currentId;
	}

	public void setCurrentId(String currentId) {
		this.currentId = currentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(int currentSize) {
		this.currentSize = currentSize;
	}

}
