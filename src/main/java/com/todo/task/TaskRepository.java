package com.todo.task;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findAllByOwnerCompany_Id(Long companyId, Pageable pageable);

    List<Task> findAllByOwnerCompany_IdAndOwner_Id(Long companyId, Long ownerId, Pageable pageable);

    void deleteAllByOwnerCompany_IdAndOwner_Id(Long companyId, Long ownerId);

    Optional<Task> findByIdAndOwnerCompany_Id(Long taskId, Long ownerId);

    List<Task> findAllByOwner_Id(Long ownerId, Pageable pageable);

    Optional<Task> findByIdAndOwner_Id(Long taskId, Long ownerId);
}
