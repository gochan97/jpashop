package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class SelectItem {

    @RequestMapping("items/selectItem")
        public String select(){
        log.info("select item");
        return "items/selectItem";
    }

}
