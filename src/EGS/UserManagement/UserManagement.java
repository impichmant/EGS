package EGS.UserManagement;

import EGS.UserManagement.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserManagement {

    private List<User> users = new ArrayList<>();
    private static int userID = 1000;

    public void addUser(String name, int age) {
        if (name != null && age > 0)
            addUser(new User(userID++, name, age));
    }

    private void addUser(User user) {
        if (user != null)
            users.add(user);
    }

    public void removeUser(int id) {
        if (id > 0)
                for (int i = 0; i < users.size(); i++)
                    if (users.get(i).getId() == id)
                        users.remove(i);
    }

    public void listUsers() {
        if (users.size() > 0)
            for (User user : users)
                System.out.println(user.toString());
    }

    public void serializeUsersToFile(File file) throws IOException {
        if (file != null && users != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileOutputStream);

            objectStream.writeObject(users);
            fileOutputStream.close();
            objectStream.close();
        }
    }

    public void deserializeUsersFromFile(File file) throws IOException, ClassNotFoundException {
        ArrayList<User> usersTmp = null;
        Object tmp = null;

        if (file != null) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            tmp = objectInputStream.readObject();
            usersTmp = (ArrayList<User>) tmp;

            users = usersTmp;

            fileInputStream.close();
            objectInputStream.close();
        }
    }
}

