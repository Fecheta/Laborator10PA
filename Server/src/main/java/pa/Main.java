package pa;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import socialNetwork.Person;
import socialNetwork.Sftp;
import socialNetwork.SocialNetwork;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws JSchException, SftpException, IOException {
//        Person p1 = new Person("Ana");
//        Person p2 = new Person("Ion");
//        Person p3 = new Person("Gigel");
//        Person p4 = new Person("Irina");
//        Person p5 = new Person("Jean");
//        Person p6 = new Person("Matei");

        SocialNetwork.register("Ana");
        SocialNetwork.register("Ion");
        SocialNetwork.register("Gigel");
        SocialNetwork.register("Irina");
        SocialNetwork.register("Jean");
        SocialNetwork.register("Matei");
        SocialNetwork.register("Test");
        SocialNetwork.register("Test");

        try {
            new Server();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String s = "SftpTest5";
        SocialNetwork.printFriendships();
        try {
            SocialNetwork.representation(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sftp test = new Sftp();
      test.uploadFiles(s);
//        test.download();
    }
}
