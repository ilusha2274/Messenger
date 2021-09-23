package repository;

public class CollectionChatRepository implements ChatRepository{

    @Override
    public void addChat(User user1, User user2) {
        Chat chat = new Chat();
        user1.setChats(chat);
        user2.setChats(chat);
    }
}
