package com.example.app.SpringSecurityContact.controller;

import com.example.app.SpringSecurityContact.dto.ContactDto;
import com.example.app.SpringSecurityContact.entity.Contact;
import com.example.app.SpringSecurityContact.service.impl.ContactServiceImpl;
import com.example.app.SpringSecurityContact.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ContactServiceImpl contactService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ContactServiceImpl contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String home(Model model) {
        Contact currentContact = getCurrentContact();
        ContactDto contactDto = currentContact == null ? null
                : modelMapper.convertContactToContactDto(currentContact);

        model.addAttribute("fragmentName", "home");
        model.addAttribute("title", "Security Contact Application");
        model.addAttribute("showLogout", contactDto != null);
        model.addAttribute("showLogin", contactDto == null);
        model.addAttribute("contact", contactDto);
        return "index";
    }

    // Returns the currently registered contact
    private Contact getCurrentContact(){
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return contactService.findByEmail(auth.getName());
        }catch (Exception e){
            return null;
        }
    }

}
