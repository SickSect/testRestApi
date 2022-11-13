package com.ugina.serviceApi.controller;

import com.ugina.serviceApi.exceptions.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MsgController {
    private int counter = 4;
    private List<Map<String, String>> msg = new ArrayList<Map<String, String>>(){
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
         return getInfo(id);
    }

    private Map<String, String> getInfo(String id) {
        return msg.stream().filter(msg -> msg.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> addOne(@RequestBody Map<String, String> info){
        info.put("id", String.valueOf(counter++));
        msg.add(info);
        return info;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> info){
        Map<String, String> msgFromDb = getInfo(info.get("id"));
        msgFromDb.putAll(info);
        msgFromDb.put("id", id);
        return msgFromDb;
    }
    @DeleteMapping("{id}")
    public void deleteMsg(@PathVariable String id){
        Map<String, String> msgToDel = getInfo(id);
        msg.remove(msgToDel);
    }
}
