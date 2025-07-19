package com.felypeganzert.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DevSecurityConfigTest {

    @Test
    void contextLoadsWithTestProfile() {
        // Testa se o contexto carrega corretamente com o perfil test
    }
}
