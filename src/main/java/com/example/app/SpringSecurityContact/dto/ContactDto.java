package com.example.app.SpringSecurityContact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {

    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Phone cannot be empty")
    @Pattern(regexp = "[0-9]{3} [0-9]{3}-[0-9]{4}",
            message = "Phone must be in format xxx xxx-xxxx")
    private String phone;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
