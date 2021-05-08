package pa;

public class Main {

    public static void main(String[] args) {
	    Client client = new Client("127.0.0.1", 3000);
	    client.request();
    }
}
