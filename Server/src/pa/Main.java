package pa;

import socialNetwork.Person;
import socialNetwork.SocialNetwork;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SocialNetwork.register("Ana");
        SocialNetwork.register("Ion");
        SocialNetwork.register("Gigel");
        SocialNetwork.register("Irina");
        SocialNetwork.register("Jean");
        SocialNetwork.register("Matei");

        try {
            new Server();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
