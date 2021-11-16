package com.a506.blockai.db.repository;

import com.a506.blockai.db.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    Optional<List<Certification>> findByUserId(int userId);
}