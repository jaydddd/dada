package com.qianfeng.controller;



import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by admin on 2018/4/9.
 */
@Controller
public class ControllerDemo {

    @RequestMapping("/demo")
    public String genHtml(ModelMap map){
        map.put("hello","最美的");

        return "index.html";

    }


}
