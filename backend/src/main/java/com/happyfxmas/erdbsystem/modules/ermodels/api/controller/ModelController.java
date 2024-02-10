package com.happyfxmas.erdbsystem.modules.ermodels.api.controller;

import com.happyfxmas.erdbsystem.externals.TestDataLoader;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.request.CreateModelRequestDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelDetailDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelWithPersonDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.ModelDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.ModelEntityDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.ModelWithPersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.api.mapper.RelationDTOMapper;
import com.happyfxmas.erdbsystem.modules.ermodels.service.ModelService;
import com.happyfxmas.erdbsystem.modules.ermodels.service.facade.ModelEntityRelationFacade;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.service.PersonService;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.TaskNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.service.ResultService;
import com.happyfxmas.erdbsystem.modules.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;
    private final PersonService personService;
    private final ResultService resultService;
    private final TaskService taskService;
    private final TestDataLoader testDataLoader;

    private final ModelEntityRelationFacade modelEntityRelationFacade;

    private final static String BY_ID = "/{id}";
    private final static String BY_PERSON_ID = "/persons/{personId}";
    private final static String UPLOAD_TEST_DATA_TO_MODEL = "/upload-test-data/{id}";

    @GetMapping
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithAuthors(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Boolean includeStudents,
            @RequestParam(required = false) Boolean includeTaskResults) {
        var modelWithPersonDTOList =
                modelEntityRelationFacade.getAllModelsWithPersonDTOPageable(
                        page,
                        size,
                        includeStudents,
                        includeTaskResults
                );
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ModelDetailDTO> getModelDetailById(@PathVariable Long id) {
        var model = modelService.getById(id);
        var modelDetailDTO = modelEntityRelationFacade.getModelDetailDTOByModel(model);
        return ResponseEntity.ok(modelDetailDTO);
    }

    @GetMapping(BY_PERSON_ID)
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPersonByPersonId(@PathVariable Long personId) {
        var person = personService.getById(personId);
        var modelList = modelService.getAllByPerson(person);
        var modelWithPersonDTOList = ModelWithPersonDTOMapper.makeDTOs(modelList);
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createModel(@RequestBody @Valid CreateModelRequestDTO createModelRequestDTO,
                                              @RequestParam(required = false) Long taskId) {
        var person = personService.getByIdWithModels(createModelRequestDTO.getPersonId());
        var model = ModelDTOMapper.fromDTO(createModelRequestDTO);
        var modelEntityDTOList = ModelEntityDTOMapper.makeDTOs(createModelRequestDTO);
        var relationDTOList = RelationDTOMapper.makeDTOs(createModelRequestDTO);
        var task = taskId != null
                ? taskService.getByIdWithResults(taskId)
                : null;
        model.setIsTaskResult(task != null);
        model.setPerson(person);
        model = modelEntityRelationFacade.createModelWithRelatedEntities(
                model,
                person,
                modelEntityDTOList,
                relationDTOList
        );
        if (task != null) resultService.sendResult(model, task);
        return new ResponseEntity<>(Map.of("modelId", model.getId()), HttpStatus.CREATED);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteModel(@PathVariable Long id) {
        var model = modelService.getById(id);
        modelEntityRelationFacade.deleteModelWithRelations(model);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(UPLOAD_TEST_DATA_TO_MODEL)
    public ResponseEntity<Object> uploadTestDataToAModel(@PathVariable Long id,
                                                         @RequestParam("file") MultipartFile file) {
        var model = modelService.getById(id);
        testDataLoader.uploadTestDataFileToModel(model, file);
        return ResponseEntity.ok(Map.of("file", "uploaded"));
    }
}
