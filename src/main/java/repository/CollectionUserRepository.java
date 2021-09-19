package repository;

import exception.PasswordMismatchException;
import exception.WrongEmailException;
import exception.WrongLoginPasswordException;

import java.util.ArrayList;

public class CollectionUserRepository implements UserRepository{

    private ArrayList<User> users = new ArrayList<>();

    @Override
    public User addUser(User user,String twoPassword) throws PasswordMismatchException, WrongEmailException {
        if (!(findEmailUser(user.getEmail())) && checkPassword(user.getPassword(),twoPassword)){
            users.add(user);
            return user;
        }
        return null;
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean findEmailUser(String email) throws WrongEmailException {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                throw new WrongEmailException("email занят");
            }
        }
        return false;
    }

    @Override
    public boolean checkPassword(String password, String twoPassword) throws PasswordMismatchException {
        if (password.equals(twoPassword)){
            return true;
        }else {
            throw new PasswordMismatchException("Пароли не совпадают");
        }
    }

    @Override
    public User logInUser(String email, String password) throws WrongLoginPasswordException {
        for (User user : users){
            if(email.equals(user.getEmail())&& password.equals(user.getPassword())){
                    return user;
            }else {
                throw new WrongLoginPasswordException("Неверное имя пользователя или пароль");
            }
        }
        return null;
    }
}
