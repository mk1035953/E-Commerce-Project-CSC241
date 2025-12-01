import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ErrorPanel implements ActionListener{
    private JFrame frame = new JFrame("Login Window");
    private JLabel lbl1 = new JLabel("Incorrect UserName");
    private JLabel lbl2 = new JLabel("or Password");
    private JButton loginButton = new JButton("Retry?");
    public static void main(String[] args){
        Login login = new Login();
    }
    public ErrorPanel(){
        
        lbl1.setFont(new Font(Font.SANS_SERIF,Font.TYPE1_FONT,36));
        lbl2.setFont(new Font(Font.SANS_SERIF,Font.TYPE1_FONT,36));

        int start = 145;
        lbl1.setBounds(250,start,400,40);start+=50;
        lbl2.setBounds(250,start,400,40);start+=50;
        loginButton.setBounds(325,start,150,50);

        loginButton.addActionListener(this);

        frame.setLayout(null);
        frame.add(lbl1);
        frame.add(lbl2);
        frame.add(loginButton);

        int sizeScale = 200;
        frame.setBounds(0, 0, 4*sizeScale, 3*sizeScale);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(loginButton)){
            Login login = new Login();
            frame.dispose();
        }
    }
}
