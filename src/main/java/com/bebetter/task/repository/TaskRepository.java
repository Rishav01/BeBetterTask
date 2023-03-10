package com.bebetter.task.repository;

import com.bebetter.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findByUserEmailIdAndForDate(String userEmailId, LocalDate date);
    public List<Task> findByTaskId(Integer taskId);
    public List<Task> findByUserEmailId(String userEmailId);

    @Query(value = "select * from task t where t.userEmailId = :userEmailId and t.forDate >= :fromDate " +
            "and t.forDate <= :toDate", nativeQuery = true)
    public List<Task> getTasksByUserAndDuration(String userEmailId, LocalDate fromDate, LocalDate toDate);
}
