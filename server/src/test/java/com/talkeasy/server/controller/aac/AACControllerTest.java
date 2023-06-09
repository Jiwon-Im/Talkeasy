package com.talkeasy.server.controller.aac;


import com.google.gson.Gson;
import com.talkeasy.server.authentication.OAuthUserInfo;
import com.talkeasy.server.common.PagedResponse;
import com.talkeasy.server.domain.aac.AAC;
import com.talkeasy.server.domain.aac.AACCategory;
import com.talkeasy.server.domain.aac.CustomAAC;
import com.talkeasy.server.domain.member.Member;
import com.talkeasy.server.dto.aac.CustomAACDto;
import com.talkeasy.server.dto.aac.ResponseAACDto;
import com.talkeasy.server.dto.aac.ResponseAACListDto;
import com.talkeasy.server.dto.chat.ChatTextDto;
import com.talkeasy.server.service.aac.AACService;
import com.talkeasy.server.service.chat.TTSService;
import com.talkeasy.server.service.member.OAuth2UserImpl;
import com.talkeasy.server.testutil.WithCustomUser;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AACControllerTest {

    @Mock
    private OAuth2UserImpl oAuth2User;

    @InjectMocks
    private AACController aacController;

    @Mock
    private AACService aacService;
    @Mock
    private TTSService ttsService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(aacController).build();
    }

    @Test
    @DisplayName("[GET] 카테고리 목록")
    void getCategory() throws Exception {

        AACCategory aacCategory1 = AACCategory.builder().id("1").title("고정").build();
        AACCategory aacCategory2 = AACCategory.builder().id("2").title("음식").build();

        List<AACCategory> result = List.of(aacCategory1, aacCategory2);

        PagedResponse<AACCategory> aacCategory = new PagedResponse<>(HttpStatus.OK, result, 1);

        when(aacService.getCategory()).thenReturn(aacCategory);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/aac/categories"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @DisplayName("[GET] 카테고리별 AAC")
    @WithCustomUser()
    void getCategoryContents() throws Exception {

//        OAuth2UserImpl member = new OAuth2UserImpl(Member.builder().id("1").build());
        String categoryId = "1"; // categoryId = 9일 때 다르게 처리

        int offset = 1;
        int size = 10;

        ResponseAACDto aac1 = ResponseAACDto.builder().id("1").title("안녕하세요").category("1").build();
        ResponseAACDto aac2 = ResponseAACDto.builder().id("2").title("안녕히계세요").category("1").build();

        List<ResponseAACDto> aacList = List.of(aac1, aac2);

        ResponseAACListDto lists = ResponseAACListDto.builder().fixedList(aacList).aacList(aacList).build();

        PagedResponse<ResponseAACListDto> result = new PagedResponse<>(HttpStatus.OK, lists, 1);

//        when(aacService.getAacByCategory(anyString(), anyInt(), anyInt())).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/aac/categories/{categoryId}", categoryId)
                        .param("offset", String.valueOf(offset))
                        .param("size", String.valueOf(size))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].title").value("안녕하세요"))
                .andExpect(jsonPath("$.data[0].category").value("1"))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].title").value("안녕히계세요"))
                .andExpect(jsonPath("$.data[1].category").value("1"))
                .andReturn();


