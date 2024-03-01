package ru.sportswearcompany.sportswearsshop.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.sportswearcompany.sportswearsshop.model.User;
import ru.sportswearcompany.sportswearsshop.service.EmailService;
import ru.sportswearcompany.sportswearsshop.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserService userService;
    private final EmailService emailService;

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping("login")
    public String login(){
        return "login";
    }

//АУТЕНФИКАЦИЯ ПРОЙДЕНА
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request, RedirectAttributes redirectAttributes){
        if (request.isUserInRole("ROLE_ADMIN")){
            redirectAttributes.addFlashAttribute("welcome", "Welcom" +
                    request.getUserPrincipal().getName());
            return "redirect:admin/adminhome";
        }
        redirectAttributes.addFlashAttribute("welcome",
                "Welcome"+request.getUserPrincipal().getName());
        return "redirect:/";
    }

//ПОЛУЧЕНИЕ VIEW
    @RequestMapping("register")
    public String register(Model model){
    model.addAttribute("newUser",new User());
    return "login";
    }

    @PostMapping("reg")
    public String registerSubmit(@ModelAttribute User user
            , Model model
            , RedirectAttributes redirectAttributes){
        String dataForValid=userService.registerUser(user);

        switch (dataForValid){
            case "success":
                redirectAttributes.addFlashAttribute(("userInfo"),
                        "Registration is success! Please login!");
                emailService.sendMessage(user.getEmail(),user.getUsername());
                break;
            case "notUniqueUsernameAndEmail":
                model.addAttribute("userInfo",
                        "Username is not unique! Email was already registered!");
                model.addAttribute("newUser",new User());
                return "login";
            case "notUniqueUsername":
                model.addAttribute("userInfo",
                        "Username is not unique!");
                model.addAttribute("newUser",new User());
                return "login";
            case "notUniqueEmail":
                model.addAttribute("userInfo",
                        "Email was already registered!");
                model.addAttribute("newUser",new User());
                return "login";
        }
        return "redirect:/login";
    }
}
