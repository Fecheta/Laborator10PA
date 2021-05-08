package socialNetwork;

import java.util.ArrayList;
import java.util.List;

public class SocialNetwork {
    private static List<Person> users = new ArrayList<>();

    public static boolean register(String name){
        boolean valid = true;

        for(Person p : users){
            if(p.getName().compareTo(name) == 0) {
                valid = false;
                break;
            }
        }

        if(valid == true){
            users.add(new Person(name));
        }

        return valid;
    }

    public static Person login(String name){
        Person user = null;

        for(Person p : users){
            if(p.getName().compareTo(name) == 0){
                user = p;
                break;
            }
        }

        return user;
    }

    public static Person find(String name){
        for(Person p : users){
            if(p.getName().compareTo(name) == 0){
                return p;
            }
        }
        return null;
    }
}
