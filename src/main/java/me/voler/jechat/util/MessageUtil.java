package me.voler.jechat.util;

import me.voler.jechat.dto.MessageDto;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 0.2
 */
public class MessageUtil {

    private static final Pattern pattern = Pattern.compile("(>@[^&@]+<)");

    public static void parse(MessageDto dto) {
        dto.setAtIds(new ArrayList<String>());

        Matcher matcher = pattern.matcher(dto.getContent());
        while (matcher.find()) {
            String rawAt = matcher.group();
            dto.getAtIds().add(rawAt.substring(2, rawAt.length() - 1));
        }

        // 私聊时发送方也要接收消息
        if (!CollectionUtils.isEmpty(dto.getAtIds())) {
            dto.getAtIds().add(dto.getUserId());
        }

        System.out.println(StringEscapeUtils.escapeJava(dto.getContent()));
    }

    public static void clean(MessageDto dto) {
        dto.setContent(Jsoup.clean(dto.getContent(), new Whitelist()
                .addTags("span", "a")
                .addAttributes("span", "data-before", "data-class")
                .addAttributes("a", "data-before")));
    }

}
