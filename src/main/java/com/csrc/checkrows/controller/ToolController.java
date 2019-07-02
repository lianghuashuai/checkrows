package com.csrc.checkrows.controller;


import com.csrc.checkrows.config.checkrows;
import com.csrc.checkrows.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
@RequestMapping(path = "/checkrows")
public class ToolController {

   @Autowired
   private ToolService toolService;





    @GetMapping(path = "/checkrows")
    public String sqlupdate(Model model,@RequestParam(value = "txtfilepath") String txtfilepath,@RequestParam(value = "excelfilepath") String excelfilepath){
        List<checkrows> list= toolService.checkrowsservice(txtfilepath,excelfilepath);
        Map<String,List<checkrows>> re=new HashMap<>();
        re.put("re",list);
        model.addAllAttributes(re);
         return "result";
    }

    @GetMapping(path = "/tool")
    public String tool(Model model){
        return "index";
    }
}
