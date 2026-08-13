package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.PostService;
import com.saad.socialmedia.models.Post;
import com.saad.socialmedia.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController
{
    private final PostService postService;
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @PostMapping("/posts/user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Integer userId) throws Exception
    {
        Post createPost = postService.createNewPost(post, userId);

        return new ResponseEntity<>(createPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception
    {
        String message = postService.deletePost(postId, userId);
        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception
    {
        Post post = postService.findPostById(postId);

        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUserPost(@PathVariable Integer userId)
    {
        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPost()
    {
        List<Post> posts = postService.findAllPost();

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PutMapping("/posts/save/{postId}/user/{userId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception
    {
        Post posts = postService.savedPost(postId, userId);

        return new ResponseEntity<Post>(posts, HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/like/{postId}/user/{userId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId, @PathVariable Integer userId) throws Exception
    {
        Post posts = postService.likePost(postId, userId);

        return new ResponseEntity<Post>(posts, HttpStatus.ACCEPTED);
    }


}
