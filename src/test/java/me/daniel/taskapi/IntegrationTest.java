package me.daniel.taskapi;


import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import me.daniel.taskapi.user.dto.LoginDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(value = SpringExtension.class)
@SpringBootTest(classes = TaskApiApplication.class)
@AutoConfigureMockMvc
@Transactional
@Ignore
public class IntegrationTest {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;


    protected LoginDto.Res requestUserJoin(LoginDto.Req dto) throws Exception {
        String content = mvc.perform(post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(content, LoginDto.Res.class);
    }
}
