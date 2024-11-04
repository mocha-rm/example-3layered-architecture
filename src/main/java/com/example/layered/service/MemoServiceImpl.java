package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository repository;

    public MemoServiceImpl(MemoRepository repository) {
        this.repository = repository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto dto) {

        //요청 받은 데이터로 memo 객체 생성 ,ID는 없음(Repopsitory 의 역할이다)
        Memo memo = new Memo(dto.getTitle(), dto.getContents());

        //DB에 저장
        return repository.saveMemo(memo);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return repository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {
        Memo memo =repository.findMemoByIdOrElseThrow(id);
        return new MemoResponseDto(memo);
    }

    @Transactional
    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        if (title == null || contents == null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }

        int updateRow = repository.updateMemo(id, title, contents);

        if (updateRow == 0) { //id 값으로 조회된 결과가 없다면
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does no exist id = " + id);
        }

        Memo memo = repository.findMemoByIdOrElseThrow(id);

        return new MemoResponseDto(memo);
    }

    @Transactional
    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {

        if (title == null || contents != null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }

        int updatedRow = repository.updateTitle(id, title);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does no exist id = " + id);
        }

        Memo memo = repository.findMemoByIdOrElseThrow(id);

        return new MemoResponseDto(memo);
    }

    @Override
    public void deleteMemo(Long id) {

        int deletedRow = repository.deleteMemo(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
