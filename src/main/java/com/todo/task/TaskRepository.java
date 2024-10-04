package com.todo.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findAllByOwnerCompany_Id(long companyId);

    List<Task> findAllByOwnerCompany_IdAndOwner_Id(long companyId, long ownerId);

    void deleteAllByOwnerCompany_IdAndOwner_Id(long companyId, long ownerId);

    Optional<Task> findByIdAndOwnerCompany_Id(long taskId, long ownerId);

    List<Task> findAllByOwner_Id(long ownerId);

    Optional<Task> findByIdAndOwner_Id(long taskId, long ownerId);
}
