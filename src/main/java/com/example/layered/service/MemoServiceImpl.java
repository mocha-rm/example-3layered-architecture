package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import com.example.layered.repository.MemoRepository;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        Memo savedMemo = repository.saveMemo(memo);

        return new MemoResponseDto(savedMemo);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return repository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {

        Memo memoById = repository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does no exist id = " + id);
        }
        return new MemoResponseDto(memoById);
    }

    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        Memo memoById = repository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does no exist id = " + id);
        }

        if (title == null || contents == null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }

        memoById.update(title, contents);

        return new MemoResponseDto(memoById);
    }

    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {
        Memo memoById = repository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does no exist id = " + id);
        }

        if (title == null || contents != null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }

        memoById.updateTitle(title);

        return new MemoResponseDto(memoById);
    }

    @Override
    public void deleteMemo(Long id) {

        Memo memoById = repository.findMemoById(id);

        if (memoById == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repository.deleteMemo(id);
    }
}
