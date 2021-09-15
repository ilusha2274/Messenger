package repository;

import java.util.ArrayList;

public class CollectionUserRepository implements UserRepository{

    private ArrayList<User> users = new ArrayList<>();
    private String status;

    @Override
    public boolean addUser(User user,String twoPassword) {
        if (!(findEmailUser(user.getEmail())) && checkPassword(user.getPassword(),twoPassword)){
            users.add(user);
            return true;
        }
        return false;
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findEmailUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                status = "email занят";
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkPassword(String password, String twoPassword) {
        if (password.equals(twoPassword)){
            return true;
        }else {
            status = "Пароли не совпадают";
            return false;
        }
    }

    @Override
    public User logInUser(String email, String password) {
        for (User user1 : users){
            if(email.equals(user1.getEmail())){
                if(password.equals(user1.getPassword())){
                    return user1;
                }else {
                    status = "Неверное имя пользователя или пароль";
                }
            }else {
                status = "Неверное имя пользователя или пароль";
            }
        }
        return null;
    }

    @Override
    public String getStatus() {
        return status;
    }
}
