package com.prakem.prakem.controller;

import com.prakem.prakem.dto.UserDTO;
import com.prakem.prakem.entity.User;
import com.prakem.prakem.mapper.UserMapper;
import com.prakem.prakem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "This endpoint allows you to create a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserDTO.class),
                        examples = @ExampleObject(
                            name = "Example Response",
                            value = """
                                    {
                                      "email": "user@example.com",
                                      "password": "string",
                                      "fullname": "string",
                                      "photo": "iVBORw0KGgoAAAANSUhEUgAAAAUA",
                                      "enabled": true
                                    }
                                    """
                    )
            )),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            User user = UserMapper.toEntity(userDTO);
            User savedUser = userService.createUser(user);
            UserDTO savedUserDTO = UserMapper.toDTO(savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
