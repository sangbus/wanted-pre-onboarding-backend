package com.example.demo.controller;

import com.example.demo.domain.post.Post;
import com.example.demo.domain.user.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostControllerTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    Member member1 = Member.builder()
            .email("tkdghks4564@naver.com")
            .password("tkdghks12")
            .build();
    Member member2 = Member.builder()
            .email("rhdrl4564@naver.com")
            .password("tkdghks12")
            .build();
    Post post1 = new Post("도서관","도서관에서 하루를 보내고 있다","tkdghks4564@naver.com");
    Post post2 = new Post("책상","책상에서 공부를 하고 있다","rhdrl4564@naver.com");

    @AfterEach
    void afterEach() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void getPosts() {
        //given
        memberRepository.save(member1);
        memberRepository.save(member2);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        List<Post> result = postRepository.findAll();

        //then
        //게시글 수 체크
        assertThat(result.size()).isEqualTo(2);
        //게시글 제목 체크
        assertThat(result.get(0).getTitle()).contains(post1.getTitle());
        assertThat(result.get(1).getTitle()).contains(post2.getTitle());
    }

    @Test
    void getPostById() {
        //given
        memberRepository.save(member1);
        memberRepository.save(member2);
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        //then
        assertThat(postRepository.findByEmail(post1.getEmail()).get(0).getTitle())
                .isEqualTo(post1.getTitle());
        assertThat(postRepository.findByEmail(post2.getEmail()).get(1).getTitle())
                .isEqualTo(post2.getTitle());
    }

    @Test
    void createPost() {
        //given
        memberRepository.save(member1);
        memberRepository.save(member2);
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        //then
        assertThat(postRepository.findByEmail(post1.getEmail()).get(0).getTitle())
                .isEqualTo(post1.getTitle());
        assertThat(postRepository.findByEmail(post2.getEmail()).get(1).getTitle())
                .isEqualTo(post2.getTitle());
    }

    @Test
    void updatePost() {
        //given
        memberRepository.save(member1);
        memberRepository.save(member2);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        //when
        post1.setTitle("수정된 제목1");
        post1.setContent("수정된 내용1");
        post2.setTitle("수정된 제목2");
        post2.setContent("수정된 내용2");
        Post updatedPost1 = postRepository.save(post1);
        Post updatedPost2 = postRepository.save(post2);
        //then
        assertThat(savedPost1.getTitle()).isEqualTo(updatedPost1.getTitle());
        assertThat(savedPost2.getTitle()).isEqualTo(updatedPost2.getTitle());
    }

    @Test
    void deletePostById() {
        //given
        memberRepository.save(member1);
        memberRepository.save(member2);
        postRepository.save(post1);
        postRepository.save(post2);
        //when
        postRepository.delete(post1);
        postRepository.delete(post2);
        //then
        assertThat(postRepository.count()).isEqualTo(0);
    }
}