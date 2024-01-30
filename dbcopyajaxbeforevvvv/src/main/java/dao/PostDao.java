package dao;

import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.session.SqlSession;

import model.BoardWrite;
import model.SearchCriteria;
import model.post;
import oracle.jdbc.proxy.annotation.Post;

@Repository
public class PostDao {
    
    @Autowired
    private SqlSession sqlSession; // SqlSession�� �����մϴ�.

    @Autowired
    private JdbcTemplate jdbcTemplate; // �����ͺ��̽��� ��ȣ �ۿ��ϴ� JdbcTemplate�� �����մϴ�.




	/*
	 * public void savePost(BoardWrite newPost) { // SQL ������ �ۼ� (���������� ���� seq �� �����ͼ�
	 * 1 ����) String sql =
	 * "INSERT INTO board_study (seq, mem_name, mem_id, board_subject, board_content, reg_date) "
	 * + "VALUES ((SELECT MAX(seq) FROM board_study) + 1, ?, ?, ?, ?, ?)";
	 * 
	 * String memName = newPost.getMem_name(); String memId = newPost.getMem_id();
	 * String boardSubject = newPost.getBoard_subject(); String boardContent =
	 * newPost.getBoard_content(); Date regDate = newPost.getReg_date();
	 * 
	 * // �Խñ��� �����ͺ��̽��� ���� jdbcTemplate.update(sql, memName, memId, boardSubject,
	 * boardContent, new java.sql.Date(regDate.getTime())); }
	 */
    
    public void savePost(BoardWrite newPost) {
        sqlSession.insert("mapper.BoardMapper.savePost", newPost);
    }


	public void updatePost(post existingPost) {
		// TODO Auto-generated method stub
		sqlSession.update("updatePost", existingPost);
	}


	public post getPostBySeq(int seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.BoardMapper.getPostBySeq", seq);
	}


	public void deletePost(Long seq) {
		 sqlSession.delete("mapper.BoardMapper.deletePost", seq);
	        // TODO: ���� ó�� ���� ����ϼ���.
	}


	public void deleteSelectPost(Long postId) {
		// TODO Auto-generated method stub
		 sqlSession.delete("mapper.BoardMapper.deletePost", postId);
		
	}


	public List<Post> searchPosts(String keyword) {
		
	    return sqlSession.selectList("mapper.BoardMapper.searchPosts", keyword);
	}

   
	
	public List<post> searchPostsByTitle(String keyword) {
	    System.out.println(keyword + " Ű���� ������ Ȯ��");
	    // TODO Auto-generated method stub
	    Map<String, Object> parameter = new HashMap<>();
	    parameter.put("keyword", keyword);
	    return sqlSession.selectList("mapper.BoardMapper.searchPostsByTitle", parameter);
	}


	public List<post> searchPostByTitleAndAuthor(String keyword) {
		  System.out.println(keyword + " Ű���� ������ Ȯ�� �̰��� ����++");
		// TODO Auto-generated method stub
		 Map<String, Object> parameter = new HashMap<>();
		    parameter.put("keyword", keyword);
		    return sqlSession.selectList("mapper.BoardMapper.searchPostsByTitleAndAuthor", parameter);
	}


	public List<post> searchPostById(String keyword) {
		  System.out.println(keyword + " Ű���� ������ Ȯ�� �̰��� �ۼ��ڸ�");
			// TODO Auto-generated method stub
			 Map<String, Object> parameter = new HashMap<>();
			    parameter.put("keyword", keyword);
			    return sqlSession.selectList("mapper.BoardMapper.searchPostsById", parameter);
	}
	
/*
	public List<post> getPostList(int offset, int pageSize) {
		System.out.println(offset + "||" + pageSize + "���� üŷ�� ��ġȮ��");

		  Map<String, Object> params = new HashMap<>();
	        params.put("startRow", offset);
	        params.put("endRow", offset + pageSize);
	        return sqlSession.selectList("mapper.BoardMapper.getPostList", params);
	        
	}


	public int getTotalPosts() {
		// TODO Auto-generated method stub
		  return sqlSession.selectOne("mapper.BoardMapper.getTotalPosts");
	}
*/
	
	public List<Post> getPostList(int page, int pageSize, String keyword, String searchOption, String startDate, String endDate) {
	    int startRow = (page - 1) * pageSize + 1;
	    int endRow = page * pageSize;

	    Map<String, Object> params = new HashMap<>();
	    params.put("startRow", startRow);
	    params.put("endRow", endRow);
	    params.put("keyword", keyword);
	    params.put("searchOption", searchOption);
	    params.put("startDate", startDate);
	    params.put("endDate", endDate);

	    System.out.println(startRow + " || " + endRow + " ���� üŷ map");

	    return sqlSession.selectList("mapper.BoardMapper.getPostList", params);
	}






	
	


	/*
	public List<post> searchPost(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		 return sqlSession.selectList("mapper.BoardMapper.searchPosts", searchMap);
	}
	*/
	public int getTotalPosts(String keyword, String searchOption) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("searchOption", searchOption);
	    return sqlSession.selectOne("mapper.BoardMapper.getTotalPosts", params);
	}

	// postDao�� �޼��� ����
	public List<post> searchPost(SearchCriteria searchCriteria) {
		System.out.println(searchCriteria.getKeyword()+"���� ���� Ű����");
	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", searchCriteria.getKeyword());
	    // ������ �˻� ���ǿ� ���� ó��
	    return sqlSession.selectList("mapper.BoardMapper.searchPost", params);
	}

	public int getTotalPosts(SearchCriteria searchCriteria) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", searchCriteria.getKeyword());
	    // ������ �˻� ���ǿ� ���� ó��
	    return sqlSession.selectOne("mapper.BoardMapper.getTotalPosts", params);
	}

	 public List<post> getAllPosts() {
	        System.out.println("dao �������� ���⿡��  search �� ���鿹�� �Ѿ��");
	        return sqlSession.selectList("mapper.BoardMapper.getPost");
	    }
	

}
