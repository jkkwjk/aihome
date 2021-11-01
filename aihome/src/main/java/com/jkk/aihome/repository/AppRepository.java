package com.jkk.aihome.repository;

import com.jkk.aihome.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, Integer> {
}
