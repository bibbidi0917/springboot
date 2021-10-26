package com.koscom.springboot.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByEmail로_조회되느냐() {
        String email = "aa.com";
        userRepository.save(User.builder()
                .name("test")
                .email(email)
                .role(Role.USER)
                .build());

        Optional<User> result = userRepository.findByEmail(email);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getRole()).isEqualTo(Role.USER);
        assertThat(result.get().getEmail()).isEqualTo(email);
    }
}
