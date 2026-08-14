package com.saad.socialmedia.Service.Impl;

import com.saad.socialmedia.Repository.StoryRepository;
import com.saad.socialmedia.Service.StoryService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Story;
import com.saad.socialmedia.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService
{
    private final StoryRepository storyRepository;
    private final UserService userService;
    public StoryServiceImpl(StoryRepository storyRepository, UserService userService)
    {
        this.storyRepository = storyRepository;
        this.userService = userService;
    }

    @Override
    public Story createStory(Story story, User user)
    {
        Story createStory = new Story();
        createStory.setCaptions(story.getCaptions());
        createStory.setImage(story.getImage());
        createStory.setUser(user);
        createStory.setTimestamp(LocalDateTime.now());

        return storyRepository.save(createStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception
    {
        User user = userService.findUserById(userId);

        return storyRepository.findByUserId(userId);
    }
}
