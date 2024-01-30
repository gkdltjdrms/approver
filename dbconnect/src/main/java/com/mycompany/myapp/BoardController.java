package com.mycompany.myapp;

import model.BoardPost;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BoardController {

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String showWriteForm(Model model) {
        return "write"; // write.jsp�� ��� ���
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writeBoard(BoardPost boardPost) {
        // boardPost ��ü�� �Էµ� �����Ͱ� ���޵�
        // ���⿡�� �����ͺ��̽��� �����ϴ� ������ �߰�
        // ���� �� Ȩ�������� �����̷�Ʈ �Ǵ� �ٸ� �۾� ����
        return "redirect:/"; // Ȩ�������� �����̷�Ʈ
    }
}

