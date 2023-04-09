package netplus.messaging.web.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.PreAuth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/messaging")
public class MessagingController extends BasedController{

    @PreAuth(privilegeCode = "PG0101")
    @GetMapping("/index")
    public String index(ModelMap modelMap,HttpServletRequest request) {
        modelMap.put("pageCode", request.getParameter("pageCode"));
        return "index";
    }

}
