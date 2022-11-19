package com.ugina.serviceApi.controller;

import com.ugina.serviceApi.exceptions.NotFoundException;
import com.ugina.serviceApi.repo.MessageRepo;
import domain.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MsgController {
    private final MessageRepo messageRepo;

    @Autowired
    public MsgController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list(){
        return messageRepo.findAll();
    }
    @GetMapping("{id}")
    public Message getByIndex(@PathVariable("id") Message message){
         return message;
    }

    @PostMapping
    public Message addOne(@RequestBody Message message){
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message msgFromDb,
            @RequestBody Message message){
        BeanUtils.copyProperties(message, msgFromDb, "id");
        return messageRepo.save(message);
    }
    @DeleteMapping("{id}")
    public void deleteMsg(@PathVariable("id") Message message){
        messageRepo.delete(message);
    }
}
    //fetch ('/message', {method: 'POST', headers:{'Content-Type': 'application/json'}, body: JSON.stringify({text: 'Fourth message'})}).then(console.log)
