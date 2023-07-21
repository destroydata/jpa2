package com.example.demo.todos.controller;

import com.example.demo.config.domain.entity.MemberLogin;
import com.example.demo.config.repository.MemberLoginRepository;
import com.example.demo.members.domain.entity.Member;
import com.example.demo.members.domain.request.LoginRequest;
import com.example.demo.members.domain.response.LoginResponse;
import com.example.demo.members.repository.MemberRepository;
import com.example.demo.members.service.MemberService;
import com.example.demo.todos.domain.entity.Todo;
import com.example.demo.todos.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    Todo todo;
    @Nested
    class 투두_포스트_요청{
        @Test
        @DisplayName("성공")
        void insert() throws Exception{
            Map<String, Object> req = new HashMap<>();
            req.put("title", "ttt");
            req.put("content", "ccc");
            req.put("isDone", false);
            mockMvc.perform(
                            post("/api/v1/todos")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(req))
                                    .header("Authorization", "Bearer "+token)
                    )
                    .andExpect(
                            status().isCreated());
            List<Todo> all = todoRepository.findAll();
            assertEquals(all.size(), 3);
        }
        @Test
        @DisplayName("토큰 실패")
        void insertFail() throws Exception{
            Map<String, Object> req = new HashMap<>();
            req.put("title", "ttt");
            req.put("content", "ccc");
            req.put("isDone", false);
            mockMvc.perform(
                            post("/api/v1/todos")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(req))
                    )
                    .andExpect(
                            status().isBadRequest());

            List<Todo> all = todoRepository.findAll();
            assertEquals(all.size(), 2);
        }
    }
    @Nested
    class 투두_체크하기{
        @Test
        @DisplayName("성공")
        void insert() throws Exception{
            mockMvc.perform(
                            put("/api/v1/todos/" + todo.getId() + "/check")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .header("Authorization", "Bearer "+token)
                    )
                    .andExpect(
                            status().isAccepted());
            Optional<Todo> byId = todoRepository.findById(todo.getId());
            assertTrue(byId.get().isDone());
        }
        @Test
        @DisplayName("토큰 실패")
        void insertFail() throws Exception{
            Map<String, Object> req = new HashMap<>();
            req.put("title", "ttt");
            req.put("content", "ccc");
            req.put("isDone", false);
            mockMvc.perform(
                            put("/api/v1/todos/" + todo.getId() + "/check")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(req))
                    )
                    .andExpect(
                            status().isBadRequest());
            Optional<Todo> byId = todoRepository.findById(todo.getId());
            assertFalse(byId.get().isDone());
        }
    }

    /**
     * Params :
     * content = "" , page = 0, size 20 , isDone(default null) , title = ""
     * likeLoe (Integer) = null, likeGoe (Integer) = null
     * 만약 likeLoe 3 이면 3보다 작거나 같은 것 것들 가져오기 likeCount <= likeLoe
     * 만약 likeGoe 3 이면 3보다 크거나 같은 것 것들 가져오기 likeCount >= likeGoe
     * 만약 likeLoe 3 , likeGoe 5 이면  likeGoe <= likeCount  <= likeLoe
     * 만약 likeLoe 3 , likeGoe 5 이고 content가 t면
     * likeGoe <= likeCount  <= likeLoe and content like "%t%"
     * 만약 likeLoe 3 , likeGoe 5 이고 content가 t 고 title = 1 이면
     * likeGoe <= likeCount  <= likeLoe and content like "%t%" and title like "%1%"
     */
    @Nested
    class 투두_가져오기{
        @Test
        @DisplayName("그냥 가져오기")
        void getDefault() throws Exception{
            mockMvc.perform(
                            get("/api/v1/todos")
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(
                            status().isOk())
                    .andExpect(
                            jsonPath("$.content", hasSize(2)))
                    .andExpect(
                            jsonPath("$.content.[0].id").isNotEmpty())
                    .andExpect(
                            jsonPath("$.content.[0].title").value("t"))
                    .andExpect(
                            jsonPath("$.content.[1].id").isNotEmpty())
                    .andExpect(
                            jsonPath("$.content.[1].title").value("t2"));
        }
        @Test
        @DisplayName("타이틀로 가져오기")
        void likeTitle() throws Exception{
            mockMvc.perform(
                            get("/api/v1/todos")
                                    .param("title", "t2")
                    )
                    .andExpect(
                            status().isOk())
                    .andExpect(
                            jsonPath("$.content", hasSize(1)))
                    .andExpect(
                            jsonPath("$.content.[0].id").isNotEmpty())
                    .andExpect(
                            jsonPath("$.content.[0].title").value("t2"))
            ;
        }

        @Test
        @DisplayName("체크한거")
        void isDoneTure() throws Exception{
            Todo todo1 = new Todo(todo.getId(), todo.getTitle(), todo.getContent(), true, todo.getLikeCount(), todo.getMember());
            todoRepository.save(todo1);
            entityManager.flush();
            entityManager.clear();
            mockMvc.perform(
                            get("/api/v1/todos")
                                    .param("isDone", "true")
                    )
                    .andExpect(
                            status().isOk())
                    .andExpect(
                            jsonPath("$.content", hasSize(1)))
                    .andExpect(
                            jsonPath("$.content.[0].title")
                                    .value(todo1.getTitle()))
            ;
        }
    }



    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    MemberLoginRepository memberLoginRepository;
    String email = "1111";
    String password = "1234";
    Member member;
    @Autowired
    EntityManager entityManager;
    @Autowired
    MemberService memberService;
    String token;
    @BeforeEach
    void init(){
        Member member =
                new Member(null, email, password
                        , "name", 10, new ArrayList<>(), null);

        this.member = memberRepository.save(member);
        this.todo = todoRepository.save(
                new Todo(null, "t", "t"
                        , false, 0, member)
        );
        todoRepository.save(
                new Todo(null, "t2","t2"
                        , false,0, member)
        );
        MemberLogin entity = new MemberLogin(this.member, LocalDateTime.now());
        memberLoginRepository.save(entity);
        entityManager.flush();
        entityManager.clear();
        LoginResponse login = memberService.login(new LoginRequest(email, password));
        token = login.token();

    }
    @AfterEach
    void clean(){
        todoRepository.deleteAll();
        memberLoginRepository.deleteAll();
        memberRepository.deleteAll();
    }
}