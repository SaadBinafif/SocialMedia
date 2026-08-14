package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.CommentService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Comment;
import com.saad.socialmedia.models.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController
{
    private final CommentService commentService;
    private final UserService userService;
    public CommentController(CommentService commentService, UserService userService)
    {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String jwt,
                                 @PathVariable("postId") Integer postId) throws Exception
    {
        User user = userService.findUserByJwt(jwt);
        Comment createdComment = commentService.createComment(comment, postId, user.getId());

        return createdComment;
    }

    @PutMapping("/api/comments/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String jwt,
                                 @PathVariable Integer commentId) throws Exception
    {
        User user = userService.findUserByJwt(jwt);
        Comment likedComment = commentService.likeComment(commentId, user.getId());
        return likedComment;
    }
}
