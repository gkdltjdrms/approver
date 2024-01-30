package com.mycompany.myapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import service.PostService; // �ùٸ� ��Ű�� ��θ� ���
import util.BoardStatusConverter;
import model.Board;
import model.History;
import model.SearchCriteria;
import model.User;
import model.post; // �ùٸ� ��Ű�� ��θ� ���
import oracle.jdbc.proxy.annotation.Post;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private PostService postService;
	
	private String uploadFolder;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate);
		
		return "home";
	}
	
	
			
			//search
			 @RequestMapping(value = "/search", method = RequestMethod.GET)
			 public String search(@RequestParam("id") String id,
			                      @RequestParam("searchType") String searchType,
			                      @RequestParam("searchKeyword") String searchKeyword,
			                      @RequestParam("approveStatus") String payOption,
			                      @RequestParam("startDate") String startDate,
			                      @RequestParam("endDate") String endDate,
			                      HttpSession session,
			                      Model model) {
			     // �˻� ���� ���� ����
				 User user = postService.getUserInfo(id);
				   String userRank = user.getRank();
			        List<Board> boardList = new ArrayList<>(); // Initialize with an empty list
			            // �ٸ� ����� �Խ��� ����� ������ (��: �Ϲ� ����)
			            boardList = postService.getBoardList(id, payOption, userRank, searchType, searchKeyword, startDate, endDate);
			            model.addAttribute("boardList", boardList);
				 
			     return "mainList";
			 }
			
			 //���� ȭ�� �������� 
			 @RequestMapping(value = "/boardList", method = RequestMethod.POST)
			 public String boardList(@RequestParam("id") String id,
					 Model model, HttpSession session) {
				   // �α��� ���� �� ������� �̸��� ������ ������
				    User user = postService.getUserInfo(id);
				  List<Board> boardList = new ArrayList<>(); // Initialize with an empty list
				   String payOption = null;
			        String searchType = null;
			        String searchKeyword = null;
			        String userRank = user.getRank();
			        String startDate = null;
			        String endDate = null;
				  // �ٸ� ����� �Խ��� ����� ������ (��: �Ϲ� ����)
		            boardList = postService.getBoardList(id, payOption, userRank, searchType, searchKeyword, startDate, endDate);
	
		        model.addAttribute("boardList", boardList);
		        System.out.println(boardList + "���������� üũ ���帮��Ʈ");
				 
				 return "redirect:/mainList";
				
			 }
			 

				//�α��� ������ �̵�
				@RequestMapping(value = "/payLogin", method = RequestMethod.GET)
				public String payLogin() {
					System.out.println("���⼭ �Ǵ°ǰ�? Ȩ��Ʋ�ѷ� ȣ��");
					return "payLogin"; // "payLogin.jsp"�� �̵�
				}
			 
				// �α��� ó��
				@RequestMapping(value = "/checkId", method = RequestMethod.POST)
				public String checkId(@RequestParam("id") String id,
				                      @RequestParam("pwd") String pwd,
				                      Model model,
				                      HttpSession session) {

				    // Service�� ���� ID�� ��й�ȣ Ȯ��
				    int userIdCount = postService.findId(id);
				    int passwordCount = postService.checkPassword(id, pwd);

				    // ��ϵ��� ���� ������� ���
				    if (userIdCount == 0) {
				        model.addAttribute("error", "��ϵ��� ���� ������Դϴ�.");
				        return "redirect:/payLogin"; // payLogin �������� �����̷�Ʈ
				    } else if (passwordCount == 0) {
				        model.addAttribute("error", "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				        return "redirect:/payLogin"; // payLogin �������� �����̷�Ʈ
				    }

				    // �α��� ���� �� ������� �̸��� ������ ������
				    User user = postService.getUserInfo(id);

				    if (user != null) {
				        // ����� ������ �ִٸ� ���ǿ� ����
				        session.setAttribute("loggedInUserId", id);
				        session.setAttribute("loggedInUserName", user.getMemName());
				        session.setAttribute("loggedInUserRank", user.getRank());
				    }

				    // ���⿡ �α��� ���� �� ó���ϴ� �ڵ带 �߰��ϼ���.
				    System.out.println("���θ���Ʈ��22");
				    return "redirect:/mainList"; 
				}
			 
				
				// ���� ������ �̵�
				@RequestMapping(value = "/mainList", method = RequestMethod.GET)
				public String mainList(HttpServletRequest request, Model model) {
				    System.out.println("���⼭ �Ǵ°ǰ�? Ȩ��Ʈ�ѷ� ȣ��");

				    // ���ǿ��� id ���� ������
				    String id = (String) request.getSession().getAttribute("loggedInUserId");

				    // ���ǿ� id�� ������ �α��� �������� �����̷�Ʈ
				    if (id == null) {
				        // ���⿡�� alert �޽����� �����ϴ� ����� �����ϴ�.
				        // JavaScript�� ó���ϰų�, ��� â�� ��� ����ڿ��� �˸��� �� �� �ֽ��ϴ�.
				        return "redirect:/payLogin";
				    }
				    User user = postService.getUserInfo(id);

				    String payOption = null;
				    String searchType = null;
				    String searchKeyword = null;
				    String userRank = user.getRank();
				    String startDate = null;
				    String endDate = null;
				    List<Board> boardList = new ArrayList<>(); // Initialize with an empty list

				    // �ٸ� ����� �Խ��� ����� ������ (��: �Ϲ� ����)
				    boardList = postService.getBoardList(id, payOption, userRank, searchType, searchKeyword, startDate, endDate);
				    // �Խñ��� �� checkName�� �ش��ϴ� �̸��� �����ͼ� �𵨿� �߰�
				    
				    
				    System.out.println(id + userRank + "rank���� ������ Ȯ��");
				    model.addAttribute("boardList", boardList);
				    System.out.println(boardList + "���������� üũ ���帮��Ʈ");

				    return "mainList";
				}

			 
			 
			 
			 
			
			//update AJAX
			@RequestMapping(value = "/searchoption", method = RequestMethod.GET)
			@ResponseBody
			public List<Board> searchoption(
			    @RequestParam("id") String id,
			    @RequestParam("approveStatus") String payOption,
			    @RequestParam(value = "searchType", required = false) String searchType, // Added searchType parameter
			    @RequestParam(value = "searchKeyword", required = false) String searchKeyword, // Added searchKeyword parameter
			    @RequestParam("startDate") String startDate,
                @RequestParam("endDate") String endDate,
			    HttpSession session,
			    Model model) {
			    System.out.println("�˻��Ϸ� ��Ʈ�ѷ� ��");
			    User user = postService.getUserInfo(id);
			    String userRank = user.getRank();

			    // You can use searchType and searchKeyword as needed

			    List<Board> boardList = postService.getBoardList(id, payOption, userRank, searchType, searchKeyword, startDate, endDate);
			    
			    // ������¸� �ѱ۷� ��ȯ�Ͽ� �����Ϳ� �߰�
			    for (Board board : boardList) {
			        board.setPayOption(BoardStatusConverter.getKoreanStatus(board.getPayOption()));
			    }

	
			    System.out.println(boardList);
			    return boardList;
			}

			@RequestMapping(value = "/detail", method = RequestMethod.GET)
			public String detail(@RequestParam("seq") int seq, Model model) {
			    // �Խñ� ���� ��ȸ
			    Board board = postService.getBoardBySeq(seq);
			    
			    // �����丮 ���� ��ȸ (����, ���� ������ ��ȸ �ڵ忡 �°� ���� �ʿ�)
			    List<History> historyList = postService.getHistoryListByBoardSeq(seq);

			    // �𵨿� �Խñ� �� �����丮 ���� �߰�
			    model.addAttribute("board", board);
			    model.addAttribute("historyList", historyList);

			    // �� �̸� ��ȯ
			    return "detail";
			}
			
			@RequestMapping(value = "/reject", method = RequestMethod.GET)
			public String reject(@RequestParam("userId") String userId,
							@RequestParam("seq") int seq,
							@RequestParam("userRank") String userRank,
					Model model) {
			  
				System.out.println(userId+"�α��� ���̵� üũ");
			    // �� �̸� ��ȯ
			    return "mainList";
			}
			


			//logOut
			@RequestMapping(value = "/logout", method = RequestMethod.POST)
			public String logout(HttpSession session) {
				System.out.println("logout@@@@@@@@@@@@@@@@@@@@");
			    // Invalidate the session to log the user out
			    session.invalidate();
			    // Redirect to the login page or any other desired page
			    return "redirect:/payLogin";
			}
			
			
			@RequestMapping(value = "/payWriteForm", method = RequestMethod.GET)
			public String payWriteForm(Model model,
					HttpSession session,
			        @RequestParam("id") String loggedInUserId,
			        @RequestParam("userName") String loggedInUserName,
			        @RequestParam("userRank") String loggedInUserRank) {

			    if (loggedInUserId != null) {
			    	   int seqNum = postService.findSeq();
			    	    model.addAttribute("seqNum", seqNum);
			    		System.out.println(seqNum);
						model.addAttribute("seqNum", seqNum);
						System.out.println("Model: " + model.asMap());

			    }else {
			    	  return "redirect:/payLogin";
			    }

			    // �۾��� �������� �̵�
			    return "payWriteForm";
			}
			
			//������Ʈ
			@RequestMapping(value = "/updateBoard", method = RequestMethod.POST)
			public String updateBoard(
					 @RequestParam("action") String action, // �߰��� �κ�
					 @RequestParam("seq") int seq,
					  @RequestParam("id") String id,
					  @RequestParam("payOption") String payOption,
				        @RequestParam("subject") String subject,
				        @RequestParam("content") String content,
				        HttpSession session,
				        Model model) {
				switch (action) {
			    case "reject":
			        // �ݷ� ��ư�� ������ ���� ó��
			        // ...
			        break;
			    case "approval":
			        if ("save".equals(payOption)) {
			            // ���� ��ư�� ������ ���� ó��
			            postService.updateBoard(seq, id, subject, content);
			            int PostSeq = seq;
			            System.out.println(PostSeq+"seqüũ");
			            postService.submitHistory(id, PostSeq);
			            System.out.println("update �Ϸ�222 "+PostSeq);
			        } else {
			          
			            System.out.println("update �Ϸ�ƴ� ");
			        }
			        break;
			    case "save":
			      
			        break;
			}

				
				return "redirect:/mainList"; // ������ URL�� ����
			}
					
			//submit
			@RequestMapping(value = "/submitWriteForm", method = RequestMethod.POST)
			public String submitWriteForm(
			        @RequestParam("id") String id,
			        @RequestParam("subject") String subject,
			        @RequestParam("content") String content,
			        @RequestParam("payOption") String payOption, // Add this line for payOption
			        HttpSession session,
			        Model model) {
			    System.out.println("�Ѿ������ Ȯ��");
			    if ("save".equals(payOption)) {
			        // Your logic for handling 'save' option goes here
			    	 postService.savesubmitWriteForm(id, subject, content);
			    	
			    }
			    else {
			        // postService.submitWriteForm �޼��忡 �ʿ��� ���ڵ��� �����Ͽ� ȣ��
			    	int PostSeq = postService.submitWriteForm(id, subject, content);
				    postService.submitHistory(id, PostSeq);
			    }
		
			
			    // Do something with the payOption parameter (e.g., save it to the database)

			    // �α��� ���� �� ������� �̸��� ������ ������
			    User user = postService.getUserInfo(id);
			    String userRank = user.getRank();
			    if (user != null) {
			        // ����� ������ �ִٸ� ���ǿ� ����
			        session.setAttribute("loggedInUserId", id);
			        session.setAttribute("loggedInUserName", user.getMemName());
			        session.setAttribute("loggedInUserRank", user.getRank());
			        
			        String searchType = null;
			        String searchKeyword = null;
			        		payOption = null;
			        String startDate = null;
			        String endDate = null;
			        List<Board> boardList = postService.getBoardList(id, payOption, userRank, searchType, searchKeyword, startDate, endDate);
			        model.addAttribute("boardList", boardList);
			        System.out.println(boardList + "���������� üũ ���帮��Ʈ");
			    }

			    return "mainList"; // ������ URL�� ����
			}


			
			

}
