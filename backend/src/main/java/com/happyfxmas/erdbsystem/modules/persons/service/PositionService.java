package com.happyfxmas.erdbsystem.modules.persons.service;


import com.happyfxmas.erdbsystem.modules.persons.store.models.Position;

import java.util.List;

public interface PositionService {
    List<Position> getAll();

    Position getById(Long id);

    Position getByIdWithTeachers(Long id);

    Position create(Position position);

    void delete(Position position);

    Position update(Position positionNew);
}
