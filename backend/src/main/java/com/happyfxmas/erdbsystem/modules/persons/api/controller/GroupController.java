package com.happyfxmas.erdbsystem.modules.persons.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.GroupDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreateGroupRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.GroupDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.service.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${APP_REST_API_PREFIX}/${APP_REST_API_VERSION}/groups")
@CrossOrigin(origins = "${APP_FRONT_URL}")
public class GroupController {

    private final GroupService groupService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getGroups(@RequestParam(required = false) Boolean isActive) {
        if (isActive == null) isActive = true;
        var groupList = groupService.getAll(isActive);
        var groupDTOList = GroupDTOMapper.makeDTOs(groupList);
        return ResponseEntity.ok(groupDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        var group = groupService.getById(id);
        var groupDTO = GroupDTOMapper.makeDTO(group);
        return ResponseEntity.ok(groupDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody @Valid CreateGroupRequestDTO createGroupRequestDTO) {
        var group = GroupDTOMapper.fromDTO(createGroupRequestDTO);
        group = groupService.create(group);
        return new ResponseEntity<>(Map.of("id", group.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteGroupById(@PathVariable Long id) {
        var group = groupService.getById(id);
        groupService.delete(group);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<GroupDTO> updateGroupById(@PathVariable Long id,
                                                    @RequestBody @Valid CreateGroupRequestDTO createGroupRequestDTO) {
        var group = groupService.getById(id);
        group.setTitle(createGroupRequestDTO.getTitle());
        group = groupService.update(group);
        return ResponseEntity.ok(GroupDTOMapper.makeDTO(group));
    }
}
