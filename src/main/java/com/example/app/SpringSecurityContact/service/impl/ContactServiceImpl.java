package com.example.app.SpringSecurityContact.service.impl;

import com.example.app.SpringSecurityContact.dto.ContactDto;
import com.example.app.SpringSecurityContact.entity.Contact;
import com.example.app.SpringSecurityContact.entity.Role;
import com.example.app.SpringSecurityContact.repository.ContactRepository;
import com.example.app.SpringSecurityContact.repository.RoleRepository;
import com.example.app.SpringSecurityContact.service.ContactService;
import com.example.app.SpringSecurityContact.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(ContactDto contactDto) {
        Contact contact = modelMapper.convertContactDtoToContact(contactDto);

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        contact.addRole(role);
        contactRepository.save(contact);
    }

    @Override
    public void update(Long id, ContactDto contactDto) {
        Optional<Contact> optional = contactRepository.findById(id);
        if (optional.isEmpty()) return;

        Contact contactFromDb = optional.get();
        contactFromDb.setName(contactDto.getName());
        contactFromDb.setPhone(contactDto.getPhone());
        contactRepository.save(contactFromDb);
    }

    @Override
    public Contact findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    @Override
    public List<ContactDto> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream()
                .map(modelMapper::convertContactToContactDto)
                .collect(Collectors.toList());
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
