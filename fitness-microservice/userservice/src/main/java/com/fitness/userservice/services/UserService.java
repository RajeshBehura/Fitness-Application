package com.fitness.userservice.services;

import com.fitness.userservice.UserRepository;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public  class UserService{
    //@Autowired
    private final UserRepository repository;
    //ADDED BY CHATGPT
    /*
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    */

    public UserResponse register(@NotNull RegisterRequest request) {
        if(repository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exist");
        }
        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());



        User saveUser = repository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(saveUser.getId());
        userResponse.setEmail(saveUser.getEmail());
        userResponse.setPassword(saveUser.getPassword());
        userResponse.setFirstName(saveUser.getFirstName());
        userResponse.setLastName(saveUser.getLastName());
        userResponse.setCreatedAt(saveUser.getCreatedAt());
        userResponse.setUpdatedAt(saveUser.getUpdatedAt());
        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;

    }

    public Boolean existByUserId(String userId) {
        log.info("Calling User Service for {}",userId);
        return repository.existsById(userId);
    }
}
