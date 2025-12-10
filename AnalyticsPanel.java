import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Font;

public class AnalyticsPanel implements ActionListener{
    private JFrame frame = new JFrame();
    private JScrollPane scrollPane;
    private JTable table;
    private Order avgOrder;
    private JButton retButton;
    private String user;

    //Constructor
    public AnalyticsPanel(String username){
        user = username;
        ArrayList<Order> orders = new ArrayList<>();

        try {
        Scanner sc = new Scanner(new File("Orders.csv"));
        int count = 0;
        while(sc.hasNextLine()){
            String[] strs = sc.nextLine().split(",");
            if(strs.length==1){break;}
            Order order = lineToOrder(strs);
            orders.add(order);
            count++;
        }


        for(int i = 0; i<orders.size(); i++){

        }

        //Setup Swing stuff
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(null);

        // Table column names
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};

        // Table data (rows)
        ArrayList<Item> Cart = avgOrder.getCart();
        String[][] strs = new String[Cart.size()][5];
        for(int i = 0;i<Cart.size();i++){
            Item product = Cart.get(i);
            strs[i][0] = product.getID();
            strs[i][1] = product.getName();
            strs[i][2] = product.getCategory();
            strs[i][3] = "$" + product.getPrice();
            strs[i][4] = Integer.toString(product.getItemsLeft());
        }
        //Set up Table
        table = new JTable(strs, columnNames);
        scrollPane = new JScrollPane(table);
        table.setEnabled(false);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        //add the parts
        frame.add(scrollPane);

        frame.setVisible(true);
            
        } catch (FileNotFoundException e) {
        }

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(retButton)){
            AdminPanel panel = new AdminPanel(user);
            frame.dispose();
        }
    }
    public Order lineToOrder(String[] orderLine){
        Order order = new Order(orderLine[0]);
        try {
            for(int i = 2;i<orderLine.length;i+=2){
                String str1 = orderLine[i];
                String str2 = orderLine[i+1];
                Scanner sc = new Scanner(new File("Products.csv"));
                ArrayList<Item> tempCart = order.getCart();
                while(sc.hasNextLine()){
                    String[] item = sc.nextLine().split(",");
                    Item product = new Item(item[0],item[1],item[2],Double.parseDouble(item[3]), Integer.parseInt(str2));
                    if(product.getID().equals(str1)){
                        tempCart.add(product);
                        Order tempOrder = new Order("");
                        tempOrder.setCart(tempCart);
                        if(tempOrder.isValid()){
                            order.setCart(tempCart);
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return order;
    }
}
