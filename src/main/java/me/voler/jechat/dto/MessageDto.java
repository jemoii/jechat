package me.voler.jechat.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @since 0.2
 */
public class MessageDto implements Serializable {
    private static final long serialVersionUID = -727929647916034149L;

    private String userId;
    private String content;
    private transient List<String> atIds;

    public MessageDto() {
    }

    public MessageDto(String userId, String content) {
        this.userId = userId;
        this.content = content;
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

    public List<String> getAtIds() {
        return atIds;
    }

    public void setAtIds(List<String> atIds) {
        this.atIds = atIds;
    }
}
