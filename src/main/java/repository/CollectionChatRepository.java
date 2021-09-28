package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionChatRepository implements ChatRepository{

    private List<Chat> chats = new ArrayList<>();
    private Map<User, Chat> usersByChat = new HashMap<>();

    @Override
    public List<Chat> findListChatByUser(User user) {
        List<Chat> chatsUser = new ArrayList<>();
        for (int i =0;i<chats.size();i++){
            Chat chat = chats.get(i);
            if (chat.getUser1() == user || chat.getUser2() == user){
                chatsUser.add(chat);
            }
        }
        return chatsUser;
    }

    @Override
    public Chat getByNumberChat(int i) {
        return null;
    }

    @Override
    public Chat addChat(User user1, User user2) {
        Chat chat = new Chat(user1, user2);
        chats.add(chat);
        usersByChat.put(user1,chat);
        usersByChat.put(user2,chat);
        return chat;
    }
}
