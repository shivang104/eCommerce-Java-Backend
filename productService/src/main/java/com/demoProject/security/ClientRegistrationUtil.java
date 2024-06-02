package com.demoProject.security;

import com.demoProject.DTO.ClientRegistrationRequestDTO;
import com.demoProject.DTO.ClientRegistrationResponseDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class ClientRegistrationUtil {

    @Value("${jwt.public.key.file-path}")
    private String publicKeyFilePath;

    @Value("${jwt.auth.server.url}")
    private String authServerUrl;

    @Value("${spring.application.name}")
    private String serviceName;

    @PostConstruct
    public void registerClient() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ClientRegistrationRequestDTO request = new ClientRegistrationRequestDTO();
            request.setName(serviceName);
            request.setRedirectUri("");
            ClientRegistrationResponseDTO response = restTemplate.postForObject(authServerUrl, request, ClientRegistrationResponseDTO.class);
            if (response != null && response.getPublicKey() != null) {
                String publicKeyEncoded = response.getPublicKey();
                Path path = Paths.get(publicKeyFilePath);
                Files.createDirectories(path.getParent());
                Files.write(path, publicKeyEncoded.getBytes());
            } else {
                throw new RuntimeException("Failed to retrieve public key from auth server");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during client registration", e);
        }
    }

    public PublicKey loadPublicKey() {
        try {
            String publicKeyEncoded = new String(Files.readAllBytes(Paths.get(publicKeyFilePath)));
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyEncoded);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Error loading public key", e);
        }
    }
}

