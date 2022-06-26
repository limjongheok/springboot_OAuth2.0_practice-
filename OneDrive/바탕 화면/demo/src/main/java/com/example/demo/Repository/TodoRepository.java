package com.example.demo.Repository;

import com.example.demo.DTO.TodoDTO;
import com.example.demo.Entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
   List<TodoEntity> findByUserID(String userID);

}
