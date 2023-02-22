package com.example.fastcampusmysql.application.controller;


import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberController memberController;

    private ObjectMapper mapper = new ObjectMapper();


    private final String PATH = "/members";

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(memberController).build();
        mapper.registerModule(new JavaTimeModule());
    }

    @DisplayName("회원가입 - 모든 유효성 검사에 통과했다면 회원가입에 성공한다.")
    @Test
    public void register_Test() throws Exception {
        MemberDto.RegisterMemberCommand memberDto = new MemberDto.RegisterMemberCommand(
                "ytothej92@gmail.com", "yongjae", LocalDate.of(1992, 04, 14));

        String body = mapper.writeValueAsString(memberDto);

        mvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("회원가입 - 닉네임 길이가 10자리 넘어가면 회원가입에 실패한다.")
    @Test
    public void register_nickname_overMaxSize() throws Exception {
        String overSizeNickname = "yongjaeoversize";
        MemberDto.RegisterMemberCommand memberDto = new MemberDto.RegisterMemberCommand(
                "ytothej92@gmail.com", overSizeNickname, LocalDate.of(1992, 04, 14));

        String body = mapper.writeValueAsString(memberDto);

        mvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect((result) ->
                        assertTrue(result.getResolvedException().getClass().isAssignableFrom(MethodArgumentNotValidException.class)))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 - 중복된 닉네임으로 회원가입에 실패한다.")
    void registerMember_duplicate_nickname_failure() throws Exception {


    }

    @Test
    @DisplayName("회원가입 - 중복된 이메일로 회원가입에 실패한다.")
    void registerMember_duplicate_email_failure() throws Exception {

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
