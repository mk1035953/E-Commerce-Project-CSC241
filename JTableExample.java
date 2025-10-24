import javax.swing.*;
import java.awt.*;

public class JTableExample {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Product Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Table column names
        String[] columnNames = {"Product", "Price", "Description"};

        // Table data (rows)
        Object[][] data = {
            {"Laptop", "$999", "15-inch display, 16GB RAM"},
            {"Phone", "$599", "5G capable, OLED screen"},
            {"Tablet", "$399", "10-inch, 128GB storage"},
            {"Monitor", "$199", "24-inch, 144Hz"},
            {"Keyboard", "$49", "Mechanical, RGB backlight"},
            {"Mouse", "$29", "Wireless optical"},
            {"Headphones", "$79", "Noise-cancelling"},
            {"Camera", "$499", "4K video recording"},
            {"Printer", "$149", "Laser, duplex printing"},
            {"Smartwatch", "$199", "Heart rate monitor"}
        };

        // Create the JTable with the data
        JTable table = new JTable(data, columnNames);

        // Optional: make the table read-only
        table.setEnabled(false);

        // Optional: customize appearance
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // Add the table inside a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }
}
