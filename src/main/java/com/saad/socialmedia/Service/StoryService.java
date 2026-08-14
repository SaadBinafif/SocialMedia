package com.saad.socialmedia.Service;

import com.saad.socialmedia.models.Story;
import com.saad.socialmedia.models.User;

import java.util.List;

public interface StoryService
{
    public Story createStory(Story story, User user);

    public List<Story> findStoryByUserId(Integer userId) throws Exception;
}
