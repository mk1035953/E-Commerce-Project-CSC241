import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.awt.event.*;

public class ProfileWindow implements ActionListener{
    private boolean isAdmin;
    private String user;
    private String name;
    private JFrame frame;
    private JButton retButton;

    public ProfileWindow(String id, boolean admin){
        isAdmin = admin;
        user = id;
        if(admin){
            try {
                Scanner sc = new Scanner(new File("AdminLogin.csv"));
                while(sc.hasNextLine()){
                    String[] strs = sc.nextLine().split(",");
                    if(strs[0].equals(user)){
                        name = strs[2];
                    }
                }
            } catch (Exception e) {
            }
        }else{
            try {
                Scanner sc = new Scanner(new File("CustomerLogin.csv"));
                while(sc.hasNextLine()){
                    String[] strs = sc.nextLine().split(",");
                    if(strs[0].equals(user)){
                        name = strs[2];
                    }
                }
            } catch (Exception e) {
            }
        }

        JLabel lbl1 = new JLabel();
        if(isAdmin){
            lbl1.setText("Admin");
        }
        else{
            lbl1.setText("Customer");
        }
        JLabel lbl2 = new JLabel(user);
        JLabel lbl3 = new JLabel(name);
        retButton = new JButton("Return");
        retButton.addActionListener(this);

        lbl1.setBounds(300,200,200,50);
        lbl2.setBounds(300,250,200,50);
        lbl3.setBounds(300,300,200,50);
        retButton.setBounds(300,350,200,50);

        frame.setSize(800,600);
        frame.add(lbl1);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(retButton)){
            if(isAdmin){
                //AdminPanel panel = new AdminPanel(user)
            }
            else{
                CustomerPanel panel = new CustomerPanel(user);
            }
            frame.dispose();
        }
    }
}
