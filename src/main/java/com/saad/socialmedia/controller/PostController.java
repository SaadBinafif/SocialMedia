package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.PostService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Post;
import com.saad.socialmedia.models.User;
import com.saad.socialmedia.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController
{
    private final PostService postService;
    private final UserService userService;
    public PostController(PostService postService, UserService userService)
    {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/api/posts")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt, @RequestBody Post post) throws Exception
    {
        User reqUser = userService.findUserByJwt(jwt);
        Post createPost = postService.createNewPost(post, reqUser.getId());

        return new ResponseEntity<>(createPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception
    {
        User reqUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse res = new ApiResponse(message, true);
        return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }

    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception
    {
        Post post = postService.findPostById(postId);

        return new ResponseEntity<Post>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUserPost(@PathVariable Integer userId)
    {
        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<Post>> findAllPost()
    {
        List<Post> posts = postService.findAllPost();

        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PutMapping("/api/posts/save/{postId}")
    public ResponseEntity<Post> savedPost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception
    {
        User reqUser = userService.findUserByJwt(jwt);
        Post posts = postService.savedPost(postId, reqUser.getId());

        return new ResponseEntity<Post>(posts, HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/posts/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId, @RequestHeader("Authorization") String jwt) throws Exception
    {
        User reqUser = userService.findUserByJwt(jwt);
        Post posts = postService.likePost(postId, reqUser.getId());

        return new ResponseEntity<Post>(posts, HttpStatus.ACCEPTED);
    }


}
