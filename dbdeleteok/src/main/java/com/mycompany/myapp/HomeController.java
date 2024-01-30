package com.mycompany.myapp;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




import service.PostService; // �ùٸ� ��Ű�� ��θ� ���
import model.post; // �ùٸ� ��Ű�� ��θ� ���

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate);
		
		return "home";
	}
	
	//�۾��� ������ �̵�
	@RequestMapping(value = "/goToWrite", method = RequestMethod.GET)
	public String goToWrite() {
		System.out.println("���⼭ �Ǵ°ǰ�? Ȩ��Ʋ�ѷ� ȣ��");
		return "write"; // "write.jsp"�� �̵�
	}
	
	@RequestMapping(value = "/listinfo", method = RequestMethod.GET)
	public String goToPost(@RequestParam("seq") int seq, Model model) {
	    // seq�� ����Ͽ� �ش� ���� �������� ���� �߰�
	    post post = postService.getPostBySeq(seq);
	    model.addAttribute("post", post);
	    return "listinfo"; // "listinfo.jsp"�� �̵�
	}
	
	
	//�ۼ�or����
		@RequestMapping(value = "/writeOrUpdatePost", method = RequestMethod.POST)
		public String writeOrUpdatePost(@RequestParam("memName") String memName,
		                                @RequestParam("memId") String memId,
		                                @RequestParam("boardSubject") String boardSubject,
		                                @RequestParam("boardContent") String boardContent,
		                                @RequestParam(value = "seq", required = false) Integer seq,
		                                Model model) {
		    if (seq == null) {
		        // seq�� null�̸� ���ο� �� �ۼ�
		        postService.writePost(memName, memId, boardSubject, boardContent);
		    } else {
		        // seq�� �����ϸ� �ش� seq�� �� ������Ʈ
		        postService.updatePost(seq, memName, memId, boardSubject, boardContent);
		    }

		    // �ۼ� �Ǵ� ������Ʈ �Ϸ� �� ����Ʈ �������� �����̷�Ʈ
		    return "redirect:/goToList";
		}

		
		// �� ����
		@RequestMapping(value = "/deletePost", method = RequestMethod.POST)
		public String deletePost(@RequestParam("seq") Long seq) {
		    // Assuming you have a service to handle post deletion
		    postService.deletePost(seq);
		    // Redirect to the post list page after deletion
		    return "redirect:/goToList";
		}



	
	
	@RequestMapping(value = "/goToList", method = RequestMethod.GET)
	public String goToList(Model model) {
	
		List<post> postList = postService.getPostList();
		model.addAttribute("listOfPosts", postList);
		return "list";
	}
}
