package com.bebetter.task.repository;

import com.bebetter.task.entity.Task;
import com.bebetter.task.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findByEmailIdAndForDate(String emailId, LocalDate date);

    @Query(value = "select * from task t where t.email_Id = :emailId and t.for_Date >= :fromDate " +
            "and t.for_Date <= :tillDate", nativeQuery = true)
    public List<Task> getTasksByUserAndDuration(String emailId, LocalDate fromDate, LocalDate tillDate);

    @Query(value = "select * from task t where t.email_Id = :emailId and t.for_Date >= :fromDate " +
            "and t.for_Date <= :tillDate and t.status = :status", nativeQuery = true)
    public List<Task> getTasksByUserDurationAndStatus(String emailId, LocalDate fromDate, LocalDate tillDate, String status);
}
