package org.bogdanbuduroiu.auction.client.view;

import org.bogdanbuduroiu.auction.model.User;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bogdanbuduroiu on 29.04.16.
 */
class ClientLoginPanel extends JPanel {

    private ClientLoginScreen clientLoginScreen;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JLabel lbl_register;
    private JLabel lbl_err;
    private JTextField txt_username;
    private JPasswordField txt_password;
    private JButton btn_register;
    JButton btn_submit;
    private JList<String> lst_servers;
    private JScrollPane scrl_servers;
    private Map<String, InetSocketAddress> servers;
    private JButton btn_addServer;
    private JButton btn_resetServers;
    String[] serverNames;

    public ClientLoginPanel(ClientLoginScreen cls) {
        this.clientLoginScreen = cls;
        init();
    }

    private void init() {
        lbl_username = new JLabel("Username:");
        lbl_password = new JLabel("Password:");
        lbl_register = new JLabel("New to this? Click here:");
        lbl_err = new JLabel();
        lbl_err.setForeground(Color.RED);
        txt_username = new JTextField(20);
        txt_password = new JPasswordField(20);
        btn_submit = new JButton("Submit");
        btn_register = new JButton("Register");
        btn_addServer = new JButton("Add server");
        btn_resetServers = new JButton("Clear");
        this.servers = new HashMap<>();

        try {
            this.servers.put("localhost", new InetSocketAddress(InetAddress.getByName("localhost"), 8080));
            this.servers.put("raspberrypi", new InetSocketAddress(InetAddress.getByName("raspberrypi"), 8080));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        serverNames = this.servers.keySet().toArray(new String[this.servers.size()]);
        lst_servers = new JList<>(serverNames);
        scrl_servers = new JScrollPane(lst_servers);

        clientLoginScreen.client.setServer(servers.get("localhost"));

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 3;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        add(lbl_err, c);

        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 2;
        c.gridy = 2;
        add(lbl_username, c);

        c.gridwidth = 2;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 2;
        add(txt_username, c);

        c.gridwidth = 1;
        c.weightx = 0.3;
        c.gridx = 2;
        c.gridy = 3;
        add(lbl_password, c);

        c.gridwidth = 2;
        c.weightx = 0.6;
        c.gridx = 3;
        c.gridy = 3;
        add(txt_password, c);

        c.gridwidth = 1;
        c.weightx = 0.2;
        c.gridx = 3;
        c.gridy = 4;
        add(btn_submit, c);

        c.gridwidth = 3;
        c.gridheight = 2;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 7;
        add(lbl_register, c);

        c.gridwidth = 1;
        c.weightx = 1;
        c.gridx = 4;
        c.gridy = 7;
        add(btn_register, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20,0,10,0);
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 4;
        c.weightx = 1;
        add(scrl_servers, c);

        c.insets = new Insets(0,0,0,0);
        c.gridx = 3;
        c.gridy = 11;
        c.gridwidth = 1;
        add(btn_addServer, c);

        btn_submit.addActionListener((e) -> {
            User tmpUser = new User(txt_username.getText());
            clientLoginScreen.client.setCurrentUser(tmpUser);
            clientLoginScreen.client.validateDetails(tmpUser, txt_password.getPassword());

            this.txt_password.setText("");
        });

        lst_servers.addListSelectionListener(e -> clientLoginScreen.client.setServer(servers.get(lst_servers.getSelectedValue())));

        btn_register.addActionListener(e -> clientLoginScreen.cardLayout.show(clientLoginScreen.cards, ClientLoginScreen.REGISTRATION_CARD));

        btn_resetServers.addActionListener(e -> servers.clear());

        btn_addServer.addActionListener(e -> new AddServerScreen(this));
    }

    public void addServer(String identifier, InetSocketAddress server) {
        servers.put(identifier, server);
        serverNames = Arrays.copyOf(serverNames, serverNames.length + 1);
        serverNames[serverNames.length - 1] = identifier;
        lst_servers = new JList<>(serverNames);
        scrl_servers = new JScrollPane(lst_servers);
    }

    public void setErr(String errMsg) {
        lbl_err.setText(errMsg);
    }
}
