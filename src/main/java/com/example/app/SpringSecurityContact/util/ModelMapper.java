package com.example.app.SpringSecurityContact.util;

import com.example.app.SpringSecurityContact.dto.ContactDto;
import com.example.app.SpringSecurityContact.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ModelMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Contact convertContactDtoToContact(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setPassword(passwordEncoder.encode(contactDto.getPassword()));
        return contact;
    }

    public ContactDto convertContactToContactDto(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setName(contact.getName());
        contactDto.setEmail(contact.getEmail());
        contactDto.setPhone(contact.getPhone());
        return contactDto;
    }

}
