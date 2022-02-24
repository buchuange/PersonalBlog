package com.dawn.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.model.Blog;
import com.dawn.model.Tag;
import com.dawn.model.Type;
import com.dawn.model.User;
import com.dawn.service.*;
import com.dawn.utils.MarkdownUtils;
import com.dawn.vo.CommentVO;
import com.dawn.vo.IndexVO;
import com.dawn.vo.PaginationVO;
import com.dawn.vo.TopVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IndexController {

    @Reference(interfaceClass = BlogService.class, version = "1.0.0", timeout = 15000)
    private BlogService blogService;

    @Reference(interfaceClass = TypeService.class, version = "1.0.0", timeout = 15000)
    private TypeService typeService;

    @Reference(interfaceClass = TagService.class, version = "1.0.0", timeout = 15000)
    private TagService tagService;

    @Reference(interfaceClass = UserService.class, version = "1.0.0", timeout = 15000)
    private UserService userService;


    @GetMapping("/")
    public String index(Integer pageNum, Model model) {


        if (pageNum == null) {
            pageNum = 1;
        }
        int pageSize = 4;

        List<TopVO> typeTop = typeService.listTypeTop(6);
        List<TopVO> tagTop = tagService.listTagTop(7);

        List<Blog> recommendBlogs = blogService.getRecommendBlogs(5);

        PaginationVO<IndexVO> vo = blogService.getBlogList(pageNum, pageSize);

        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("typeTop", typeTop);
        model.addAttribute("tagTop", tagTop);
        model.addAttribute("recommendBlogs", recommendBlogs);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "index";
    }

    @PostMapping("/search")
    public String search(Integer pageNum, String query, Model model) {


        if (pageNum == null) {
            pageNum = 1;
        }
        int pageSize = 4;


        PaginationVO<IndexVO> vo = blogService.getSearchBlogs(pageNum, pageSize, query);

        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("query", query);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "search";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Integer id, Model model) {

        Blog blog = blogService.increaseViews(id);


        String content = blog.getContent();
        String contentHtml = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(contentHtml);

        User user = userService.getUserInfo(blog.getUserId());
        String[] tagsName = tagService.getTagsByBlogId(id);

        model.addAttribute("blog", blog);
        model.addAttribute("user", user);
        model.addAttribute("tagsName", tagsName);

        return "blog";
    }

    @GetMapping("/footer/newBlogs")
    public String newBlogs(Model model) {

        List<Blog> recommendBlogs = blogService.getRecommendBlogs(3);
        model.addAttribute("newBlogs", recommendBlogs);

        return "_fragments :: newBlogList";
    }
}
