package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.HardwareDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareJpaRepository extends JpaRepository<HardwareDO, String> {
}
