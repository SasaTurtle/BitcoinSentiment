import Models.Tweet;
import Utility.Sentiment;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BitcoinTwitterOkno extends JFrame {
    private GridLayout gridLayout;
    //private JPanel Bitcoin_Sentiment;
    private JTextField twitterKeyword;
    private JLabel labelKeyword;
    private JButton button1;
    private JTable twitterTable;
    private JTable twitterHistory;
    private JButton ulozButton;

    private JMenuBar menuBar;
    private JMenu menu;

    private JPanel topPanel;
    private JPanel centralPanel;
    private JPanel bottomPanel;
    private CardLayout cl;
    private JPanel panelMain;
    private JPanel panel1;
    private JPanel panel2;

    List<Tweet> list;

    public BitcoinTwitterOkno(String title) {
        super(title);
        list = new ArrayList<Tweet>();
        setBounds(500, 500, 500, 500);
        //Container container = getContentPane();
        //container.setLayout(new FlowLayout());
        //container.setLayout(new BorderLayout());
        //this.setContentPane(new BitcoinTwitterOkno().Bitcoin_Sentiment);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        menu = new JMenu("Okna");
        JMenuItem menuItem2 = new JMenuItem("Vyhledávání tweetů");
        menu.add(menuItem2);
        JMenuItem menuItem1 = new JMenuItem("Uložené tweety");
        menu.add(menuItem1);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);


        //Obrazovka 1
        labelKeyword = new JLabel();
        labelKeyword.setText("Klíčové slovo");
        //labelKeyword.setHorizontalAlignment(JLabel.LEFT);
        twitterKeyword = new JTextField();
        //  twitterKeyword.setHorizontalAlignment(JLabel.CENTER);

        button1 = new JButton();
        button1.setText("Hledej");
        //button1.setHorizontalAlignment(JLabel.RIGHT);
        twitterTable = new JTable();
        ulozButton = new JButton();
        ulozButton.setText("Ulož");

        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(labelKeyword, BorderLayout.WEST);
        topPanel.add(twitterKeyword, BorderLayout.CENTER);
        topPanel.add(button1, BorderLayout.EAST);

        centralPanel = new JPanel();
        centralPanel.setLayout(new GridLayout());
        centralPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Zprávy z twitteru", TitledBorder.CENTER, TitledBorder.TOP));
        //centralPanel.setBackground(Color.green);

        String[] columnNames = {"Tweety", "Sentiment", "ID autora", "Datum"};
        //Object[][] data = {{"Swing Timer", 2.99, 5, 1.01}, {"Swing Worker", 7.10, 5, 1.010}, {"TableModelListener", 25.05, 5, 1.01}};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        twitterTable = new JTable(model);
        twitterTable.getColumnModel().getColumn(0).setPreferredWidth(1200);

        //twitterTable.setPreferredScrollableViewportSize(twitterTable.getPreferredSize());
        centralPanel.add(new JScrollPane(twitterTable), BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.add(ulozButton, BorderLayout.EAST);

        //obrazovka 2
        DefaultTableModel model2 = new DefaultTableModel(columnNames, 0);
        twitterHistory = new JTable(model2);
        twitterHistory.getColumnModel().getColumn(0).setPreferredWidth(1200);
        createHistoryTableData();

        //hlavni okno
        cl = new CardLayout();
        panelMain = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panelMain.setLayout(cl);

        panel1.setLayout(new BorderLayout());
        panel1.add(topPanel, BorderLayout.NORTH);
        panel1.add(centralPanel, gridLayout);
        centralPanel.setBackground(Color.cyan);
        panel1.add(bottomPanel, BorderLayout.SOUTH);

        panel2.setLayout(new BorderLayout());
        panel2.add(new JScrollPane(twitterHistory),BorderLayout.CENTER);


        panelMain.add(panel1,"1");
        panelMain.add(panel2,"2");
        cl.show(panelMain, "1");

        this.add(panelMain);

        ulozButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                centralPanel.add(new JLabel(twitterKeyword.getText()));
                saveTweets(list);
            }
        });
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        twitterKeyword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    search();
                }
            }
        });

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelMain, "2");
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cl.show(panelMain, "1");
            }
        });
    }

    private void search () {
            Sentiment sentiment = new Sentiment();
            List<Tweet> tweet = sentiment.getTwitterAndAnalyzeSentiment(twitterKeyword.getText());
            for (Tweet t : tweet) {
                list.add(t);
            }
            createTableData(tweet);
    }

    private void createTableData (List<Tweet> list) {
            DefaultTableModel model = (DefaultTableModel) twitterTable.getModel();

            for (Tweet t : list) {
                model.addRow(new Object[]{t.getText(), t.getSentiment(), t.getAutorID(), t.getCreatedAt()});
            }

    }

    private void saveTweets (List<Tweet> list) {
        try (PrintWriter writer = new PrintWriter("ulozeneTweety.csv")) {

            StringBuilder sb = new StringBuilder();

            for (Tweet t : list) {
                sb.append(String.format("%s,%s,%s,%s\n",t.getText().replace(","," "),t.getSentiment(),t.getAutorID(),t.getCreatedAt()));
            }

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    private void createHistoryTableData () {
        String line = "";
        String splitBy = ",";

        DefaultTableModel model = (DefaultTableModel) twitterHistory.getModel();

        try {
            BufferedReader br = new BufferedReader(new FileReader("ulozeneTweety.csv"));
            while ((line = br.readLine()) != null)
            {
                String[] data = line.split(splitBy);
                if (data.length > 3) {
                    model.addRow(new Object[]{data[0], data[1], data[2], data[3]});
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
