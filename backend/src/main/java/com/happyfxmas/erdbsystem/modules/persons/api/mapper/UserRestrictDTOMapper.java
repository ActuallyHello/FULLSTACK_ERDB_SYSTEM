package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.UserRestrictDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.User;
import lombok.NonNull;

import java.util.List;

public class UserRestrictDTOMapper {
    public static UserRestrictDTO makeDTO(@NonNull User user) {
        return UserRestrictDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }

    public static List<UserRestrictDTO> makeDTOs(List<User> userList) {
        return userList.stream()
                .map(UserRestrictDTOMapper::makeDTO)
                .toList();
    }
}
