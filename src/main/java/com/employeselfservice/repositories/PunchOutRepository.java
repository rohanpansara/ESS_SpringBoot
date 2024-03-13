package com.employeselfservice.repositories;

import com.employeselfservice.models.PunchOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunchOutRepository extends JpaRepository<PunchOut,Long> {
}
