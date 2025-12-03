import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;

public class OrderPanel implements ActionListener{
    private JScrollPane scrollPanel;
    private JTable table;
    private JComboBox<String> ordersMenu;
    private ArrayList<String> orderNames;
    private ArrayList<Order> orders;
    private boolean isAdmin;

    public OrderPanel(String user, boolean admin){
        try {
            Scanner sc = new Scanner(new File("Orders.csv"));
            
        } catch (Exception e) {
        }

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(ordersMenu)){

        }
    }
}
