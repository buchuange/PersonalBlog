package com.dawn.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dawn.mapper.CommentMapper;
import com.dawn.model.Comment;
import com.dawn.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Service(interfaceClass = CommentService.class, version = "1.0.0", timeout = 15000)
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentVO> listCommentByBlogId(Integer blogId) {

        // 获取所有一级评论
        List<CommentVO> topComment = commentMapper.listTopComment(blogId);

        // 获取所有子评论
        for(CommentVO vo : topComment) {
            vo.setReplyComments(commentMapper.listTreeComment(vo.getComment().getId()));
        }

        return eachComment(topComment);
    }

    // 循环遍历顶级评论，并放入commentView中
    private List<CommentVO> eachComment(List<CommentVO> comments) {
        List<CommentVO> commentView = new ArrayList<>();
        for (CommentVO comment : comments) {
            CommentVO c = new CommentVO();
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        //合并评论的各层子代到一级子代中
        combineChildren(commentView);
        return commentView;
    }

    // 存放迭代找出的所有子评论的集合
    List<CommentVO> tempReplys = new ArrayList<>();

    // 进行所有子评论的遍历，将所有子评论合并到一个集合中
    private void combineChildren(List<CommentVO> topComment) {
        for (CommentVO vo : topComment) {
            List<CommentVO> replyComments = vo.getReplyComments();
            for (CommentVO commentVO : replyComments) {
                log.info("--******----" + commentVO.getComment().getId());
                //循环迭代找出子代，存放在tampReplys中
                recursively(commentVO);
            }

            //修改顶级节点的reply集合为迭代后的集合
            vo.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    private void recursively(CommentVO commentVO) {
        tempReplys.add(commentVO);
        log.info("------" + commentVO.getComment().getId());
        if (commentVO.getReplyComments().size() > 0) {
            List<CommentVO> replys = commentVO.getReplyComments();
            for (CommentVO reply : replys) {
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                } else {
                    tempReplys.add(reply);
                }
            }
        }
    }


    @Override
    public boolean saveComment(Comment comment) {

        boolean flag = true;

        comment.setCreateTime(new Date());
        int count = commentMapper.insertSelective(comment);

        if (count != 1) {
            flag = false;
        }
        return flag;
    }
}
