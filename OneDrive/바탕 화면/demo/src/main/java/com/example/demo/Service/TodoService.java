package com.example.demo.Service;

import com.example.demo.Repository.TodoRepository;
import com.example.demo.Entity.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public List<TodoEntity> create(final  TodoEntity entity){
        validate(entity);
        repository.save(entity);

        log.info("entity id : {} is saved.", entity.getId());
        return repository.findByUserID(entity.getUserID());

    }
    private  void validate(final  TodoEntity todoEntity){
        if(todoEntity== null){
            log.warn("entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if(todoEntity.getUserID() == null){
            log.warn("unkown user.");
            throw  new RuntimeException("Unknow user.");
        }
    }

    public List<TodoEntity> retrieve(final String userID){
        return  repository.findByUserID(userID);
    }
    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        original.ifPresent(todo ->{ // original 이 만약 존재 할시
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });
        return retrieve(entity.getUserID());
    }

    public List<TodoEntity> delete(final  TodoEntity entity){
        validate(entity);
        try{
            repository.delete(entity);
        }catch (Exception e){
            log.error("error deleting entity", entity.getId(),e);
            throw new RuntimeException("error deleting entity"+entity.getId());
        }
        return retrieve(entity.getUserID());
    }


    public String testService(){
        TodoEntity entity = TodoEntity.builder().title("my first todo").build();
        repository.save(entity);
        TodoEntity saveEntity = repository.findById(entity.getId()).get();
        return saveEntity.getTitle();
    }



}