//        ResponseEntity<?> response = aacController.getCategoryContents(categoryId, fixed, offset, size, member);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(result, response.getBody());
//
//        verify(aacService).getAacByCategory(member.getId(), categoryId, fixed, offset, size);

    }

    @Test
    @DisplayName("[GET] 커스텀 AAC")
    void getCategoryContentsByCustom() throws Exception {

//        OAuth2UserImpl member = new OAuth2UserImpl(Member.builder().id("6447cb89dade8b8f866e8f34").build());
        String categoryId = "9";
        int offset = 1;
        int size = 10;

        CustomAAC aac1 = CustomAAC.builder().id("6448c3e09ce4637f2c4269e6").text("헉").userId("6447cb89dade8b8f866e8f34").build();
        CustomAAC aac2 = CustomAAC.builder().id("6449bcad36204f5f1b7770d1").text("히히").userId("6447cb89dade8b8f866e8f34").build();

        List<CustomAAC> aacList = List.of(aac1, aac2);


        PagedResponse<ResponseAACListDto> result = new PagedResponse<>(HttpStatus.OK, aacList, 1);

        when(aacService.getAacByCustom(anyString())).thenReturn(result);



//        ResponseEntity<?> response = aacController.getCategoryContents(categoryId,  offset, size, member);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(result, response.getBody());

//        verify(aacService).getAacByCustom(member.getId(), offset, size);

    }

    @Test
    @DisplayName("[GET] 연관동사 리스트")
    void getRelativeVerb() throws Exception {
        String aacId = "22";

        ResponseAACDto aac1 = ResponseAACDto.builder().id("112").title("매워요").category("1").build();
        ResponseAACDto aac2 = ResponseAACDto.builder().id("113").title("짜요").category("1").build();

        List<ResponseAACDto> aacList = List.of(aac1, aac2);

        PagedResponse<ResponseAACDto> serviceReturnList = new PagedResponse(HttpStatus.OK, aacList, 1);

        when(aacService.getRelativeVerb(anyString())).thenReturn(serviceReturnList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/aac/relative-verb/{aacId}", aacId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("112"))
                .andExpect(jsonPath("$.data[0].title").value("매워요"))
                .andExpect(jsonPath("$.data[0].category").value("1"))
                .andExpect(jsonPath("$.data[1].id").value("113"))
                .andExpect(jsonPath("$.data[1].title").value("짜요"))
                .andExpect(jsonPath("$.data[1].category").value("1"))
                .andReturn();;

//        ResponseEntity<?> response = aacController.getRelativeVerb(aacId);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(serviceReturnList, response.getBody());
//
//        verify(aacService).getRelativeVerb(aacId);
    }

    @Test
    @DisplayName("[POST] 커스텀 aac 등록")
    @WithCustomUser
    void postCustomAac() throws Exception {

        CustomAACDto customAACDto = new CustomAACDto();
        customAACDto.setTitle("스불재");

        String requestJson = new Gson().toJson(customAACDto);

        Member member = Member.builder().id("1").name("name").role(1).build();
        oAuth2User = new OAuth2UserImpl(member);

//        when(aacService.postCustomAac(customAACDto, oAuth2User.getId())).thenReturn("1");

        // Set the authentication object in SecurityContextHolder
        Authentication authentication = new OAuth2AuthenticationToken(oAuth2User, null, "provider");
        SecurityContextHolder.getContext().setAuthentication(authentication);


        doReturn("1").when(aacService).postCustomAac(customAACDto, oAuth2User.getId());

        mockMvc.perform(post("/api/aac/custom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                        .with(csrf())
                        .with(user(oAuth2User))) // 사용자 정보를 직접 지정)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("[PUT] 커스텀 aac 수정")
    void putCustomAac() {
    }

    @Test
    @DisplayName("[DELETE] 커스텀 aac 삭제")
    void deleteCustomAac() {
    }

    @Test
    @DisplayName("[POST] OpenAI 문장 생성")
    void getGenereteText() throws Exception {
        String text = "배고프다 밥";
        ChatTextDto textDto = new ChatTextDto();
        textDto.setText(text);

        String requestJson = new Gson().toJson(textDto);

//        doReturn("배고파요 밥 먹고싶어요").when(aacService).getGenereteText(textDto);
//        when(aacService.getGenereteText(textDto)).thenReturn("배고파요 밥 먹고싶어요");

        mockMvc.perform(post("/api/aac/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data").value("배고파요 밥 먹고싶어요"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[GET] TTS 음성 파일 반환")
    void getTTS() {
    }
}