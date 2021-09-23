package repository;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Message> messages = new ArrayList<>();
    private String name;

    public List<Message> getMessages() {
        return messages;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void addMessage (String text){
        Message message = new Message(text);
        messages.add(message);
    }
}

