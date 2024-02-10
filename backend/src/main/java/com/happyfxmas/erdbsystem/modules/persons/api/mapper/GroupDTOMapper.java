package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.GroupDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreateGroupRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Group;
import lombok.NonNull;

import java.util.List;

public class GroupDTOMapper {
    public static GroupDTO makeDTO(@NonNull Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .title(group.getTitle())
                .build();
    }

    public static GroupDTO makeDTO(@NonNull CreateGroupRequestDTO createGroupRequestDTO) {
        return GroupDTO.builder()
                .title(createGroupRequestDTO.getTitle())
                .build();
    }

    public static List<GroupDTO> makeDTOs(@NonNull List<Group> groupList) {
        return groupList.stream()
                .map(GroupDTOMapper::makeDTO)
                .toList();
    }

    public static Group fromDTO(@NonNull GroupDTO groupDTO) {
        return Group.builder()
                .id(groupDTO.getId())
                .title(groupDTO.getTitle())
                .isActive(groupDTO.getIsActive())
                .build();
    }

    public static Group fromDTO(@NonNull CreateGroupRequestDTO createGroupRequestDTO) {
        return Group.builder()
                .title(createGroupRequestDTO.getTitle())
                .build();
    }

}
