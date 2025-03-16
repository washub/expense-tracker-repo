package com.expense.ds_service.controller;

import com.expense.ds_service.dto.AIMessageDTO;
import com.expense.ds_service.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/v1/ds")
public class DataSciController {

    private final MessageService messageService;

    public DataSciController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping("/message")
    public ResponseEntity<?> handleMessage(@RequestBody AIMessageDTO message){
        var result = messageService.processMessage(message);
        if(Objects.nonNull(result))
            return new ResponseEntity<>(result, HttpStatus.OK);
        return ResponseEntity.internalServerError().body("Oops something went wrong please re-try");
    }
}
