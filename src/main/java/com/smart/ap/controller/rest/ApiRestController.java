package com.smart.ap.controller.rest;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart.ap.common.constraint.ResponseService;
import com.smart.ap.common.model.CommonResult;
import com.smart.ap.common.model.ListResult;
import com.smart.ap.common.model.SingleResult;
import com.smart.ap.entity.Board;
import com.smart.ap.service.ApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = { "API" }, description = "API")
@RestController
@RequiredArgsConstructor
public class ApiRestController {

	private final ResponseService responseService;

	@Autowired
	ApiService apiService;


	@ApiOperation(value="Board List", notes="게시판 리스트")
    @GetMapping(value="/boards")
	public ListResult<Board> boards(@RequestParam Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) {
		return responseService.getListResult(apiService.boards(params));
	}

	@ApiOperation(value="Board Get", notes="게시판 조회")
	@GetMapping(value="/board")
	public SingleResult<Board> board(@RequestParam Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) {
		return responseService.getSingleResult(apiService.board(params));
	}

	@ApiOperation(value="Board Save", notes="게시판 저장")
	@PostMapping(value="/board")
	public SingleResult<Board> boardSave(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) {
		return responseService.getSingleResult(apiService.boardSave(params));
	}

	@ApiOperation(value="Board Update", notes="게시판 수정")
	@PutMapping(value="/board")
	public SingleResult<Board> boardUpdate(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) {
		return responseService.getSingleResult(apiService.boardUpdate(params));
	}

	@ApiOperation(value="Board Delete", notes="게시판 삭제")
	@DeleteMapping(value="/board")
	public CommonResult boardDelete(@RequestBody Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) {
		return responseService.getSuccessResult();
	}
}
