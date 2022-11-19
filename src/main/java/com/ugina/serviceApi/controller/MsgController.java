package com.ugina.serviceApi.controller;

import com.ugina.serviceApi.repo.MessageRepo;
import com.ugina.serviceApi.domain.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MsgController {
    private final MessageRepo messageRep;

    @Autowired
    public MsgController(MessageRepo messageRepo) {
        this.messageRep = messageRepo;
    }

    @GetMapping
    public List<Message> list(){
        return messageRep.findAll();
    }
    @GetMapping("{id}")
    public Message getByIndex(@PathVariable("id") Message message){
         return message;
    }

    @PostMapping
    public Message addOne(@RequestBody Message message){
        message.setCreationDate(LocalDateTime.now());
        return messageRep.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message msgFromDb,
            @RequestBody Message message){
        BeanUtils.copyProperties(message, msgFromDb, "id");
        return messageRep.save(msgFromDb);
    }
    @DeleteMapping("{id}")
    public void deleteMsg(@PathVariable("id") Message message){
        messageRep.delete(message);
    }
}
    //fetch ('/message', {method: 'POST', headers:{'Content-Type': 'application/json'}, body: JSON.stringify({text: 'Fourth message'})}).then(console.log)
