package com.board.service;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.dto.file.BoardFileResponseDto;
import com.board.entity.board.BoardEntity;
import com.board.entity.file.BoardFileEntity;
import com.board.entity.file.BoardFileRepository;

import lombok.RequiredArgsConstructor;

/**
 * 게시글 파일 관리를 위한 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class BoardFileService {
    private final BoardFileRepository boardFileRepository;

    /**
     * 파일 ID로 파일 정보를 조회하는 메서드
     *
     * @param id 파일 ID
     * @return 조회된 파일 정보를 담은 BoardFileResponseDto 객체
     * @throws Exception 예외 발생 시
     */
    public BoardFileResponseDto findById(Long id) throws Exception {
        return new BoardFileResponseDto(boardFileRepository.findById(id).get());
    }

    /**
     * 게시글 ID로 해당 게시글의 파일 ID 목록을 조회하는 메서드
     *
     * @param boardId 게시글 ID
     * @return 조회된 파일 ID 목록
     * @throws Exception 예외 발생 시
     */
    public List<Long> findByBoardId(Long boardId) throws Exception {
        return boardFileRepository.findByBoardId(boardId);
    }

    /**
     * 파일 업로드를 처리하는 메서드
     *
     * @param multiRequest MultipartHttpServletRequest 객체
     * @param board        게시글 엔티티
     * @return 파일 업로드 성공 여부
     * @throws Exception 예외 발생 시
     */
    @Transactional
    public boolean uploadFile(MultipartHttpServletRequest multiRequest, BoardEntity board) throws Exception {
        if (board == null) {
            throw new NullPointerException("Empty BOARD_ID.");
        }

        // 파라미터 이름을 키로 파라미터에 해당하는 파일 정보를 값으로 하는 Map을 가져온다.
        Map<String, MultipartFile> files = multiRequest.getFileMap();

        // files.entrySet()의 요소를 읽어온다.
        Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();

        MultipartFile mFile;

        String savaFilePath = "", randomFileName = "";

        Calendar cal = Calendar.getInstance();

        List<Long> resultList = new ArrayList<>();

        while (itr.hasNext()) {
            Entry<String, MultipartFile> entry = itr.next();
            mFile = entry.getValue();

            int fileSize = (int) mFile.getSize();

            if (fileSize > 0) {
                String filePath = "C:\\Users\\bthdu\\eclipse-workspace\\uploadFiles\\";

                // 파일 업로드 경로 + 현재 년월(월별관리)
                filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR))
                        + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);
                randomFileName = "FILE_" + RandomStringUtils.random(8, 0, 0, false, true, null, new SecureRandom());

                String realFileName = mFile.getOriginalFilename();
                String fileExt = realFileName.substring(realFileName.lastIndexOf(".") + 1);
                String saveFileName = randomFileName + "." + fileExt;
                String saveFilePath = filePath + File.separator + saveFileName;

                File filePyhFolder = new File(filePath);

                if (!filePyhFolder.exists()) {
                    // 부모 폴더까지 포함하여 경로에 폴더를 만든다.
                    if (!filePyhFolder.mkdirs()) {
                        throw new Exception("File.mkdir() : Fail.");
                    }
                }

                File saveFile = new File(saveFilePath);

                // saveFile이 File이면 true, 아니면 false
                // 파일명이 중복일 경우 파일명(1).확장자, 파일명(2).확장자와 같은 형태로 생성한다.
                if (saveFile.isFile()) {
                    boolean _exist = true;
                    int index = 0;

                    // 동일한 파일명이 존재하지 않을 때까지 반복한다.
                    while (_exist) {
                        index++;
                        saveFileName = randomFileName + "(" + index + ")." + fileExt;
                        String dictFile = filePath + File.separator + saveFileName;
                        _exist = new File(dictFile).isFile();

                        if (!_exist) {
                            savaFilePath = dictFile;
                        }
                    }

                    mFile.transferTo(new File(savaFilePath));
                } else {
                    // 생성한 파일 객체를 업로드 처리하지 않으면 임시파일에 저장된 파일이 자동적으로 삭제되기 때문에 transferTo(File f) 메서드를 이용해서 업로드 처리한다.
                    mFile.transferTo(saveFile);
                }

                BoardFileEntity boardFileEntity = BoardFileEntity.builder()
                        .board(board)
                        .origFileName(realFileName)
                        .saveFileName(saveFileName)
                        .fileSize(fileSize)
                        .fileExt(fileExt)
                        .filePath(filePath)
                        .deleteYn("N")
                        .build();

                resultList.add(boardFileRepository.save(boardFileEntity).getId());
            }
        }

        return (files.size() == resultList.size());
    }

    /**
     * 삭제 대상 파일들의 deleteYn 값을 업데이트하는 메서드
     *
     * @param deleteIdList 삭제 대상 파일 ID 목록
     * @return 업데이트된 행 수
     * @throws Exception 예외 발생 시
     */
    public int updateDeleteYn(Long[] deleteIdList) throws Exception {
        return boardFileRepository.updateDeleteYn(deleteIdList);
    }

    /**
     * 삭제 대상 게시글의 파일들의 deleteYn 값을 업데이트하는 메서드
     *
     * @param boardIdList 삭제 대상 게시글 ID 목록
     * @return 업데이트된 행 수
     * @throws Exception 예외 발생 시
     */
    public int deleteBoardFileYn(Long[] boardIdList) throws Exception {
        return boardFileRepository.deleteBoardFileYn(boardIdList);
    }
}