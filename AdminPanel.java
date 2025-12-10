import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class AdminPanel implements ActionListener{
    private String user;
    private DataBase database = new DataBase();
    private JFrame frame = new JFrame("Admin Panel");
    private String[] filterTypes = {"ID", "Name", "Price", "Category","Quantity"};
    private JComboBox<String> filters = new JComboBox<>(filterTypes);
    private JTable table;
    private JButton profileButton;
    private JButton productButton;
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
    private JButton orderHistory;
    private Order order;

    public static void main(String[] args) {
        AdminPanel panel = new AdminPanel("");
    }
    public AdminPanel(String username){
        user = username;
        JLabel lbl1 = new JLabel("Filters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setLayout(null);
        order = new Order(user);

        // Table column names
        String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};

        // Table data (rows)
        String[][] strs = new String[database.products.size()][5];
        for(int i = 0;i<database.products.size();i++){
            Item product = database.products.get(i);
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
        profileButton = new JButton("Profile");

        //Set up ProductButton
        productButton = new JButton("Products");
        productButton.setBounds(1400,80,100,50);
        productButton.addActionListener(this);
        productButton.setFont(new Font("SansSerif", Font.PLAIN, 16));

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
        sortTop.setBounds(1300,320,200,20);
        sortTop.setFont(new Font("SansSerif", Font.PLAIN, 16));
        sortReverse.setSelected(false);
        sortReverse.setFont(new Font("SansSerif", Font.PLAIN, 16));
        sortReverse.setBounds(1300,340,200,20);

        //Setup Cart Features
        cartLabel = new JLabel("Cart");
        cartLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cartButton = new JButton("Cart");
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

        //Setup Order History Button
        orderHistory = new JButton("Order History");
        orderHistory.setFont(new Font("SansSerif", Font.PLAIN, 16));
        orderHistory.setBounds(1235,30,160,50);

        //Setup ActionListeners
        sortTop.addActionListener(this);
        sortReverse.addActionListener(this);
        profileButton.addActionListener(this);
        profileButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        filters.addActionListener(this);
        cartButton.addActionListener(this);
        addToCartButton.addActionListener(this);
        orderHistory.addActionListener(this);

        //Setting the bounds
        lbl1.setBounds(1300, 275, 100, 20);
        filters.setBounds(1300,300,200,20);
        scrollPane.setBounds(10, 100, 1200, 600);
        profileButton.setBounds(1400,30,100,50);

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
        frame.add(orderHistory);
        frame.add(productButton);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(productButton)){
            ProductPanel panel = new ProductPanel(user);
            frame.dispose();
        }
        if(e.getSource().equals(profileButton)){
            ProfileWindow window = new ProfileWindow(user, true);
            frame.dispose();
        }
        if(e.getSource().equals(filters)){
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    database.products = insertionsort(database.products, i);
                }
            }
            String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
            String[][] strs = new String[database.products.size()][5];
            for(int i = 0;i<database.products.size();i++){
                Item product = database.products.get(i);
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
            sortMethod = true;
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    database.products = insertionsort(database.products, i);
                }
            }
            String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
            String[][] strs = new String[database.products.size()][5];
            for(int i = 0;i<database.products.size();i++){
                Item product = database.products.get(i);
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
            sortMethod = false;
            String filter = (String)filters.getSelectedItem();
            for(int i = 0;i<filterTypes.length;i++){
                if(filterTypes[i].equals(filter)){
                    database.products = insertionsort(database.products, i);
                }
            }
            String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
            String[][] strs = new String[database.products.size()][5];
            for(int i = 0;i<database.products.size();i++){
                Item product = database.products.get(i);
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
            String str = (searchBox.getText());
            if(str.equals("")){
                String[] columnNames = {"ID", "Name", "Category", "Price", "Quantity"};
                String[][] strs = new String[database.products.size()][5];
                for(int i = 0;i<database.products.size();i++){
                    Item product = database.products.get(i);
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
                ArrayList<Item> products = database.products;
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
        }
        if(e.getSource().equals(cartButton)){
            CartPanel panel = new CartPanel(order, user, true);
            frame.dispose();
        }
        if(e.getSource().equals(orderHistory)){
            OrderPanel panel = new OrderPanel(user,true);
            frame.dispose();
        }
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