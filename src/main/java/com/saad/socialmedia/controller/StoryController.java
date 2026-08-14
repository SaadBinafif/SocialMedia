package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.StoryService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Story;
import com.saad.socialmedia.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController
{
    private final StoryService storyService;
    private final UserService userService;
    public StoryController(StoryService storyService, UserService userService)
    {
        this.storyService = storyService;
        this.userService = userService;
    }

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt)
    {
        User reqUser = userService.findUserByJwt(jwt);

        Story createdStory = storyService.createStory(story, reqUser);

        return createdStory;
    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findUsersStory(@PathVariable Integer userId, @RequestHeader("Authorization") String jwt) throws Exception
    {
        User reqUser = userService.findUserByJwt(jwt);

        List<Story> stories = storyService.findStoryByUserId(userId);

        return stories;
    }
}
