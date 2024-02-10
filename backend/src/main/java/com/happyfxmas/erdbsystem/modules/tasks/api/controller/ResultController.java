package com.happyfxmas.erdbsystem.modules.tasks.api.controller;

import com.happyfxmas.erdbsystem.modules.persons.service.TeacherService;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.ResultDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.request.UpdateResultRequestDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.ResultWithModelDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.ResultWithTaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.ResultDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.api.mapper.ResultWithTaskDTOMapper;
import com.happyfxmas.erdbsystem.modules.tasks.service.ResultService;
import com.happyfxmas.erdbsystem.modules.tasks.service.facade.ResultFacade;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Mark;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;
    private final TeacherService teacherService;
    private final ResultFacade resultFacade;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<ResultWithTaskDTO>> getResultsWithTasks(@RequestParam(required = false) Integer page,
                                                                       @RequestParam(required = false) Integer size) {
        var resultWithTaskList = resultFacade.getAllPreview(page, size);
        var resultWithTaskDTOList = resultFacade.aggregateResultWithTasks(resultWithTaskList);
        return ResponseEntity.ok(resultWithTaskDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ResultWithModelDTO> getResultById(@PathVariable Long id) {
        var result = resultService.getByIdWithModelAndTaskAndTeacher(id);
        var resultWithModelDTO = resultFacade.getByIdToEvaluateResult(result);
        return ResponseEntity.ok(resultWithModelDTO);
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<ResultDTO> updateResultByTeacher(
            @RequestBody @Valid UpdateResultRequestDTO updateResultRequestDTO,
            @PathVariable Long id) {
        var result = resultService.getById(id);
        var teacher = teacherService.getByIdWithResults(updateResultRequestDTO.getTeacherId());
        var mark = Mark.fromInteger(updateResultRequestDTO.getMark());
        result = resultService.update(result, mark, teacher);
        return ResponseEntity.ok(ResultDTOMapper.makeDTO(result));
    }
}
