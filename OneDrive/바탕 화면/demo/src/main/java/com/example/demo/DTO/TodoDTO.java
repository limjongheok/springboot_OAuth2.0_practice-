package com.example.demo.DTO;

import com.example.demo.Entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private  String title;
    private boolean done;

    // entity 를 받아 dto로 변경
    public TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title= entity.getTitle();
        this.done = entity.isDone();
        //userID 는 스피링 시큐리티를 이용해 인증 구현  // 숨길수 있음 숨겨야하는 id
    }
    public static TodoEntity todoEntity(final  TodoDTO dto){
        return TodoEntity.builder().id(dto.getId()).title(dto.getTitle()).done(dto.isDone()).build();
    }

}
