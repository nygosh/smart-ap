package com.smart.ap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.ap.entity.Board;

@Repository
public interface BoardJpaRepo extends JpaRepository<Board, Long> {

}
