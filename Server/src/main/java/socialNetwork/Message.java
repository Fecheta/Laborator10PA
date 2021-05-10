package socialNetwork;

public class Message {
    private Person from;
    private String content;

    public Message(Person person, String content){
        this.from = person;
        this.content = content;
    }

    @Override
    public String toString() {
        return "[" + from.getName() + "] " + content;
    }
}
