package com.smart.ap.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.ap.entity.Board;
import com.smart.ap.repository.BoardJpaRepo;

@Service
public class ApiService {

	@Autowired
	private BoardJpaRepo boardJpaRepo;

	@Transactional
	public List<Board> boards(Map<String, Object> params) {
		return boardJpaRepo.findAll();
	}

	@Transactional
	public Board board(Map<String, Object> params) {
		Long boardId = Long.parseLong((String) params.get("boardId"));
		return boardJpaRepo.findById(boardId).get();
	}

	@Transactional
	public Board boardSave(Map<String, Object> params) {
		String boardType = (String) params.get("boardType");
		String boardTitle = (String) params.get("boardTitle");
		String boardContents = (String) params.get("boardContents");
		Board board = Board.builder().boardType(boardType).boardTitle(boardTitle).boardContents(boardContents).boardDt(LocalDateTime.now()).regDt(LocalDateTime.now()).chgDt(LocalDateTime.now()).build();
		boardJpaRepo.save(board);
		return board;
	}

	@Transactional
	public Board boardUpdate(Map<String, Object> params) {
		Long boardId = new Long((Integer) params.get("boardId"));
		String boardType = (String) params.get("boardType");
		String boardTitle = (String) params.get("boardTitle");
		String boardContents = (String) params.get("boardContents");
		Board board = boardJpaRepo.findById(boardId).get();
		board.setBoardType(boardType);
		board.setBoardTitle(boardTitle);
		board.setBoardContents(boardContents);
		boardJpaRepo.save(board);
		return board;
	}

	@Transactional
	public void boardDelete(Map<String, Object> params) {
		Long boardId = new Long((Integer) params.get("boardId"));
		boardJpaRepo.deleteById(boardId);
	}

}
