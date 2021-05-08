package socialNetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private List<Person> friends = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    public Person(String name){
        this.name = name;
    }

    public boolean addFriend(Person friend){
        for(Person p : friends){
            if(p.equals(friend))
                return false;
        }
        if(friend.equals(this))
            return false;
        friends.add(friend);
        friend.addFriend(this);
        return true;
    }

    public String friendsList(){
        String list = "";

        for(Person p : friends){
            list = list + p.getName() + "@#";
        }

        return list;
    }

    public void addMessage(Person from, String content){
        messages.add(new Message(from, content));
    }

    public void sendMessage(String message){
        for (Person p : friends){
            p.addMessage(this, message);
        }
    }

    public String messages(){
        String msg = "";
        for(Message m : messages){
            msg = msg + m + "@#";
        }
        return msg;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.getName());
    }
}
