package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.CommentRepository;
import com.saad.socialmedia.Repository.PostRepository;
import com.saad.socialmedia.Service.CommentService;
import com.saad.socialmedia.Service.PostService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Comment;
import com.saad.socialmedia.models.Post;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService
{
    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentServiceImpl(PostService postService, UserService userService,
                              CommentRepository commentRepository,
                              PostRepository postRepository)
    {
        this.postService = postService;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception
    {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment =commentRepository.save(comment);

        post.getComments().add(savedComment);

        postRepository.save(post);

        return savedComment ;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> optional = commentRepository.findById(commentId);
        if (optional.isEmpty())
            throw new Exception("Comment Not Exist..");

        return optional.get();
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception
    {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);

        if(!comment.getLiked().contains(user)){
             comment.getLiked().add(user);
        }
        else {
            comment.getLiked().remove(user);
        }
        return commentRepository.save(comment);
    }
}
