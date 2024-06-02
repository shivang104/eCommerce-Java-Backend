package com.demoProject.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRegistrationResponseDTO {
    private String clientId;
    private String clientSecret;
    private String publicKey;
}
