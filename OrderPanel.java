import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Font;

public class OrderPanel implements ActionListener{
    private JFrame frame = new JFrame();
    private JScrollPane scrollPane;
    private JTable table;
    private JComboBox<String> ordersMenu;
    private ArrayList<String> orderNames;
    private ArrayList<Order> orders;
    private JButton analyticsButton;
    private boolean isAdmin;
    private JButton retButton;
    private String user;
    private JLabel userLabel;

    public static void main(String[] args){
        CustomerPanel panel = new CustomerPanel("User");
    }
    //Constructor
    public OrderPanel(String username, boolean admin){
        isAdmin = admin;
        user = username;
        orderNames = new ArrayList<>();
        orders = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("Orders.csv"));
            if(isAdmin){
                int count = 0;
                while(sc.hasNextLine()){
                    String[] strs = sc.nextLine().split(",");
                    if(strs.length==1){break;}
                    Order order = lineToOrder(strs);
                    orders.add(order);
                    count++;
                    orderNames.add("Order " + count);
                }
            }
            else{
                int count = 0;
                while(sc.hasNextLine()){
                    String[] strs = sc.nextLine().split(",");
                    if(strs.length!=1&&strs[0].equals(user)){
                        Order order = lineToOrder(strs);
                        orders.add(order);
                        count++;
                        orderNames.add("Order " + count);
                    }
                }
            }

            //Setup Swing stuff
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(null);

        // Table column names
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};

        // Table data (rows)
        ArrayList<Item> Cart = orders.get(0).getCart();
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
        scrollPane.setBounds(10, 100, 1200, 600);

        //Add the Order ComboBox
        String[] ordersArray = new String[orderNames.size()];
        for(int i = 0; i<ordersArray.length;i++){
            ordersArray[i] = orderNames.get(i);
        }
        ordersMenu = new JComboBox<>(ordersArray);
        ordersMenu.setBounds(10,40,300,30);
        ordersMenu.addActionListener(this);

        retButton = new JButton("return");
        retButton.setBounds(1300,750,160,50);
        retButton.addActionListener(this);

        userLabel = new JLabel(orders.get(0).getUser());
        userLabel.setBounds(1400,30,100,50);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        //Add analytics button(Admin Only)
        analyticsButton = new JButton("Analytics");
        analyticsButton.addActionListener(this);
        analyticsButton.setBounds(0,0,0,0);

        //add the parts
        frame.add(scrollPane);
        if(isAdmin){
            frame.add(analyticsButton);//Admin-Only feature
        }
        frame.add(ordersMenu);
        frame.add(retButton);
        frame.add(userLabel);

        frame.setVisible(true);
            
        } catch (FileNotFoundException e) {
        }

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(ordersMenu)){
            int ind = ordersMenu.getSelectedIndex();
            userLabel.setText(orders.get(ind).getUser());
            ArrayList<Item> cart = orders.get(ind).getCart();
            String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
            String[][] strs = new String[cart.size()][5];
            for(int i = 0;i<cart.size();i++){
                Item product = cart.get(i);
                strs[i][0] = product.getID();
                strs[i][1] = product.getName();
                strs[i][2] = product.getCategory();
                strs[i][3] = "$" + product.getPrice();
                strs[i][4] = Integer.toString(product.getItemsLeft());
            }

            frame.remove(scrollPane);
            frame.setVisible(false);
            table = new JTable(strs, columnNames);
            scrollPane = new JScrollPane(table);
            table.setEnabled(false);
            table.setRowHeight(25);
            table.setFont(new Font("SansSerif", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
            scrollPane.setBounds(10, 100, 1200, 600);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
        if(e.getSource().equals(retButton)){
            if(isAdmin){
                AdminPanel panel = new AdminPanel(user);
                frame.dispose();
            }
            else{
                CustomerPanel panel = new CustomerPanel(user);
                frame.dispose();
            }
        }
        if(e.getSource().equals(analyticsButton)){

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
