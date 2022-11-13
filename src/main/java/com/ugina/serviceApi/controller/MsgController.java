package com.ugina.serviceApi.controller;

import com.ugina.serviceApi.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MsgController {
    public List<Map<String, String>> msg = new ArrayList<Map<String, String>>(){
        {
            add(new HashMap<String, String>() {{
                put("id", "1");
                put("text", "First str");
            }});
            add(new HashMap<String, String>() {{
                put("id", "2");
                put("text", "Second str");
            }});
            add(new HashMap<String, String>() {{
                put("id", "3");
                put("text", "Third str");
            }});
        }
    };
    @GetMapping
    public List<Map<String, String>> list(){
        return msg;
    }
    @GetMapping("{id}")
    public Map<String, String> getByIndex(@PathVariable String id){
         return msg.stream().filter(msg->msg.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }
}
