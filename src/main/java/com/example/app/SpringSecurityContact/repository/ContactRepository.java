package com.example.app.SpringSecurityContact.repository;

import com.example.app.SpringSecurityContact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByEmail(String email);
}
