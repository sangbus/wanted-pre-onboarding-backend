package com.example.demo.controller;

import com.example.demo.domain.post.Post;
import com.example.demo.dto.MemberLoginRequestDto;
import com.example.demo.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findAll(pageable);
        log.info("게시글 전체 조회");
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postRepository.findById(id);
        log.info("게시글 하나 조회");
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post, Principal principal) {
        post.setEmail(principal.getName());
        Post newPost = postRepository.save(post);
        log.info("게시글 생성");
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody Post updatedPost,
            @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Post> existingPost = postRepository.findById(id);
        log.info("게시글 수정");
        if (existingPost.isEmpty()) {
            return new ResponseEntity<>("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        Post post = existingPost.get();

        // 작성자와 로그인한 사람과 일치하는지 체크 
        if (!userDetails.getUsername().equals(post.getEmail())) {
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        // 게시글 수정
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());

        // 수정한 게시글 저장
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(savedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Optional<Post> existingPost = postRepository.findById(id);
        log.info("게시글 삭제");
        if (existingPost.isEmpty()) {
            return new ResponseEntity<>("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        Post post = existingPost.get();

        // 작성자와 로그인한 사람과 일치하는지 체크
        if (!userDetails.getUsername().equals(post.getEmail())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            return new ResponseEntity<>("작성자만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        }

        postRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
        return new ResponseEntity<>("게시글 삭제 성공", HttpStatus.OK);
    }
}
