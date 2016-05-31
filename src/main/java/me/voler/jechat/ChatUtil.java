package me.voler.jechat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

import com.alibaba.fastjson.JSON;

public class ChatUtil {

	private static final Logger LOGGER = Logger.getLogger(ChatUtil.class);
	/** 管理userId与ScriptSession的映射关系 */
	private static WeakHashMap<String, ScriptSession> USERID_SCRIPTSESSION = new WeakHashMap<String, ScriptSession>(
			new HashMap<String, ScriptSession>());

	public void onPageLoad(String userId) {
		if (StringUtils.isEmpty(userId)) {
			LOGGER.error("parameter 'uid' is empty");
			return;
		}

		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		// 工厂方法get()返回WebContext实例，通过WebContext获取servlet参数
		// ScriptSession与HttpSession类似
		scriptSession.setAttribute("uid", userId);
		USERID_SCRIPTSESSION.put(userId, scriptSession);
		sendDialogueMessage(Collections.<String> emptyList(),
				new ChatODTO("系统", userId + "加入群聊...", USERID_SCRIPTSESSION.size()));

	}

	public void onSendMessage(ChatIDTO idto) {
		if (!USERID_SCRIPTSESSION.keySet().contains(idto.getUserId())) {
			LOGGER.error("parameter 'uid' is vaild");
			return;
		}

		// 支持一对一私聊
		List<String> atList = new ArrayList<String>();
		String content = idto.getContent();
		if (USERID_SCRIPTSESSION.keySet().contains(idto.getAt())) {
			atList.add(idto.getUserId());
			atList.add(idto.getAt());
			content = content + "<label>@" + idto.getAt() + "</label>";
		}

		sendDialogueMessage(atList, new ChatODTO(idto.getUserId(), content, USERID_SCRIPTSESSION.size()));
	}

	public void onPageClose(String userId) {
		USERID_SCRIPTSESSION.remove(userId);
		sendDialogueMessage(Collections.<String> emptyList(),
				new ChatODTO("系统", userId + "退出群聊...", USERID_SCRIPTSESSION.size()));
	}

	private void sendDialogueMessage(List<String> userIds, ChatODTO chatMessage) {
		final List<String> uids = userIds;
		final String message = JSON.toJSONString(chatMessage);

		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			// 实现过滤器中的match()方法
			@Override
			public boolean match(ScriptSession session) {
				if (session.isInvalidated() || session.getAttribute("uid") == null)
					return false;
				// 向所有有效用户发送消息
				else if (uids.size() == 0) {
					return true;
				} else {
					return (uids.contains(session.getAttribute("uid")));
				}
			}
		}, new Runnable() {

			private ScriptBuffer script = new ScriptBuffer();

			@Override
			public void run() {
				// 调用JSP中定义的showMessage()方法，向用户发送消息
				script.appendCall("showMsg", message);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}

}
