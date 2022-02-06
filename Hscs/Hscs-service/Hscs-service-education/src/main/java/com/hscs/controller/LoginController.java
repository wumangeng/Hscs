package com.hscs.controller;

import com.hscs.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 *
 * @author ：Carina
 * @method : EduLoginController
 * @date : 2020/12/16 21:09
 */
@RestController
@RequestMapping("edu/user")
public class LoginController {
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1753933538,2582587221&fm=26&gp=0.jpg");
    }
}
