package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.OverviewDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverviewJpaRepository extends JpaRepository<OverviewDO, Integer> {
}
