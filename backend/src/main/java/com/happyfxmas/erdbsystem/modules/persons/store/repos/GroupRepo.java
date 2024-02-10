package com.happyfxmas.erdbsystem.modules.persons.store.repos;

import com.happyfxmas.erdbsystem.modules.persons.store.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GroupRepo extends JpaRepository<Group, Long> {

    @Query("""
            select group from Group group
                left join fetch group.studentList
            where group.id = :id
            """)
    Optional<Group> findByIdWithStudents(Long id);

    List<Group> findByIsActive(Boolean isActive);
}
