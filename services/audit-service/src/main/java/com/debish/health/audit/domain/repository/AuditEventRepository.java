package com.debish.health.audit.domain.repository;

import com.debish.health.audit.domain.model.AuditEventRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventRepository extends JpaRepository<AuditEventRecord, Long> {
}
