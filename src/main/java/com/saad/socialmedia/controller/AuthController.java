package com.saad.socialmedia.controller;

import com.saad.socialmedia.Repository.UserRepository;
import com.saad.socialmedia.Service.CustomeUserDetailsService;
import com.saad.socialmedia.Service.UserService;
import com.saad.socialmedia.config.JwtUtils;
import com.saad.socialmedia.models.User;
import com.saad.socialmedia.request.LoginRequest;
import com.saad.socialmedia.response.AuthResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomeUserDetailsService customeUserDetails;
    public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, CustomeUserDetailsService customeUserDetails)
    {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customeUserDetails = customeUserDetails;
    }

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception
    {
        User isExist = userRepository.findByEmail(user.getEmail());
        if(isExist != null)
            throw new Exception("this email is already used with another account");


        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

        String token = JwtUtils.generateToken(authentication);

        AuthResponse res = new AuthResponse(token, "Register Success");

        return res;
    }

    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String token = JwtUtils.generateToken(authentication);

        return new AuthResponse(token, "Login Success");

    }

    private Authentication authenticate(String email, String password)
    {
        UserDetails userDetails = customeUserDetails.loadUserByUsername(email);

        if (userDetails == null)
            throw new BadCredentialsException("Invalid username");

        if(!passwordEncoder.matches(password, userDetails.getPassword()))
        {
            throw new BadCredentialsException("Password Not Matched");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
