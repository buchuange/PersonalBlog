package com.dawn.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.model.Comment;
import com.dawn.model.User;
import com.dawn.service.CommentService;
import com.dawn.vo.CommentVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {


    @Reference(interfaceClass = CommentService.class, version = "1.0.0", timeout = 15000)
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Integer blogId, Model model) {

        List<CommentVO> vo = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", vo);

        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar("/images/elliot.jpg");
            comment.setAdminComment("1");
        } else {
            comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);

        return "redirect:/comments/" + comment.getBlogId();
    }
}
