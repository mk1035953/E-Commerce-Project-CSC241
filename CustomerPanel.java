import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;

public class CustomerPanel implements ActionListener{
    private DataBase database = new DataBase();
    private JFrame frame = new JFrame("Customer Panel");
    private JComboBox filters = new JComboBox();
    private JTable table;
    public static void main(String[] args) {
        CustomerPanel panel = new CustomerPanel();
    }
    public CustomerPanel(){
        JLabel lbl1 = new JLabel("Filters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);

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

        table = new JTable(strs, columnNames);
        table.setEnabled(false);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));



        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){

    }
}
