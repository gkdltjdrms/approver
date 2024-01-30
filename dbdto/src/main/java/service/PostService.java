package service;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.PostDao;
import model.post;
import model.BoardWrite; // ������ �� Ŭ����


@Service
public class PostService {
    
    @Autowired
    private PostDao postDao; // PostDao�� �����մϴ�.

    public List<post> getPostList() {
    	System.out.println("����Ʈ ���� �Ѿ��");
        return postDao.getAllPosts(); // PostDao�� ����Ͽ� �����ͺ��̽����� �Խñ� ����� �����ɴϴ�.
    }
    
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

	public post getPostBySeq(int seq) {
		// TODO Auto-generated method stub
		 return postDao.getPostBySeq(seq);
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
    
    

   
    
    
    
    
}
