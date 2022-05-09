package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.OnOffStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OnOffStateJpaRepository extends JpaRepository<OnOffStateDO, String> {
}
