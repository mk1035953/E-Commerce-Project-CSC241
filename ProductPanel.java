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
        user = username;

        //id swing parts

        //name swing parts

        //category swing parts

        //price swing parts

        //quantity swing parts

        //other parts
        enterButton = new JButton("Enter");

        retButton = new JButton("Return");

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
                        }
                    }
                } catch (FileNotFoundException exc) {
                    isValidId = false;
                }

                if(price!=0&&quantity!=0&&isValidId){
                    Item item = new Item(id,name,category,price,quantity);
                    String filename = "Products.csv";
                    try(FileWriter writer = new FileWriter(filename, true)){
                        writer.write(item.getID()+item.getName()+item.getCategory()+item.getPrice()+item.getItemsLeft());
                        writer.write("\n");
                    } 
                    catch (Exception exc){exc.printStackTrace();}
                }
            }
        }
    }
}
