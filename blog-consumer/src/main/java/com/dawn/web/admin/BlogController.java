package com.dawn.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.model.Blog;
import com.dawn.model.Tag;
import com.dawn.model.Type;
import com.dawn.model.User;
import com.dawn.service.BlogService;
import com.dawn.service.TagService;
import com.dawn.service.TypeService;
import com.dawn.vo.PaginationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Reference(interfaceClass = BlogService.class, version = "1.0.0", timeout = 15000)
    private BlogService blogService;

    @Reference(interfaceClass = TypeService.class, version = "1.0.0", timeout = 15000)
    private TypeService typeService;

    @Reference(interfaceClass = TagService.class, version = "1.0.0", timeout = 15000)
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(Integer pageNum, Model model, Blog blog) {

        if (pageNum == null) {
            pageNum = 1;
        }
        int pageSize = 4;

        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);

        PaginationVO<Blog> vo = blogService.listBlog(pageNum, pageSize, blog);
        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "admin/blogs";
    }

    @RequestMapping("/blogs/search")
    public String search(Integer pageNum, Model model, Blog blog) {
        
        if (pageNum == null) {
            pageNum = 1;
        }
        int pageSize = 4;

        PaginationVO<Blog> vo = blogService.listBlog(pageNum, pageSize, blog);
        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "admin/blogs :: blogList";
    }


    @GetMapping("/blogs/input")
    public String blogInput(Model model) {

        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);

        List<Tag> tagList = tagService.getAllTag();
        model.addAttribute("tagList", tagList);

        model.addAttribute("blog", new Blog());


        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable("id") Integer id, Model model) {

        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);

        List<Tag> tagList = tagService.getAllTag();
        model.addAttribute("tagList", tagList);

        Integer[] tagIds = tagService.getTagIdsByBlogId(id);

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < tagIds.length; i++) {
            str.append(tagIds[i]);
            if (i < tagIds.length - 1) str.append(",");
        }

        Blog blog = blogService.getBlogById(id);

        model.addAttribute("blog", blog);
        model.addAttribute("tagIds", str);

        return "admin/blogs-input";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteTypes(@PathVariable("id") Integer id, RedirectAttributes attributes) {

        boolean flag = blogService.deleteBlog(id);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，删除一篇博客！");
        } else {
            attributes.addFlashAttribute("message", "操作失败");
        }

        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes, Integer[] tagIds) {

        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        boolean flag = blogService.saveBlog(blog, tagIds);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，新增一篇博客！");
        } else {
            attributes.addFlashAttribute("message", "操作失败，提交信息不符合规则");
            return "redirect:/admin/blogs/input";
        }

        return "redirect:/admin/blogs";
    }

    @PostMapping("/blogs/{id}")
    public String updateBlog(Blog blog, RedirectAttributes attributes, Integer[] tagIds) {

        boolean flag = blogService.updateBlog(blog, tagIds);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，修改一篇博客！");
        } else {
            attributes.addFlashAttribute("message", "操作失败，提交信息不符合规则");
            return "redirect:/admin/blogs/input";
        }

        return "redirect:/admin/blogs";
    }
}
