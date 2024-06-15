package com.example.app.SpringSecurityContact.controller;

import com.example.app.SpringSecurityContact.dto.ContactDto;
import com.example.app.SpringSecurityContact.entity.Contact;
import com.example.app.SpringSecurityContact.service.impl.ContactServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final ContactServiceImpl contactService;

    @Autowired
    public AuthController(ContactServiceImpl contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("fragmentName", "login");
        model.addAttribute("title", "Login");
        model.addAttribute("showLogout", false);
        model.addAttribute("showLogin", false);
        return "index";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contact", contactDto);
        model.addAttribute("fragmentName", "register");
        model.addAttribute("title", "Register");
        model.addAttribute("showLogout", false);
        model.addAttribute("showLogin", false);
        return "index";
    }

    @PostMapping("/register")
    public String registerContact(
            @Valid @ModelAttribute("contact") ContactDto contactDto,
            BindingResult result,
            Model model
    ){
        Contact contact = contactService.findByEmail(contactDto.getEmail());
        if (contact != null) {
            result.rejectValue("email", "email error",
                    "The email already exists");
        }
        if (result.hasErrors()) {
            model.addAttribute("contact", contactDto);
            model.addAttribute("fragmentName", "register");
            model.addAttribute("title", "Register");
            model.addAttribute("showLogout", false);
            model.addAttribute("showLogin", false);
            return "index";
        }
        contactService.save(contactDto);
        return "redirect:/register?success";
    }

}
