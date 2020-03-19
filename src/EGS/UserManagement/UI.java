package EGS.UserManagement;

import EGS.UserManagement.UserManagement;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    public UI(String fileName) {
        File inFile = new File(fileName);
        if (inFile.exists()) {
            users = new UserManagement();
            try {
                users.deserializeUsersFromFile(inFile);
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    class MenuChoose {
        private MenuElement element;
        private String parameters;

        public MenuChoose(MenuElement element, String parameters) {
            this.element = element;
            this.parameters = parameters;
        }

        public MenuChoose(MenuElement element) {
            this.element = element;
            this.parameters = "";
        }

        public MenuElement getElement() {
            return element;
        }

        public void setElement(MenuElement element) {
            this.element = element;
        }

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }
    }

    public enum MenuElement {ADD, REMOVE, LIST, EXIT}

    ;

    private UserManagement users = null;

    public void showMenu() {
        System.out.println("Available commands: \n");

        for (MenuElement m : MenuElement.values())
            System.out.println(m.name());
    }

    public MenuChoose getChoose() {
        Scanner scanner = new Scanner(System.in);
        String tmpCommand = scanner.nextLine();

        MenuElement element = null;
        String parameters = "";

        try {
            element = MenuElement.valueOf(tmpCommand.toUpperCase());

            switch (element) {
                case ADD: {
                    System.out.print("Enter name: ");

                    String name = null;
                    try {
                        name = scanner.nextLine();
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid argument");
                    }

                    System.out.print("Enter age: ");

                    int age = 0;
                    try {
                        age = scanner.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid argument");
                    }

                    parameters = name + " " + age;
                    break;
                }

                case REMOVE: {
                    System.out.print("Enter user id for remove: ");
                    try {
                        int id = scanner.nextInt();
                        parameters = String.valueOf(id);
                    }
                    catch (InputMismatchException ex) {
                        System.out.println("Invalid argument");
                    }
                    break;
                }
                case LIST: {
                    break;
                }
                case EXIT: {
                    break;
                }
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Wrong command.");
        }

        return new MenuChoose(element, parameters);
    }

    public void process() {
        while (true) {
            showMenu();
            MenuChoose mc = getChoose();

            switch (mc.getElement()) {
                case ADD: {

                    String params[] = mc.getParameters().split(" ");
                    String name = params[0];
                    int age = Integer.valueOf(params[1]);

                    users.addUser(name, age);
                    break;
                }
                case REMOVE: {

                    int id = Integer.valueOf(mc.getParameters());
                    users.removeUser(id);

                    break;
                }
                case LIST: {
                    users.listUsers();
                    break;
                }
                case EXIT: {
                    try {
                        users.serializeUsersToFile(new File("out.dat"));
                    }
                    catch(IOException ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                    System.exit(0);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        UI ui = new UI("out.dat");
        ui.process();
    }
}
