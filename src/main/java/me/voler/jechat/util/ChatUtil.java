package me.voler.jechat.util;

import com.alibaba.fastjson.JSON;
import me.voler.jechat.dto.MessageDto;
import me.voler.jechat.dto.SystemMessageDto;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.Browser;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

public class ChatUtil {

    private static final Logger LOGGER = Logger.getLogger(ChatUtil.class);
    /**
     * 管理userId与ScriptSession的映射关系
     */
    private static WeakHashMap<String, ScriptSession> USERID_SCRIPTSESSION = new WeakHashMap<>(
            new HashMap<String, ScriptSession>());

    public void onPageLoad(String userId) {
        if (StringUtils.isEmpty(userId)) {
            LOGGER.warn("parameter 'uid' is empty");
            return;
        }

        Container container = ServerContextFactory.get().getContainer();
        ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
        manager.addScriptSessionListener(new ScriptSessionListener() {

            @Override
            public void sessionCreated(ScriptSessionEvent ev) {

            }

            @Override
            public void sessionDestroyed(ScriptSessionEvent ev) {
                HttpSession httpSession = WebContextFactory.get().getSession();
                String userId = (String) httpSession.getAttribute("uid");
                if (USERID_SCRIPTSESSION.remove(userId) != null) {
                    sendMessage(new SystemMessageDto(userId + " exit", new ArrayList<>(USERID_SCRIPTSESSION.keySet())));
                }
            }
        });

        ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        // 工厂方法get()返回WebContext实例，通过WebContext获取servlet参数
        // ScriptSession与HttpSession类似
        scriptSession.setAttribute("uid", userId);
        USERID_SCRIPTSESSION.put(userId, scriptSession);
        sendMessage(new SystemMessageDto(userId + " join in", new ArrayList<>(USERID_SCRIPTSESSION.keySet())));
    }

    public void onSendMessage(MessageDto dto) {
        if (!USERID_SCRIPTSESSION.containsKey(dto.getUserId())) {
            LOGGER.warn("parameter 'uid' is vaild");
            return;
        }

        // 支持一对一私聊
        MessageUtil.parse(dto);

        sendMessage(dto);
    }

    /**
     * 配合前端{@code window.onbeforeunload}使用时，交互有偏差
     * <p>
     * v0.2 重新使用
     *
     * @see org.directwebremoting.event.ScriptSessionListener#sessionDestroyed
     */
    public void onPageClose(String userId) {
        USERID_SCRIPTSESSION.remove(userId);
        sendMessage(new SystemMessageDto(userId + " exit", new ArrayList<>(USERID_SCRIPTSESSION.keySet())));
    }

    private void sendMessage(MessageDto dto) {
        final List<String> uids = dto.getAtIds();
        final String message = JSON.toJSONString(dto);

        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
            // 实现过滤器中的match()方法
            @Override
            public boolean match(ScriptSession session) {
                if (session.isInvalidated()) {
                    return false;
                } else {
                    String uid = (String) session.getAttribute("uid");
                    return CollectionUtils.isEmpty(uids) || uids.contains(uid);
                }
            }
        }, new Runnable() {

            private ScriptBuffer script = new ScriptBuffer();

            @Override
            public void run() {
                // 调用前端页面中定义的showMsg()方法，向用户发送消息
                script.appendCall("showMsg", message);
                Collection<ScriptSession> sessions = Browser.getTargetSessions();
                for (ScriptSession scriptSession : sessions) {
                    scriptSession.addScript(script);
                }
            }
        });
    }

}
