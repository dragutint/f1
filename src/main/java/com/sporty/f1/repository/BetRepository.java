package com.sporty.f1.repository;

import com.sporty.f1.domain.BetEntity;
import com.sporty.f1.domain.BetStatusEnum;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BetRepository extends JpaRepository<BetEntity, UUID> {

    List<BetEntity> findByEventIdAndStatus(String eventId, BetStatusEnum status);

    @Query("SELECT b FROM BetEntity b WHERE b.id = :betId")
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<BetEntity> lockById(UUID betId);
}
