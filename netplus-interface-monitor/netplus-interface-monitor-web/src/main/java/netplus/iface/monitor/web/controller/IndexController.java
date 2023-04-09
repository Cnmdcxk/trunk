package netplus.iface.monitor.web.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.auth.PreAuth;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitor")
public class IndexController extends BasedController {

    @PreAuth(privilegeCode = "PG0041")
    @GetMapping("/InterFace")
    @Anonymous
    public String interfaces(){
        return "InterFace";
    }
}