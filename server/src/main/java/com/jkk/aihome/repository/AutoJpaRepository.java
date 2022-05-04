package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.AutoDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoJpaRepository extends JpaRepository<AutoDO, Integer> {
}
