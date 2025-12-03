import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.awt.event.*;
import java.awt.Font;

public class ProfileWindow implements ActionListener{
    private boolean isAdmin;
    private String user;
    private String name;
    private JFrame frame;
    private JButton retButton;

    public ProfileWindow(String id, boolean admin){
        frame = new JFrame("Profile Window");
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
                if(name.equals("")){
                    name = "N/A";
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
                if(name.equals("")){
                    name = "N/A";
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
        JLabel lbl2 = new JLabel("");
        JLabel lbl3 = new JLabel("");
        lbl2.setText(user);
        lbl3.setText(name);
        retButton = new JButton("Return");
        retButton.addActionListener(this);

        Font font = new Font("SansSerif", Font.PLAIN, 42);
        lbl1.setFont(font);
        lbl2.setFont(font);
        lbl3.setFont(font);
        retButton.setFont(new Font("SansSerif", Font.PLAIN, 24));
        lbl1.setHorizontalAlignment(SwingConstants.CENTER);
        lbl2.setHorizontalAlignment(SwingConstants.CENTER);
        lbl3.setHorizontalAlignment(SwingConstants.CENTER);

        lbl1.setBounds(250,150,300,50);
        lbl2.setBounds(250,225,300,50);
        lbl3.setBounds(250,300,300,50);
        retButton.setBounds(300,375,200,50);

        frame.setSize(800,600);
        frame.setLayout(null);
        frame.add(lbl1);
        frame.add(lbl2);
        frame.add(lbl3);
        frame.add(retButton);
        frame.setVisible(true);
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
