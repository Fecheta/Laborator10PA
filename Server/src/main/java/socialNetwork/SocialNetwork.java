package socialNetwork;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SocialNetwork {
    private static List<Person> users = new ArrayList<>();

    private static List<Person> friend1 = new ArrayList<>();
    private static List<Person> friend2 = new ArrayList<>();

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

    public static boolean friendship(Person person1, Person person2){
        friend1.add(person1);
        friend2.add(person2);

        boolean result;
        result = person1.addFriend(person2);

        return result;
    }

    public static Person find(String name){
        for(Person p : users){
            if(p.getName().compareTo(name) == 0){
                return p;
            }
        }
        return null;
    }

    public static void printFriendships(){
        String res = "";
        for(int i = 0; i < friend1.size(); i++){
            res = friend1.get(i).getName() + " : " + friend2.get(i).getName();
            System.out.println(res);
        }
    }

    public static void representation(String filename) throws IOException {
        JFrame frame = new JFrame();
        frame.setSize(400,400);

        File imgFile = new File("images/"+filename+".png");
        DefaultUndirectedGraph<Person, DefaultEdge> g = new DefaultUndirectedGraph<>(DefaultEdge.class);

        for(Person u : users){
            g.addVertex(u);
        }

        for(int i=0; i<friend1.size(); i++){
            g.addEdge(friend1.get(i), friend2.get(i));
        }

        JGraphXAdapter<Person, DefaultEdge> graphAdapter = new JGraphXAdapter<>(g);
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        mxGraphModel graphModel = (mxGraphModel)graphComponent.getGraph().getModel();
        Collection<Object> cells = graphModel.getCells().values();
        mxUtils.setCellStyles(graphComponent.getGraph().getModel(), cells.toArray(), mxConstants.STYLE_ENDARROW, mxConstants.NONE);
        frame.getContentPane().add(graphComponent);

        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);

        String html;
        html = "<html>" +
                "<body>" +
                    "<img src ="+"\""+filename+".png\">" +
                "</body>" +
                "</html>";

        File f = new File("images/"+filename+".html");
        BufferedWriter br = new BufferedWriter(new FileWriter(f));
        br.write(html);
        br.close();
    }
}
