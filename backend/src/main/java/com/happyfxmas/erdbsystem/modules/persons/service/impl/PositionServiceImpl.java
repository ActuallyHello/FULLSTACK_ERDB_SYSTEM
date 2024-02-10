package com.happyfxmas.erdbsystem.modules.persons.service.impl;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionDeleteException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.service.PositionService;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Position;
import com.happyfxmas.erdbsystem.modules.persons.store.repos.PositionRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

    private PositionRepo positionRepo;

    @Override
    public List<Position> getAll() {
        var positionList = positionRepo.findAll();
        log.debug("GET POSITIONS ({})", positionList.size());
        return positionList;
    }

    @Override
    public Position getById(Long id) {
        var position = positionRepo.findById(id)
                .orElseThrow(() -> new PositionDoesNotExistException("Position with id=" + id + " was not found!"));
        log.debug("GET POSITION WITH ID={}", id);
        return position;
    }

    @Override
    public Position getByIdWithTeachers(Long id) {
        var position = positionRepo.findByIdWithTeachers(id)
                .orElseThrow(() -> new PositionDoesNotExistException("Position with id=" + id + " was not found!"));
        log.debug("GET POSITION WITH ID={}", id);
        return position;
    }

    @Override
    @Transactional
    public Position create(Position position) {
        try {
            position = positionRepo.saveAndFlush(position);
            log.info("POSITION WITH ID={} WAS CREATED", position.getId());
            return position;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING POSITION: {}", exception.getMessage());
            throw new PositionCreationException("Error when creating a position! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Position position) {
        try {
            positionRepo.delete(position);
            positionRepo.flush();
            log.info("POSITION WITH ID={} WAS DELETED", position.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING POSITION: {}", exception.getMessage());
            throw new PositionDeleteException("Error when deleting position! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Position update(Position positionNew) {
        try {
            var position = positionRepo.saveAndFlush(positionNew);
            log.info("POSITION WITH ID={} WAS CREATED", position.getId());
            return position;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING POSITION: {}", exception.getMessage());
            throw new PositionCreationException("Error when updating position! [DatabaseException]", exception);
        }
    }
}
