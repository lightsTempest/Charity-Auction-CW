package org.bogdanbuduroiu.auction.server.view;

import org.bogdanbuduroiu.auction.model.Item;
import org.bogdanbuduroiu.auction.model.User;
import org.bogdanbuduroiu.auction.server.controller.Server;
import org.bogdanbuduroiu.auction.server.controller.TextAreaOutputStream;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by bogdanbuduroiu on 09.05.16.
 */
public class ServerGUI extends JFrame {

    private Server server;

    private JTextArea txt_console;

    private JButton btn_report;

    private JScrollPane scrl_report;
    private JTable tbl_report;
    private TextAreaOutputStream out_console;

    private Set<Map.Entry<User, Set<Item>>> won_auctions_store = new HashSet<>();

    public ServerGUI(Server server) throws HeadlessException {
        super("Server");
        this.server = server;

    }

    public void init() {

        txt_console = new JTextArea();
        txt_console.setBackground(Color.BLACK);
        txt_console.setForeground(Color.WHITE);

        tbl_report = new JTable();
        scrl_report = new JScrollPane(tbl_report);
        scrl_report.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrl_report.setMinimumSize(new Dimension(400,800));
        scrl_report.setSize(new Dimension(600,800));
        txt_console.setMaximumSize(new Dimension(600,800));
        txt_console.setLineWrap(true);


        btn_report = new JButton("Generate Report");

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        out_console = new TextAreaOutputStream(txt_console);

        System.setOut(new PrintStream(out_console));

        c.insets = new Insets(0,5,0,5);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.1;
        add(btn_report, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 0.3;
        c.weighty = 0.9;
        add(scrl_report, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 0.7;
        c.weighty = 0.9;
        add(txt_console, c);

        btn_report.addActionListener(e -> loadTableData());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,800);
        setVisible(true);
    }

    private void loadTableData() {
        HashMap<User, Set<Item>> data = server.getWon_auctions_report();

        String[] columnName = new String[] {"Username", "Item", "Seller", "Price"};

        int totalSize = 0;
        for (Map.Entry<User, Set<Item>> entry : data.entrySet()) {
            won_auctions_store.add(entry);
            totalSize+=entry.getValue().size();
        }

        int i = 0;
        Object[][] formatted_data = new Object[totalSize][];

        for (Map.Entry<User, Set<Item>> entry : won_auctions_store)
            for (Item item : entry.getValue())
                formatted_data[i++] = new Object[] {
                        entry.getKey().getUsername(),
                        item.getTitle(),
                        item.getVendor().getUsername(),
                        item.getBids().peek().getBidAmmount()
                };

        DefaultTableModel model = new DefaultTableModel(formatted_data, columnName);
        tbl_report.setModel(model);
    }

    public JTextArea getTxt_console() {
        return txt_console;
    }
}
