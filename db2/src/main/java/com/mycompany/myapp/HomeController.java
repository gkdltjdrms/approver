package com.mycompany.myapp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.PostService; // �ùٸ� ��Ű�� ��θ� ���
import model.SearchCriteria;
import model.post; // �ùٸ� ��Ű�� ��θ� ���
import oracle.jdbc.proxy.annotation.Post;


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
			System.out.println("writeorupdatecontroller ��");
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

	
	
	
	/*
	 * @RequestMapping(value = "/goToSearch", method = RequestMethod.GET) public
	 * String goToSearch(@RequestParam(value = "keyword") String keyword,
	 * 
	 * @RequestParam(value = "searchOption", defaultValue = "all") String
	 * searchOption, Model model) { List<post> searchResults = new ArrayList<>();
	 * 
	 * switch (searchOption) { case "title":
	 * System.out.println("�ϴ� ��Ʈ�ѷ� search ���� ���񽺷� �̵���Ű����"); searchResults =
	 * postService.searchPostsByTitle(keyword); break; case "titleAndAuthor":
	 * searchResults = postService.searchPostsByTitleAndAuthor(keyword); break; case
	 * "id": searchResults = postService.searchPostsById(keyword); break; default:
	 * // searchResults = postService.searchPosts(keyword); break; }
	 * 
	 * 
	 * model.addAttribute("listOfPosts", searchResults);
	 * 
	 * return "list"; }
	 */

		/*
	@RequestMapping(value = "/goToSearch", method = RequestMethod.GET)
	public String goToSearch(@RequestParam(value = "keyword") String keyword,
	                         @RequestParam(value = "searchOption", defaultValue = "all") String searchOption,
	                         @RequestParam(value = "startDate", required = false) String startDate,
	                         @RequestParam(value = "endDate", required = false) String endDate,
	                         Model model) {
	    Map<String, Object> searchMap = new HashMap<>();
	    searchMap.put("keyword", keyword);
	    searchMap.put("searchOption", searchOption);
	    searchMap.put("startDate", startDate);
	    searchMap.put("endDate", endDate);

	    List<post> searchResults = postService.searchPosts(searchMap);

	    model.addAttribute("listOfPosts", searchResults);

	    return "list";
	}
	*/


	
	

		
		
		
	
	  //����Ʈ �߷�
	  /*
	  @RequestMapping(value = "/goToList", method = RequestMethod.GET) public
	  String goToList(Model model) {
	  
	  List<post> postList = postService.getPostList();
	  model.addAttribute("listOfPosts", postList);
	  return "list"; 
	  }
	  */
	
	  /*
	@RequestMapping(value = "/goToList", method = RequestMethod.GET)
	public String goToList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int pageSize, Model model) {
	    List<post> postList = postService.getPostList(page, pageSize);
	    int totalPosts = postService.getTotalPosts();

	    int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

	    model.addAttribute("listOfPosts", postList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", totalPages);

	    return "list";
	}
	*/
		
		
	@RequestMapping(value = "/goToSearch", method = RequestMethod.GET)
	public String goToSearch(@ModelAttribute SearchCriteria searchCriteria, Model model) {
		List<post> searchResults = postService.searchPosts(searchCriteria);
		int totalPosts = postService.getTotalPosts(searchCriteria);

		int totalPages = (int) Math.ceil((double) totalPosts / searchCriteria.getPageSize());

		model.addAttribute("listOfPosts", searchResults);
		model.addAttribute("currentPage", searchCriteria.getPage());
		model.addAttribute("totalPages", totalPages);

		return "list";
	}

	@RequestMapping(value = "/goToList", method = RequestMethod.GET)
	public String goToList(@ModelAttribute SearchCriteria searchCriteria, Model model) {
		System.out.println("�׳ɸ���Ʈ");
	    List<Post> postList = postService.getPostList(
	            searchCriteria.getPage(),
	            searchCriteria.getPageSize(),
	            searchCriteria.getKeyword(),
	            searchCriteria.getSearchOption(),
	            searchCriteria.getStartDate(),
	            searchCriteria.getEndDate()
	    );

	    int totalPosts = postService.getTotalPosts(
	            searchCriteria.getKeyword(),
	            searchCriteria.getSearchOption()
	    );

	    System.out.println("22");
	    model.addAttribute("currentPage", searchCriteria.getPage());
	    int totalPages = (int) Math.ceil((double) totalPosts / searchCriteria.getPageSize());

	    model.addAttribute("listOfPosts", postList);
	    model.addAttribute("totalPages", totalPages);

	    return "list";
	}

	
	
	@RequestMapping(value = "/searchgo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, Object> searchgo(
	        @ModelAttribute SearchCriteria searchCriteria, Model model) {
	    System.out.println("�ϴ� ajax ��Ʈ�ѷ� ��");
	    
	   
	    Map<String, Object> searchgo = new HashMap<>();
	    List<Post> postList = postService.getPostList(
	            searchCriteria.getPage(),
	            searchCriteria.getPageSize(),
	            searchCriteria.getKeyword(),
	            searchCriteria.getSearchOption(),
	            searchCriteria.getStartDate(),
	            searchCriteria.getEndDate()
	    );
	    int totalPosts = postService.getTotalPosts(
	            searchCriteria.getKeyword(),
	            searchCriteria.getSearchOption()
	    );
	    int totalPages = (int) Math.ceil((double) totalPosts / searchCriteria.getPageSize());
	    System.out.println("������ �Է���");
		/* response.put("currentPage", searchCriteria.getPage()); */
	    searchgo.put("listOfPosts", postList);
		/* response.put("totalPages", totalPages); */
	    System.out.println("������ �Է¿Ϸ�");
	    System.out.println(searchgo);
	    return searchgo;
	}




	
	
	  
	

	 
	
	



	
	
	//���� �������� �̵�
			@RequestMapping(value = "/goDelete", method = RequestMethod.GET)
			public String goDelete(Model model) {
				System.out.println("�ϴ� delete.jsp �� �̵��ϴ� ��Ʈ�ѷ� ");
				List<post> postList = postService.getPostList();
				model.addAttribute("listOfPosts", postList);
				return "delete"; // "delete.jsp"�� �̵�
			}
			
			@RequestMapping(value = "/deleteSelectedPosts", method = RequestMethod.POST)
			public String deleteSelectedPosts(@RequestParam(value = "selectedPosts", required = false) List<Long> selectedPosts, Model model) {
			    if (selectedPosts == null || selectedPosts.isEmpty()) {
			        // ���õ� �׸��� ���� ���, �޽����� �߰�
			        model.addAttribute("message", "üũ�� �׸��� �����ϴ�. �����Ϸ��� �׸��� �����ϼ���.");
			        return "goToList";
			    }

			    // ���õ� �׸��� �ִ� ���, ���� ����
			    postService.deleteSelectedPosts(selectedPosts);

			    return "redirect:/goToList";
			}

			
	
	
}
