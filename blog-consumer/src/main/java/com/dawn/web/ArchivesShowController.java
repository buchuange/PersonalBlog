package com.dawn.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.service.BlogService;
import com.dawn.vo.ArchivesVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArchivesShowController {

    @Reference(interfaceClass = BlogService.class, version = "1.0.0", timeout = 15000)
    private BlogService blogService;


    @GetMapping("/archives")
    public String archives(Model model) {

        List<ArchivesVO> vo = blogService.listArchivesBlogs();
        int total = blogService.countBlogs();

        model.addAttribute("page", vo);
        model.addAttribute("total", total);

        return "archives";
    }
}
