package com.saad.socialmedia.Service;

import com.saad.socialmedia.models.Reels;
import com.saad.socialmedia.models.User;

import java.util.List;

public interface ReelsService
{
    public Reels createReels(Reels reels, User user);

    public List<Reels> findAllReels();

    public List<Reels> findUsersReel(Integer userId) throws Exception;
}
