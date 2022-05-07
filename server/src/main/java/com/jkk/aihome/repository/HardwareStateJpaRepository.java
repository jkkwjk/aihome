package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardwareStateJpaRepository extends JpaRepository<HardwareStateDO, String> {
}
