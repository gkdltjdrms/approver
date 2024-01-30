package dao;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.apache.ibatis.session.SqlSession;

import model.BoardWrite;
import model.post;

@Repository
public class PostDao {
    
    @Autowired
    private SqlSession sqlSession; // SqlSession�� �����մϴ�.

    @Autowired
    private JdbcTemplate jdbcTemplate; // �����ͺ��̽��� ��ȣ �ۿ��ϴ� JdbcTemplate�� �����մϴ�.

    public List<post> getAllPosts() {
        System.out.println("dao �Ѿ��");
        return sqlSession.selectList("mapper.BoardMapper.getPost");
    }


    public void savePost(BoardWrite newPost) {
        // SQL ������ �ۼ� (���������� ���� seq �� �����ͼ� 1 ����)
        String sql = "INSERT INTO board_study (seq, mem_name, mem_id, board_subject, board_content, reg_date) " +
                     "VALUES ((SELECT MAX(seq) FROM board_study) + 1, ?, ?, ?, ?, ?)";

        String memName = newPost.getMem_name();
        String memId = newPost.getMem_id();
        String boardSubject = newPost.getBoard_subject();
        String boardContent = newPost.getBoard_content();
        Date regDate = newPost.getReg_date();

        // �Խñ��� �����ͺ��̽��� ����
        jdbcTemplate.update(sql, memName, memId, boardSubject, boardContent, new java.sql.Date(regDate.getTime()));
    }


	public void updatePost(post existingPost) {
		// TODO Auto-generated method stub
		sqlSession.update("updatePost", existingPost);
	}


	public post getPostBySeq(int seq) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("mapper.BoardMapper.getPostBySeq", seq);
	}

}
