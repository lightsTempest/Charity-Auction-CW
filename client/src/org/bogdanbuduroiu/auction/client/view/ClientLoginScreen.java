package org.bogdanbuduroiu.auction.client.view;

import org.bogdanbuduroiu.auction.client.controller.Client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bogdanbuduroiu on 29.04.16.
 */
public class ClientLoginScreen extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 750;
    private static final String TITLE = "jBay - Login";
    CardLayout cardLayout;
    JPanel cards;


    static final String LOGIN_CARD = "Login";
    static final String REGISTRATION_CARD = "Registration";

    Client client;
    ClientLoginPanel clientLoginPanel = new ClientLoginPanel(this);
    ClientRegistrationPanel clientRegistrationPanel = new ClientRegistrationPanel(this);

    public ClientLoginScreen(Client client) {
        this.client = client;
        init();
    }

    private void init() {

        cards = new JPanel(new CardLayout());

        setLayout(new BorderLayout());

        cards.add(clientLoginPanel, LOGIN_CARD);
        cards.add(clientRegistrationPanel, REGISTRATION_CARD);

        cardLayout = (CardLayout) cards.getLayout();

        cardLayout.show(cards, "Login");

        add(cards, BorderLayout.CENTER);

        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void registrationSuccessful() {
        JOptionPane.showMessageDialog(null, "Registration Successful");
        cardLayout.show(cards, LOGIN_CARD);
    }

    public void invalidLogin() {
        clientLoginPanel.setErr("Invalid username/password.");
    }
}