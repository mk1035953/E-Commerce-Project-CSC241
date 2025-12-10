import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class ProductPanel implements ActionListener{
    private JFrame frame = new JFrame("Product Panel");
    private JTextField idEntry;
    private JTextField nameEntry;
    private JTextField categoryEntry;
    private JTextField priceEntry;
    private JTextField quantityEntry;
    private JLabel infoLabel;
    private JButton enterButton;
    private JButton retButton;
    private String user;
    

    public static void main(String[] args){
        AdminPanel panel = new AdminPanel("Admin");
    }
    //Constructor
    public ProductPanel(String username){
        frame = new JFrame("Product Panel");
        frame.setLayout(null);
        frame.setSize(800, 600);
        user = username;

        int start = 10;
        Font font = new Font("SansSerif", Font.PLAIN, 16);

        //id swing parts
        JLabel idLabel = new JLabel("Product Id");
        idEntry = new JTextField();
        idLabel.setBounds(30,start,200,30);start+=31;
        idEntry.setBounds(30,start,350,50);start+=51;

        //name swing parts
        JLabel nameLabel = new JLabel("Product Name");
        nameEntry = new JTextField();
        nameLabel.setBounds(30,start,200,30);start+=31;
        nameEntry.setBounds(30,start,350,50);start+=51;

        //category swing parts
        JLabel categoryLabel = new JLabel("Product Category");
        categoryEntry = new JTextField();
        categoryLabel.setBounds(30,start,200,30);start+=31;
        categoryEntry.setBounds(30,start,350,50);start+=51;

        //price swing parts
        JLabel priceLabel = new JLabel("Product Price");
        priceEntry = new JTextField();
        priceLabel.setBounds(30,start,200,30);start+=31;
        priceEntry.setBounds(30,start,350,50);start+=51;

        //quantity swing parts
        JLabel quantityLabel = new JLabel("Product Amount");
        quantityEntry = new JTextField();
        quantityLabel.setBounds(30,start,200,30);start+=31;
        quantityEntry.setBounds(30,start,350,50);start+=51;

        //other parts
        enterButton = new JButton("Enter");
        infoLabel = new JLabel("Info");
        retButton = new JButton("Return");
        enterButton.setBounds(30,start+50,200,50);
        retButton.setBounds(500,start+50,200,50);
        infoLabel.setBounds(500,200,300,50);
        enterButton.addActionListener(this);
        retButton.addActionListener(this);

        //SetFonts
        idLabel.setFont(font);
        idEntry.setFont(font);
        nameLabel.setFont(font);
        nameEntry.setFont(font);
        categoryLabel.setFont(font);
        categoryEntry.setFont(font);
        priceLabel.setFont(font);
        priceEntry.setFont(font);
        quantityLabel.setFont(font);
        quantityEntry.setFont(font);
        enterButton.setFont(font);
        infoLabel.setFont(font);
        retButton.setFont(font);

        //Add Swing Parts
        frame.add(idLabel);
        frame.add(idEntry);
        frame.add(nameLabel);
        frame.add(nameEntry);
        frame.add(categoryLabel);
        frame.add(categoryEntry);
        frame.add(priceLabel);
        frame.add(priceEntry);
        frame.add(quantityLabel);
        frame.add(quantityEntry);
        frame.add(enterButton);
        frame.add(infoLabel);
        frame.add(retButton);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(retButton)){
            AdminPanel panel = new AdminPanel(user);
            frame.dispose();
        }
        if(e.getSource().equals(enterButton)){
            if(idEntry.getText().equals("")){
                infoLabel.setText("Enter an ID");
            }
            else if(nameEntry.getText().equals("")){
                infoLabel.setText("Enter a name");
            }
            else if(categoryEntry.getText().equals("")){
                infoLabel.setText("Enter a Category");
            }
            else if(priceEntry.getText().equals("")){
                infoLabel.setText("Enter a Price");
            }
            else if(quantityEntry.getText().equals("")){
                infoLabel.setText("Enter the quantity");
            }
            else{
                String id = idEntry.getText();
                String name = nameEntry.getText();
                String category = categoryEntry.getText();
                double price = 0;
                int quantity = 0;
                try {
                    price = Double.parseDouble(priceEntry.getText());
                } catch (Exception exc) {
                    infoLabel.setText("Enter a valid price");
                }
                try {
                    quantity = Integer.parseInt(quantityEntry.getText());
                } catch (Exception exc) {
                    infoLabel.setText("Enter a valid quantity");
                }

                boolean isValidId = true;
                try {
                    Scanner sc = new Scanner(new File("Products.csv"));
                    while(sc.hasNextLine()){
                        String[] strs = sc.nextLine().split(",");
                        if(id.equals(strs[0])){
                            isValidId = false;
                            infoLabel.setText("Choose a valid ID");
                        }
                    }
                } catch (FileNotFoundException exc) {
                    isValidId = false;
                }

                if(price!=0&&quantity!=0&&isValidId){
                    Item item = new Item(id,name,category,price,quantity);
                    String filename = "Products.csv";
                    try(FileWriter writer = new FileWriter(filename, true)){
                        writer.write(item.getID()+","+item.getName()+","+item.getCategory()+","+item.getPrice()+","+item.getItemsLeft());
                        writer.write("\n");
                    } 
                    catch (Exception exc){exc.printStackTrace();}
                    infoLabel.setText("Added Product");
                }
            }
            //updateFrame();
        }
    }
    public void updateFrame(){frame.setVisible(false);frame.setVisible(true);}
}
