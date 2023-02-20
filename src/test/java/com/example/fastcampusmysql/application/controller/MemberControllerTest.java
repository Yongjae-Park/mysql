package com.example.fastcampusmysql.application.controller;


import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();


    private final String PATH = "/members";

//            mvc.perform(get(BASE_PATH + path)
//                        .params(historySearch))
//            .andExpect(status().isBadRequest())
//            .andExpect((rslt) ->
//    assertTrue(rslt.getResolvedException().getClass().isAssignableFrom(IllegalArgumentException.class)))
//            .andDo(print())
//            .andReturn();

    @DisplayName("controller")
    @Test
    public void register_Test() throws Exception {
        MemberDto memberDto = new MemberDto(
                null, "ytothej92@gmail.com", "yongjae", LocalDate.of(1992, 04, 14) );

        String body = mapper.writeValueAsString(memberDto);

        String content = "";
        mvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getMember() {
    }

    @Test
    public void changeNickname() {
    }

    @Test
    void getNicknameHistories() {
    }
}
