package intern.notice;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by --- on 17/09/05.
 */
@Controller
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping(path = "/notices")
    String List(Principal principal, Model model) {
        // limitsの数字で新着何件通知するか指定
        Integer unReadSize = noticeService.getUnReadNotice(principal.getName()).size();
        List<Notice> notices = noticeService.getNotice(principal.getName());
//        List<Notice> notices = noticeService.getNotice(principal.getName())
//                                   .stream()
//                                   .limit(50)
//                                   .collect(Collectors.toList());
        noticeService.getNotice(principal.getName())
        .stream()
        .skip(50)
        .collect(Collectors.toList())
        .forEach(notice -> { noticeService.delete(notice.getId()); });
        model.addAttribute("unReadSize", unReadSize);
        model.addAttribute("notices", notices);
        return "notices/list";
    }



}
