package com.edumanager.communication.domain.announcement.repository;

import com.edumanager.communication.domain.announcement.entity.Announcement;
import com.edumanager.communication.domain.announcement.enums.AnnouncementPriority;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: AnnouncementRepository
 * Defines the contract for announcement persistence operations.
 */
public interface AnnouncementRepository {

    Announcement save(Announcement announcement);

    Optional<Announcement> findById(UUID id);

    List<Announcement> findByTenantId(UUID tenantId);

    List<Announcement> findByIsPublished(boolean isPublished);

    List<Announcement> findByAnnouncementType(String announcementType);

    List<Announcement> findByPriority(AnnouncementPriority priority);

    List<Announcement> findActiveAnnouncements(LocalDate date);

    void deleteById(UUID id);
}
