package com.demoProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegistrationRequestDTO {
    private String name;
    private String redirectUri;
}
