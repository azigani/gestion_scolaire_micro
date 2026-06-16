package com.edumanager.hr.domain.attendance.repository;

import com.edumanager.hr.domain.attendance.entity.AttendanceRecord;
import com.edumanager.hr.domain.attendance.enums.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: AttendanceRepository
 * Defines the contract for attendance persistence operations.
 */
public interface AttendanceRepository {

    AttendanceRecord save(AttendanceRecord attendanceRecord);

    Optional<AttendanceRecord> findById(UUID id);

    Optional<AttendanceRecord> findByEmployeeIdAndDate(UUID employeeId, LocalDate date);

    List<AttendanceRecord> findByEmployeeId(UUID employeeId);

    List<AttendanceRecord> findByDateRange(LocalDate startDate, LocalDate endDate);

    List<AttendanceRecord> findByStatus(AttendanceStatus status);

    void deleteById(UUID id);
}
