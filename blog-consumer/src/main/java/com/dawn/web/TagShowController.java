package com.dawn.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.service.BlogService;
import com.dawn.service.TagService;
import com.dawn.vo.IndexVO;
import com.dawn.vo.PaginationVO;
import com.dawn.vo.TopVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Reference(interfaceClass = TagService.class, version = "1.0.0", check = false)
    private TagService tagService;

    @Reference(interfaceClass = BlogService.class, version = "1.0.0", timeout = 15000)
    private BlogService blogService;

    @GetMapping("/tags/{id}/{pageNum}")
    public String types(@PathVariable("id") Integer id,
                        @PathVariable("pageNum") Integer pageNum, Model model) {


        int pageSize = 2;

        List<TopVO> tagTop = tagService.listTagTop(106);

        if (id == -1) {
            id = tagTop.get(0).getId();
        }
        PaginationVO<IndexVO> vo = blogService.listTagBlogs(pageNum, pageSize, id);

        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("tagTop", tagTop);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("activeTagId", id);

        return "tags";
    }
}
