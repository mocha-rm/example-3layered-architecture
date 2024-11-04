package com.example.layered.dto;

import com.example.layered.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor //필드 모두를 가지는 생성자를 생성?
public class MemoResponseDto {

    private Long id;
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
    }

//    public MemoResponseDto(Long id, String title, String contents) { @AllArgsConstructor 어노테이션으로 생성하는 것과 동일
//
//    }
}
