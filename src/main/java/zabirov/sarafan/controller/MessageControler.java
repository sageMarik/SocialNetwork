package zabirov.sarafan.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zabirov.sarafan.domain.Message;
import zabirov.sarafan.repo.MessageRepo;

import java.util.List;

@RestController
@RequestMapping("message")
public class MessageControler {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageControler(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list(){
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return  messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @RequestBody Message message,
            @PathVariable("id") Message messageFromDb
    ){
        BeanUtils.copyProperties(message, messageFromDb, "id");

        return messageRepo.save(message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messageRepo.delete(message);
    }

}
