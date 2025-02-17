package hellgate.api.controller.account;

import hellgate.common.model.account.AccountForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String index(@ModelAttribute AccountForm form) {
        return "account/login";
    }
}
