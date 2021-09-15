package repository;

public interface UserRepository {
    boolean addUser(User user, String twoPassword);
    void removeUser (User user);
    boolean findEmailUser (String email);
    boolean checkPassword (String password, String twoPassword);
    User logInUser (String email, String password);
    String getStatus();
}
