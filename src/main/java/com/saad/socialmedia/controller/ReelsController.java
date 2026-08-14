package com.saad.socialmedia.controller;

import com.saad.socialmedia.Service.ReelsService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.models.Reels;
import com.saad.socialmedia.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController
{
    private final ReelsService reelsService;
    private final UserService userService;
    public ReelsController(ReelsService reelsService, UserService userService)
    {
        this.reelsService = reelsService;
        this.userService = userService;
    }

    @PostMapping("/api/reels")
    public Reels createdReels(@RequestBody Reels reel, @RequestHeader("Authorization") String jwt)
    {
        User reqUser = userService.findUserByJwt(jwt);
        Reels createdReels = reelsService.createReels(reel, reqUser);

        return createdReels;
    }

    @GetMapping("/api/reels")
    public List<Reels> findAllReels()
    {
        List<Reels> reels = reelsService.findAllReels();

        return reels;
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findUsersReels(@PathVariable Integer userId) throws Exception
    {
        List<Reels> reels = reelsService.findUsersReel(userId);
        return reels;
    }
}
