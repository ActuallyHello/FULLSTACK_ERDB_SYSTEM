package com.happyfxmas.erdbsystem.modules.persons.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.request.CreatePositionRequestDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.PositionNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.PositionServerException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.service.PositionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<PositionDTO>> getPositions() {
        var positionList = positionService.getAll();
        var positionDTOList = PositionDTOMapper.makeDTOs(positionList);
        return ResponseEntity.ok(positionDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createPosition(@RequestBody @Valid CreatePositionRequestDTO createPositionRequestDTO) {
        var position = PositionDTOMapper.fromDTO(createPositionRequestDTO);
        position = positionService.create(position);
        return new ResponseEntity<>(Map.of("id", position.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deletePositionById(@PathVariable Long id) {
        var position = positionService.getById(id);
        positionService.delete(position);
        return ResponseEntity.noContent().build();
    }
}
