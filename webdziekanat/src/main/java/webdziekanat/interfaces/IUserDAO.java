package webdziekanat.interfaces;

import webdziekanat.model.User;

import java.util.List;

/**
 * Created by Jakub on 04.02.2015.
 */
public interface IUserDAO {
    public void addUser(User User);

    public boolean deleteUser(int id);

    public void updateUser(User user);

    public User getUserById(int id);

    public User getByUsernameAndPassword(String username, String password);

    public List<User> getAll();
}
