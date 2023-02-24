package com.example.fastcampusmysql.application.controller;


import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc //MockMvc를 builder없이 주입받을 수 있음
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberWriteService memberWriteService;

    @MockBean
    private MemberReadService memberReadService;

    @MockBean
    private MemberRepository memberRepository;

    private WebApplicationContext context;

    private ObjectMapper mapper = new ObjectMapper();


    private final String PATH = "/members";

    @BeforeEach
    public void setUp() throws Exception {
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

        verify(memberWriteService).register(memberDto);
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

    @DisplayName("회원을 조회한다.")
    @Test
    public void getMember() throws Exception {
        String nickname = "yongjae";
        String email = "ytothej92@gmail.com";
        LocalDate birthday = LocalDate.of(1992, 4, 14);

        MemberDto member = new MemberDto(anyLong(), email, nickname, birthday);
        //memberReadService의 getMember()가 해당 MemberDto를 반환하도록 해라.
        given(memberReadService.getMember(member.id())).willReturn(member);

        mvc.perform(get(PATH + "/{id}",anyLong()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(member.nickname()))
                .andExpect(jsonPath("$.email").value(member.email()))
                .andExpect(jsonPath("$.birthday").value(member.birthday().toString()))
                .andDo(print());

        verify(memberReadService).getMember(member.id());
    }

    @DisplayName("회원의 nickname을 변경한다.")
    @Test
    public void changeNickname() throws Exception {

        String changeNickname = "changed";
        MemberDto.RegisterMemberCommand dto = createRequestMemberCommand();

        doNothing().when(memberWriteService).changeNickname(1L, changeNickname);
        given(memberReadService.getMember(1L)).willReturn(new MemberDto(1L, dto.email(), changeNickname, dto.birthday()));

        mvc.perform(post(PATH + "/{id}/name", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(changeNickname))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname", is(changeNickname)))
                .andDo(print());

        verify(memberWriteService).changeNickname(1L, changeNickname);
        verify(memberReadService).getMember(1L);
    }

    @DisplayName("닉네임 변경이력을 조회한다.")
    @Test
    void getNicknameHistories() {
    }

    private MemberDto.RegisterMemberCommand createRequestMemberCommand() {
        MemberDto.RegisterMemberCommand dto = MemberDto.RegisterMemberCommand.builder()
                .nickname("yongjae")
                .email("ytothej92@gmail.com")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        return dto;
    }
}
