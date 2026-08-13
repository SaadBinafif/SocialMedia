package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.PostRepository;
import com.saad.socialmedia.Repository.UserRepository;
import com.saad.socialmedia.Service.PostService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Post;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService
{

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    public PostServiceImpl(PostRepository postRepository, UserService userService, UserRepository userRepository)
    {
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception
    {
        User user = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if (!post.getUser().getId().equals(user.getId())) {
            throw new Exception("You can't delete another user's post");
        }

        // Remove post from all users' saved posts to avoid FK constraint violation
        List<User> allUsers = userRepository.findAll();
        for (User u : allUsers)
        {
            if (u.getSavedPost().contains(post))
            {
                u.getSavedPost().remove(post);
                userRepository.save(u);
            }
        }

        postRepository.delete(post);

        return "Post deleted successfully";
    }


    @Override
    public List<Post> findPostByUserId(Integer userId)
    {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception
    {
        Optional<Post> optional = postRepository.findById(postId);
        if(optional.isEmpty())
            throw new Exception("post not found with id : "+postId);

        return optional.get();
    }

    @Override
    public List<Post> findAllPost()
    {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception
    {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(user.getSavedPost().contains(post))
        {
            user.getSavedPost().remove(post);
        }
        else {
            user.getSavedPost().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception
    {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        if(post.getLiked().contains(user))
        {
            post.getLiked().remove(user);
        }
        else {
            post.getLiked().add(user);
        }

        return postRepository.save(post);
    }
}
