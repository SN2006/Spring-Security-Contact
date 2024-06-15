package com.example.app.SpringSecurityContact.controller;

import com.example.app.SpringSecurityContact.dto.ContactDto;
import com.example.app.SpringSecurityContact.entity.Contact;
import com.example.app.SpringSecurityContact.service.impl.ContactServiceImpl;
import com.example.app.SpringSecurityContact.util.ModelMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final ContactServiceImpl contactService;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactController(ContactServiceImpl contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getContacts(Model model) {
        List<ContactDto> contacts = contactService.findAll();
        model.addAttribute("contacts", contacts);
        model.addAttribute("fragmentName", "contact_list");
        model.addAttribute("title", "Contact List");
        model.addAttribute("showLogout", true);
        model.addAttribute("showLogin", false);
        return "index";
    }

    @GetMapping("/edit-form")
    public String editForm(Model model){
        Contact currentContact = getCurrentContact();
        if (currentContact == null){
            return "redirect:/login";
        }
        ContactDto contactDto = modelMapper.convertContactToContactDto(currentContact);

        model.addAttribute("fragmentName", "edit_contact");
        model.addAttribute("title", "Edit Info");
        model.addAttribute("showLogout", true);
        model.addAttribute("showLogin", false);
        model.addAttribute("contact_id", currentContact.getId());
        model.addAttribute("contact", contactDto);
        return "index";
    }

    @PutMapping("/edit-info/{id}")
    public String editInfo(@PathVariable("id") Long id,
                           @Valid @ModelAttribute("contact") ContactDto contactDto,
                           BindingResult result,
                           Model model){
        if (result.hasFieldErrors("name") || result.hasFieldErrors("phone")){
            model.addAttribute("fragmentName", "edit_contact");
            model.addAttribute("title", "Edit Info");
            model.addAttribute("showLogout", true);
            model.addAttribute("showLogin", false);
            model.addAttribute("contact_id", id);
            model.addAttribute("contact", contactDto);
            return "index";
        }
        contactService.update(id, contactDto);
        return "redirect:/contacts/edit-form?success";
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
