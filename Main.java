package net.codejava;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

public class Main extends JFrame {
	private static final String url = "jdbc:mysql://localhost:3306/dbCentroEstetico";
	private static final String username = "root";
	private static final String password = "#";

	private static final String RUOLO = "SELECT * FROM utente WHERE username = '%s' AND password = '%s'";
	
	private JTextField txtUsername;
	private JLabel lblUsername, lblPassword, lblDescription, lblEmpty;
	private JButton btnLogin;
	private JPasswordField passwordField;

	
	public Main() {
		setTitle("Login Centro Estetico");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		lblDescription = new JLabel("Login:");
		lblEmpty = new JLabel("<html></html>");
		
		lblUsername = new JLabel("Username");
		txtUsername = new JTextField(50);

		lblPassword = new JLabel("Password");
		passwordField = new JPasswordField(50);
		
		btnLogin = new JButton("Login");
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				if (!txtUsername.getText().isEmpty()) {
					char[] pass = passwordField.getPassword();
					String passwordUser = new String(pass);
					String userName = txtUsername.getText();
					if (pass.length != 0) {
						int rs = login(txtUsername.getText(), passwordUser);
						switch(rs) {
						case 1: 
							JOptionPane.showMessageDialog(null, "Benvenuto Amministratore "  + txtUsername.getText());
							cleanData(txtUsername, passwordField);
							dispose(); // Close the Main JFrame
							new MenuAdministrator();			
							break;
						case 2: 
							JOptionPane.showMessageDialog(null, "Benvenuto Secretary "  + txtUsername.getText());
							cleanData(txtUsername, passwordField);
							dispose(); // Close the Main JFrame
							new MenuSecretary();							
							break;
						case 3: 
							JOptionPane.showMessageDialog(null, "Benvenuto Operatore " + txtUsername.getText());
							cleanData(txtUsername, passwordField);
							dispose(); // Close the Main JFrame
							new MenuOperator();
							break;
						case 0:
							// Invalid value selected or default value selected
			            	JOptionPane.showMessageDialog(null, "Invalid password or username.");
							break;
						default:
							JOptionPane.showMessageDialog(null, "Errore nel check della password e dello username.");
							break;
						}						
					} else {
						JOptionPane.showMessageDialog(null, "Non è stata inserito lo username");
					}
				}
			}
		}

		);
		
		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.add(lblDescription);
		panel.add(lblEmpty);
		panel.add(lblUsername);
		panel.add(txtUsername);
		panel.add(lblPassword);
		panel.add(passwordField);

		panel.add(btnLogin);

		add(panel);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new Main();

	}
	
	public static void cleanData(JTextField txtUsername, JPasswordField passwordField) {
		txtUsername.setText("");
		passwordField.setText("");
	}

	private static int login(String usernameUser, String passwordUser) {
		String query = String.format(RUOLO, usernameUser, passwordUser);
		System.out.println(query);
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			if(rs.next()) {
				String ruolo = rs.getString("ruolo");
				if(ruolo.equals("administrator")) {
					return 1;
				} else if(ruolo.equals("secretary")){
					return 2;
				} else if(ruolo.equals("operator")) {
					return 3;
				} else
					return 0;
			}
			return 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Errore durante la connessione al database, per login");
			e.printStackTrace();
		}
		return 0;
	}

}

