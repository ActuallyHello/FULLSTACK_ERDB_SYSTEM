package com.happyfxmas.erdbsystem.modules.persons.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreateUserRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.UserRestrictDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.UserDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.UserRestrictDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.UserNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.UserServerException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.UserCreationException;
import com.happyfxmas.erdbsystem.modules.persons.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<UserRestrictDTO>> getAllUsers() {
        var userList = userService.getAll();
        var userRestrictDTOList = UserRestrictDTOMapper.makeDTOs(userList);
        return ResponseEntity.ok(userRestrictDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<UserRestrictDTO> getUserById(@PathVariable Long id) {
        var user = userService.getById(id);
        var userDTO = UserRestrictDTOMapper.makeDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        var userDTO = UserDTOMapper.makeDTO(createUserRequestDTO);
        var user = UserDTOMapper.fromDTO(userDTO);
        user = userService.create(user);
        return new ResponseEntity<>(Map.of("id", user.getId()), HttpStatus.CREATED);
    }
}
