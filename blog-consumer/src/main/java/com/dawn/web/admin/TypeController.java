package com.dawn.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dawn.model.Type;
import com.dawn.service.TypeService;
import com.dawn.vo.PaginationVO;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Reference(interfaceClass = TypeService.class, version = "1.0.0", check = false)
    private TypeService typeService;

    @GetMapping("/types")
    public String types(Integer pageNum, Model model) {

        if (pageNum == null) {
            pageNum = 1;
        }

        int pageSize = 4;

        PaginationVO<Type> vo = typeService.listType(pageNum, pageSize);


        int totalPages = vo.getTotal() % pageSize == 0 ? vo.getTotal() / pageSize : (vo.getTotal() / pageSize) + 1;

        model.addAttribute("page", vo);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", totalPages);

        return "admin/types";
    }

    @RequestMapping("/types/input")
    public String typeInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("type", typeService.getTypeById(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String saveTypes(Type type, RedirectAttributes attributes) {

        boolean flag = typeService.saveType(type);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，新增一个分类！");
        } else {
            attributes.addFlashAttribute("message", "提交信息不符合规则，请确认是否重复新增同一分类");
            return "redirect:/admin/types/input";
        }

        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editTypes(Type type, RedirectAttributes attributes) {

        boolean flag = typeService.editType(type);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，修改一个分类！");
        } else {
            attributes.addFlashAttribute("message", "提交信息不符合规则，请确认是否重复新增同一分类");
            return "redirect:/admin/types/input";
        }

        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String deleteTypes(@PathVariable("id") Integer id, RedirectAttributes attributes) {

        boolean flag = typeService.deleteType(id);

        if (flag == true) {
            attributes.addFlashAttribute("message", "操作成功，删除一个分类！");
        } else {
            attributes.addFlashAttribute("message", "操作失败");
        }

        return "redirect:/admin/types";
    }
}
