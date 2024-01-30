package service;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.PostDao;
import model.post;
import oracle.jdbc.proxy.annotation.Post;
import model.BoardWrite; // ������ �� Ŭ����
import model.SearchCriteria;


@Service
public class PostService {
    
    @Autowired
    private PostDao postDao; // PostDao�� �����մϴ�.
    

  
    
    //write poast �Լ� 
    public void writePost(String memName, String memId, String boardSubject, String boardContent) {
    	System.out.println("����Ʈ �۾��� write poast �Ѿ���°� üũ �غ���");
        // �Խñ� �ۼ� ����
        BoardWrite newPost = new BoardWrite();
        newPost.setMem_name(memName);
        newPost.setMem_id(memId);
        newPost.setBoard_subject(boardSubject);
        newPost.setBoard_content(boardContent);
        newPost.setReg_date(new Date());
        
        // PostDao�� ����Ͽ� �Խñ��� �����ͺ��̽��� ����
        postDao.savePost(newPost); // "savePost"�� PostDao�� �߰��ؾ� �ϴ� �޼��� �̸��Դϴ�.
    }

    // ������Ʈ �Լ�
    public void updatePost(Integer seq, String memName, String memId, String boardSubject, String boardContent) {
        // seq�� �ش� ���� ������
        post existingPost = postDao.getPostBySeq(seq);

        // ������ ���� �����ϸ� ������Ʈ ����
        if (existingPost != null) {
            existingPost.setMem_name(memName);
            existingPost.setMem_id(memId);
            existingPost.setBoard_subject(boardSubject);
            existingPost.setBoard_content(boardContent);

            // ������ ������Ʈ
            existingPost.setUpt_date(new Date());

            // PostDao�� ����Ͽ� �����ͺ��̽��� ������Ʈ
            postDao.updatePost(existingPost); // "updatePost"�� PostDao�� �߰��ؾ� �ϴ� �޼��� �̸��Դϴ�.
        } else {
            // ������ ���� ������ ���� ó�� �Ǵ� �α� ���� ����
            System.out.println("�ش� ���� �������� �ʽ��ϴ�. �� ��ȣ: " + seq);
            // ���� ó�� �Ǵ� �α� ���� �߰��� �� �ֽ��ϴ�.
        }
    }

	public post getPostBySeq(int seq) {
		// TODO Auto-generated method stub
		 return postDao.getPostBySeq(seq);
	}

	public void deletePost(Long seq) {
	    postDao.deletePost(seq);
	    // TODO: ���� ó�� ���� ����ϼ���.
	}

	@Transactional
    public void deleteSelectedPosts(List<Long> selectedPosts) {
        for (Long postId : selectedPosts) {
            postDao.deleteSelectPost(postId);
        }
    }


	public List<post> searchPostsByTitle(String keyword) {
		// TODO Auto-generated method stub
		 return postDao.searchPostsByTitle(keyword);
	}

	public List<post> searchPostsByTitleAndAuthor(String keyword) {
		// TODO Auto-generated method stub
		return postDao.searchPostByTitleAndAuthor(keyword);
	}

	public List<post> searchPostsById(String keyword) {
		// TODO Auto-generated method stub
		return postDao.searchPostById(keyword);
	}
	
	/*
	
	//�˻� ���� �ڵ� �����
	public List<post> searchPosts(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return postDao.searchPost(searchMap);
	}
*/
	  public int getTotalPosts(String keyword, String searchOption) {
		    return postDao.getTotalPosts(keyword, searchOption);
		}


	// �˻� ���� �ڵ�
	  public List<post> searchPosts(SearchCriteria searchCriteria) {
	      return postDao.searchPost(searchCriteria);
	  }

	  public int getTotalPosts(SearchCriteria searchCriteria) {
	      return postDao.getTotalPosts(searchCriteria);
	  }

	  
	  


	
	
	
	
	  public List<post> getPostList() {
	    	System.out.println("����Ʈ ���� �Ѿ��");
	        return postDao.getAllPosts(); // PostDao�� ����Ͽ� �����ͺ��̽����� �Խñ� ����� �����ɴϴ�.
	    }

	  /*
	public List<post> getPostList(int page, int pageSize) {
		 int offset = (page - 1) * pageSize;
	        return postDao.getPostList(offset, pageSize);
	}

	public int getTotalPosts() {
		// TODO Auto-generated method stub
		 return postDao.getTotalPosts();
	}

	*/
	  
	  public List<Post> getPostList(int offset, int pageSize, String keyword, String SearchOption, String startDate, String endDate ) {
		    return postDao.getPostList(offset, pageSize, keyword, SearchOption, startDate, endDate);
		}

	


	


    

   
    
    
    
    
}
