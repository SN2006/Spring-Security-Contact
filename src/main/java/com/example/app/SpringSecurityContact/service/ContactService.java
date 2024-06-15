package com.example.app.SpringSecurityContact.service;

import com.example.app.SpringSecurityContact.dto.ContactDto;
import com.example.app.SpringSecurityContact.entity.Contact;

import java.util.List;

public interface ContactService {
    void save(ContactDto contactDto);
    void update(Long id, ContactDto contactDto);
    Contact findByEmail(String email);
    List<ContactDto> findAll();
}
