package com.example.demo.Controller;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.DTO.TestRequestBodyDTO;
import com.example.demo.DTO.TodoDTO;
import com.example.demo.Entity.TodoEntity;
import com.example.demo.Service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController(){
        return "hello world";
    }
    @GetMapping("/testGetMapping")
    public String testControllerWithPath(){
        return "hello Wolrd testgetmapping";
    }

    //PathVariable 예제
    // 주소에 있는 값을 가져오는것
    @GetMapping("/{id}")
    public String testControllerWithPathVarialbe(@PathVariable(required = false) int id){
        return "hello world id "+id;
    }

    //RequestParam 예제
    // ?값일시  값을 id에 가져옴  즉 주소에 있는 ?id=  뒤에 값을 가져옴
    @GetMapping("/t2")
    public String testRequestParam(@RequestParam(required = false) int id){
        return "hello wolrd id"+ id;
    }

    //RequestBody 사용법
    // 요청을 json 형태 문자열로 넘겨줌
    // 즉 요청은 JSON 형식이 들어오면 BODY-> RAW-> JSON 형식으로 리턴 됨을 알수 있음

    @GetMapping("/t3")
    public String testRequestBody(@RequestBody TestRequestBodyDTO dto){
        return "hello id" + dto.getId()  + "message : " + dto.getMessage();

    }

    // ResponseBody
    // 복잡한 오브젝트 리턴
    //기존 만든 responseDTO를 이용 해서 return 을 responsdto로
    @GetMapping("/test4")
    public ResponseDTO<String> testControllerResponseBody(){
        List<String> list = new ArrayList<>();
        list.add("hello responsedto");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }

    // ResponseEntity 반환
    // HTTP 의 응답 body 뿐만 아니라 여러 다른 매개변수 , status 나 header를 조작하고 싶을시 사용
    @GetMapping("/test5")
    public ResponseEntity<?> testControllerResponseEntity(){
        List<String> list = new ArrayList<>();
        list.add("error");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(response);
    }
    @Autowired
    private TodoService service;









}
