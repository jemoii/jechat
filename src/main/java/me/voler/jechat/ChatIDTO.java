package me.voler.jechat;

import java.io.Serializable;

import me.voler.jechat.util.ContentUtil;

public class ChatIDTO implements Serializable {

	private static final long serialVersionUID = -7226321550364580634L;
	private static final String[] COLOR_STYLE = { "#4499EE", "#1291A9", "#FE9D01", "#43A102", "#A73800" };

	private String userId;
	private String content;
	private String at; // 一对一私聊
	private int colorId; // 对话颜色个性化

	public ChatIDTO() {

	}

	public ChatIDTO(String userId, String content, String at, int colorId) {
		this.userId = userId;
		this.content = content;
		this.at = at;
		this.colorId = colorId;
	}

	public String getUserId() {
		return userId;
	}

	@Deprecated
	public String getUserIdTag() {
		String userIdStyle = "display: inline-block; width: 18%; color:" + COLOR_STYLE[colorId];
		return "<label style='" + userIdStyle + "'>" + userId + "</label>";
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return ContentUtil.convert(content);
	}

	@Deprecated
	public String getContentTag() {
		String contentStyle = "color:" + COLOR_STYLE[colorId];
		return "<span style='" + contentStyle + "'>" + ContentUtil.convert(content) + "</span>";
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAt() {
		return at;
	}

	public String getAtTag() {
		String atStyle = "color:" + COLOR_STYLE[0];
		return "<span style='" + atStyle + "'>" + "@" + at + "</span>";
	}

	public void setAt(String at) {
		this.at = at;
	}

	public int getColorId() {
		return colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
	}

}
