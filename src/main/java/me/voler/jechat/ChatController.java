package me.voler.jechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatController {

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ModelAndView exec(@PathVariable("userId") String userId) {
		ModelAndView mv = new ModelAndView("chat/chat");
		mv.addObject("uid", userId);
		return mv;

	}

}
