import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Scanner;
import java.io.File;

public class Login implements ActionListener{
    private JFrame frame = new JFrame("Login Window");
    private JLabel lbl1 = new JLabel("Username");
    private JLabel lbl2 = new JLabel("Password");
    private JTextField userEntry = new JTextField("username");
    private JPasswordField passEntry = new JPasswordField("Password");
    private JCheckBox checkBox = new JCheckBox("Are you an Admin?");
    private JButton loginButton = new JButton("Login");
    private boolean isAdmin = false;
    public static void main(String[] args){
        Login login = new Login();
    }
    public Login(){
        
        lbl1.setFont(new Font(Font.SANS_SERIF,Font.TYPE1_FONT,24));
        lbl2.setFont(new Font(Font.SANS_SERIF,Font.TYPE1_FONT,24));

        int start = 145;
        lbl1.setBounds(250,start,200,24);start+=34;
        userEntry.setBounds(250,start,300,50);start+=60;
        lbl2.setBounds(250,start,200,24);start+=34;
        passEntry.setBounds(250,start,300,50);start+=50;
        checkBox.setBounds(250,start,200,30);start+=30;
        loginButton.setBounds(325,start,150,50);

        loginButton.addActionListener(this);
        checkBox.addActionListener(this);

        frame.setLayout(null);
        frame.add(lbl1);
        frame.add(userEntry);
        frame.add(lbl2);
        frame.add(passEntry);
        frame.add(checkBox);
        frame.add(loginButton);

        int sizeScale = 200;
        frame.setBounds(0, 0, 4*sizeScale, 3*sizeScale);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(loginButton)){
            boolean tf = true;
            if(isAdmin){
                try {
                    Scanner sc = new Scanner(new File("AdminLogin.csv"));
                    while(sc.hasNextLine()){
                        String user = userEntry.getText();
                        String pass = "";
                        char[] temp = passEntry.getPassword();
                        for(char c:temp){
                            pass += c;
                        }
                        try {
                            String[] strs = sc.nextLine().split(",");
                            if(strs[0].equals(user)&&strs[1].equals(pass)){
                                //AdminPanel panel = new AdminPanel();
                                frame.dispose();
                                tf = false;
                            }
                        } catch (Exception exc) {

                        }
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
            else{
                try {
                    Scanner sc = new Scanner(new File("CustomerLogin.csv"));
                    while(sc.hasNextLine()){
                        String user = userEntry.getText();
                        String pass = "";
                        char[] temp = passEntry.getPassword();
                        for(char c:temp){
                            pass += c;
                        }
                        try {
                            String[] strs = sc.nextLine().split(",");
                            if(strs[0].equals(user)&&strs[1].equals(pass)){
                                CustomerPanel panel = new CustomerPanel(user);
                                frame.dispose();
                                tf = false;
                            }
                        } catch (Exception exc) {

                        }
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
            if(tf){
                ErrorPanel panel = new ErrorPanel();
                frame.dispose();
            }
        }
        if(e.getSource().equals(checkBox)){
            isAdmin = !isAdmin;
        }
    }
}
