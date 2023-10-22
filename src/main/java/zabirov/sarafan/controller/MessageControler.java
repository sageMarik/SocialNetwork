package zabirov.sarafan.controller;

import org.springframework.web.bind.annotation.*;
import zabirov.sarafan.exceptions.NotFoundExeption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageControler {
    private int counter = 4;
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>(){{ put("id", "1"); put("text", "First message");}});
        add(new HashMap<String, String>(){{ put("id", "2"); put("text", "Second message");}});
        add(new HashMap<String, String>(){{ put("id", "3"); put("text", "Third message");}});

    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getMessage(id);
    }

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundExeption::new);
    }


    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));

        messages.add(message);

        return  message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@RequestBody Map<String, String> message, @PathVariable String id ){
        final var messageFromDB = getMessage(id);

        messageFromDB.putAll(message);
        messageFromDB.put("id", id);

        return messageFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        final var message = getMessage(id);

        messages.remove(message);
    }

}
