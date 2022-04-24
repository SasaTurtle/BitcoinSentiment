import javax.swing.*;
import java.awt.*;

public class BitcoinTwitterOkno extends JFrame{
    private JPanel Bitcoin_Sentiment;
    private JTextField textField1;
    private JTable table1;
    private JButton hledejButton;

    private JMenuBar menuBar;
    private JMenu menu;
    //JMenuItem menuItem;



    public BitcoinTwitterOkno(String title) {
        super (title);
        setBounds(500,500,500,500);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        this.setContentPane(new BitcoinTwitterOkno().Bitcoin_Sentiment);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Save");
        menu.add(menuItem);
        JMenuItem menuItem2Exit = new JMenuItem("Exit");
        menu.add(menuItem2Exit);
        menuBar.add(menu);

        //Build second menu in the menu bar.
        menu = new JMenu("Window");
        JMenuItem menuItemOkno1 = new JMenuItem("Okno 1");
        menu.add(menuItemOkno1);
        JMenuItem menuItemOkno2 = new JMenuItem("Okno 2");
        menu.add(menuItemOkno2);
        JMenuItem menuItemOkno3 = new JMenuItem("Okno 3");
        menu.add(menuItemOkno3);
        menuBar.add(menu);

        this.setJMenuBar(menuBar);
    }

    public BitcoinTwitterOkno() {

    }


}
