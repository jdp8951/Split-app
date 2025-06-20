package com.rohit.splitapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohit.splitapp.persistence.dto.report.TempReport;
import com.rohit.splitapp.persistence.entities.Group;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findByUserId (UUID userId);

    @Query(value = "SELECT * FROM group_table g WHERE g.default_group = TRUE AND g.user_id = ?1", nativeQuery = true)
    Optional<Group> findDefaultGroup(UUID userId);

    @Query("SELECT NEW com.rohit.splitapp.persistence.dto.report.TempReport(" +
            "e.id, " +
            "g.groupName, " +
            "e.description, " +
            "e.payer.username, " +
            "e.amount) " +
            "FROM Expense e " +
            "JOIN e.group g " +
            "WHERE g.id = ?1 ")
    List<TempReport> generateReportById(UUID groupId);

}
