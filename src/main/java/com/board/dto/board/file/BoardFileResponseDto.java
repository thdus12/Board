package com.board.dto.board.file;

import com.board.entity.board.file.BoardFile;

import lombok.Getter;

@Getter
public class BoardFileResponseDto {
	private String origFileName;
	private String saveFileName;
	private String filePath;
	
	public BoardFileResponseDto(BoardFile entity) {
		this.origFileName = entity.getOrigFileName();
		this.saveFileName = entity.getSaveFileName();
		this.filePath = entity.getFilePath();
	}
	
	@Override
	public String toString() {
		return "FileMstResponseDto [origFileName=" + origFileName + ", saveFileName=" + saveFileName + ", filePath=" +
	            filePath + "]";
	}
}
