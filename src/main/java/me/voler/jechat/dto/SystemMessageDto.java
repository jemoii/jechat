package me.voler.jechat.dto;

import me.voler.jechat.dto.MessageDto;

import java.util.List;

/**
 * @since 0.2
 */
public class SystemMessageDto extends MessageDto {
    private static final long serialVersionUID = -1666859831164726224L;

    private List<String> userIds;

    public SystemMessageDto() {
    }

    public SystemMessageDto(String content, List<String> userIds) {
        super("System", content);
        this.userIds = userIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
