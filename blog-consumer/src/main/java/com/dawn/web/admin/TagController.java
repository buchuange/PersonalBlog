package com.dawn.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.model.Tag;
import com.dawn.service.TagService;
import com.dawn.vo.PaginationVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Reference(interfaceClass = TagService.class, version = "1.0.0", check = false)
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(Integer pageNum, Model model) {

        if (pageNum == null) {
            pageNum = 1;
        }

        int pageSize = 4;

        PaginationVO<Tag> vo = tagService.listTag(pageNum, pageSize);


        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "admin/tags";
    }

    @RequestMapping("/tags/input")
    public String tagInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tag", tagService.getTagById(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String saveTypes(Tag tag, RedirectAttributes attributes) {

        boolean flag = tagService.saveTag(tag);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，新增一个标签！");
        } else {
            attributes.addFlashAttribute("message", "提交信息不符合规则，不可重复提交同一标签");
            return "redirect:/admin/tags/input";
        }

        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editTypes(Tag tag, RedirectAttributes attributes) {

        boolean flag = tagService.editTag(tag);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，修改一个标签！");
        } else {
            attributes.addFlashAttribute("message", "提交信息不符合规则，请确认是否重复新增同一标签");
            return "redirect:/admin/tags/input";
        }

        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTypes(@PathVariable("id") Integer id, RedirectAttributes attributes) {

        boolean flag = tagService.deleteTag(id);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，删除一个标签！");
        } else {
            attributes.addFlashAttribute("message", "操作失败");
        }

        return "redirect:/admin/tags";
    }
}
