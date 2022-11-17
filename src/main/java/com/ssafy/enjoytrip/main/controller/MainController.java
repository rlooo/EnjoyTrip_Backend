package com.ssafy.enjoytrip.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping({ "/", "/index" })
    public String index() {
        return "/index";
    }

    @GetMapping("/search")
    public String mvSearch() {
        return "/content/search";
    }

    @GetMapping("/route")
    public String mvRoute() {
        return "/content/route";
    }

    @GetMapping("/mvboard")
    public String mvBoard() {
        return "/board/board_list";
    }
    
    @GetMapping("/mvboard/write")
    public String mvBoardWrite() {
        return "/board/board_wirte";
    }

    @GetMapping("/mvlogin")
    public String mvLogin() {
        return "/user/login";
    }

    @GetMapping("/mvaccount")
    public String mvAccount() {
        return "/user/account_view";
    }
}
