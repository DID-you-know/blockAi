package com.a506.blockai.db.repository;

import com.a506.blockai.db.entity.DID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DIDRepository extends JpaRepository<DID, Integer> {
}
