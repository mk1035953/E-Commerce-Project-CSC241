import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CartPanel implements ActionListener{
    private String user;
    private JFrame frame = new JFrame("Customer Panel");
    String[] filterTypes = {"ID", "Name", "Price", "Category","Quantity"};
    private JComboBox<String> filters = new JComboBox<>(filterTypes);
    private JTable table;
    private JButton profileButton;
    private JRadioButton sortReverse;
    private JRadioButton sortTop;
    private boolean sortMethod = true;
    private ButtonGroup sortButtons;
    private JScrollPane scrollPane;
    private JTextField searchBox;
    private JButton searchButton;
    private JTextField cartEntry;
    private JTextField cartNumEntry;
    private JButton addToCartButton;
    private JButton cartButton;
    private JLabel cartLabel;
    private Order order;
    private JComboBox stateBox;
    private double[] stateTaxes = new double[50];
    private String[] stateNames = new String[50];
    private JLabel totalLabel;

    public static void main(String[] args) {
        CustomerPanel panel = new CustomerPanel("User");
    }
    public CartPanel(Order Order, String username, boolean isAdmin){
        user = username;
        JLabel lbl1 = new JLabel("Filters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(null);
        order = Order;

        // Table column names
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};

        // Table data (rows)
        ArrayList<Item> cart = order.getCart();
        String[][] strs = new String[cart.size()][5];
        for(int i = 0;i<cart.size();i++){
            Item product = cart.get(i);
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

        //Set up ProfileButton
        profileButton = new JButton("Return");

        //Set up search feature
        JLabel searchLabel = new JLabel("Search");
        searchBox = new JTextField("Enter Item Name Here");
        searchBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchButton = new JButton("Search");
        searchLabel.setBounds(10,10,200,30);
        searchButton.setBounds(410,40,100,50);
        searchBox.setBounds(10,40,400,50);
        searchButton.addActionListener(this);
        searchButton.setFont(new Font("SansSerif", Font.PLAIN, 16));

        //Set up sortButtons
        sortReverse = new JRadioButton("Bottom-Top");
        sortTop = new JRadioButton("Top-Bottom");
        sortButtons = new ButtonGroup();
        sortButtons.add(sortTop);
        sortButtons.add(sortReverse);
        sortTop.setSelected(true);
        sortTop.setBounds(1300,50,200,20);
        sortTop.setFont(new Font("SansSerif", Font.PLAIN, 16));
        sortReverse.setSelected(false);
        sortReverse.setFont(new Font("SansSerif", Font.PLAIN, 16));
        sortReverse.setBounds(1300,70,200,20);

        //Setup Total and state features
        try {
            Scanner sc = new Scanner(new File("StateTax.csv"));
            int count = 0;
            while(sc.hasNextLine()){
                String[] strins = sc.nextLine().split(",");
                stateNames[count] = strins[0];
                stateTaxes[count] = Double.parseDouble(strins[1]);
                count++;
            }
        } catch (FileNotFoundException e) {
        }
        stateBox = new JComboBox<>(stateNames);
        stateBox.setBounds(1300,300,200,20);
        stateBox.addActionListener(this);
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setBounds(1300,320,200,30);
        totalLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        //Setup Cart Features
        cartLabel = new JLabel("Check-Out");
        cartLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cartButton = new JButton("Checkout");
        cartButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cartEntry = new JTextField("Example id");
        cartNumEntry = new JTextField("1");
        cartButton.setBounds(1300,750,160,50);
        addToCartButton.setBounds(380,750,160,60);
        cartLabel.setBounds(30,720,300,30);
        cartEntry.setBounds(30,750,300,60);
        cartNumEntry.setBounds(330,750,50,60);
        cartNumEntry.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cartEntry.setFont(new Font("SansSerif", Font.PLAIN, 14));

        //Setup ActionListeners
        sortTop.addActionListener(this);
        sortReverse.addActionListener(this);
        profileButton.addActionListener(this);
        profileButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        filters.addActionListener(this);
        cartButton.addActionListener(this);
        addToCartButton.addActionListener(this);

        //Setting the bounds
        lbl1.setBounds(1300, 10, 100, 20);
        filters.setBounds(1300,30,200,20);
        scrollPane.setBounds(10, 100, 1200, 600);
        profileButton.setBounds(1300,690,160,50);

        //add the parts
        frame.add(scrollPane);
        frame.add(lbl1);
        frame.add(filters);
        frame.add(profileButton);
        frame.add(sortTop);
        frame.add(sortReverse);
        frame.add(searchLabel);
        frame.add(searchBox);
        frame.add(searchButton);
        frame.add(cartButton);
        frame.add(cartEntry);
        frame.add(cartNumEntry);
        frame.add(addToCartButton);
        frame.add(cartLabel);
        frame.add(stateBox);
        frame.add(totalLabel);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(stateBox)){
            int ind = stateBox.getSelectedIndex();
            double total = order.getPrice(stateTaxes[ind]/100);
            total = (int)(total*100);
            total = total/100.0;
            if(total%10==total%100){
                totalLabel.setText("Total: $" + total + "0");
            }
            else{
                totalLabel.setText("Total: $" + total);
            }
        }
        if(e.getSource().equals(profileButton)){
            CustomerPanel panel = new CustomerPanel(user);
            panel.setOrder(order);
            frame.dispose();
        }
        if(e.getSource().equals(filters)){
            ArrayList<Item> cart = order.getCart();
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    cart = insertionsort(cart, i);
                }
            }
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
        if(e.getSource().equals(sortTop)){
            ArrayList<Item> cart = order.getCart();
            sortMethod = true;
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    cart = insertionsort(cart, i);
                }
            }
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
        if(e.getSource().equals(sortReverse)){
            ArrayList<Item> cart = order.getCart();
            sortMethod = false;
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    cart = insertionsort(cart, i);
                }
            }
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
        if(e.getSource().equals(searchButton)){
            ArrayList<Item> cart = order.getCart();
            String str = (searchBox.getText());
            if(str.equals("")){
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
            else{
                ArrayList<Item> products = cart;
                ArrayList<Item> temp = new ArrayList<>();
                for(int i = 0;i<products.size();i++){
                    if(products.get(i).getName().toLowerCase().equals(str.toLowerCase())){
                        temp.add(products.get(i));
                    }
                }
                String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
                String[][] strs = new String[temp.size()][5];
                for(int i = 0;i<temp.size();i++){
                    Item product = temp.get(i);
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
        }
        if(e.getSource().equals(addToCartButton)){
            String str1 = cartEntry.getText();
            String str2 = cartNumEntry.getText();
            try {
                Scanner sc = new Scanner(new File("Products.csv"));
                ArrayList<Item> tempCart = order.getCart();
                while(sc.hasNextLine()){
                    String[] item = sc.nextLine().split(",");
                    Item product = new Item(item[0],item[1],item[2],Double.parseDouble(item[3]), Integer.parseInt(str2));
                    if(product.getID().equals(str1)){
                        for(int i = 0;i<tempCart.size();i++){
                            if(tempCart.get(i).getID().equals(product.getID())){
                                product.setItemsLeft(product.getItemsLeft()+tempCart.remove(i).getItemsLeft());
                            }
                        }
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
            } catch (FileNotFoundException exc) {
                System.out.println("Product File Not Found");
            }

            if(order.isValid()){
                cartLabel.setText("Item Added to Cart");
                try {
                    cartLabel.wait(5000);
                } catch (Exception exc) {
                }
                cartLabel.setText("Cart");
            }
            updateTable();
        }
        if(e.getSource().equals(cartButton)){
            if(order.isValid()){
                order.writeToFile();
            }
            else{
                cartLabel.setText("Invalid Cart");
                try {
                    cartLabel.wait(5000);
                } catch (Exception exc) {
                }
                cartLabel.setText("Cart");
            }
        }
    }
    public void updateTable(){
        ArrayList<Item> cart = order.getCart();
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
    public ArrayList<Item> insertionsort(ArrayList<Item> arr, int swi){  
        ArrayList<Item> tempArr = arr;
        for (int i = 0; i < tempArr.size(); i++) {
            Item temp = tempArr.get(i);
            int j = i - 1;

            if(sortMethod){
                switch (swi) {
                    case 0:
                        while (j >= 0 && tempArr.get(j).getID().compareTo(temp.getID())<0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 1:
                        while (j >= 0 && tempArr.get(j).getName().compareTo(temp.getName())<0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 2:
                        while (j >= 0 && tempArr.get(j).getPrice()<(temp.getPrice())) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 3:
                        while (j >= 0 && tempArr.get(j).getCategory().compareTo(temp.getCategory())<0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 4:
                        while (j >= 0 && tempArr.get(j).getItemsLeft()>(temp.getItemsLeft())) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            else{
                switch (swi) {
                    case 0:
                        while (j >= 0 && tempArr.get(j).getID().compareTo(temp.getID())>0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 1:
                        while (j >= 0 && tempArr.get(j).getName().compareTo(temp.getName())>0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 2:
                        while (j >= 0 && tempArr.get(j).getPrice()>(temp.getPrice())) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 3:
                        while (j >= 0 && tempArr.get(j).getCategory().compareTo(temp.getCategory())>0) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    case 4:
                        while (j >= 0 && tempArr.get(j).getItemsLeft()<(temp.getItemsLeft())) {
                            tempArr.set(j+1,tempArr.get(j));
                            j--;
                        }
                        break;
                    default:
                        throw new AssertionError();
                }
            }
            tempArr.set(j+1,temp);
        }
        return tempArr;
    }
}