package net.codejava;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Utente extends JFrame {
	// attributi sql
	private static final String url = "jdbc:mysql://localhost:3306/dbcentroestetico";
	private static final String username = "root"; // passw di amministratore generale
	private static final String password = "#";

	// attributi interfaccia grafica
	private JLabel lblId, lblUsername, lblPassword, lblRuolo;
	private JTextField txtId, txtUsername, txtRuolo;
	private JPasswordField txtPassword;
	
	// bottoni
	private JButton btnInserimento;
	private JButton btnAggiornare;
	private JButton btnCancellare;
	private JButton btnVisualizzare;
	private JButton btnMenuOperazioni;
	private JTextArea JTRisultato;
	
	// costruttore

	public Utente(int ruolo) {
		
		setTitle("Utente");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// inizializzaione dei componenti dell'interfaccia
		lblId = new JLabel("Id: ");
		lblUsername = new JLabel("Inscerisci il tuo Username: ");
		lblPassword = new JLabel("Inserisci la tua Password");
		lblRuolo = new JLabel("Inserisci il Ruolo dell'utente: ");

		txtId = new JTextField(3);
		txtUsername = new JTextField(50);
		
		txtRuolo = new JTextField(100);
		
		btnInserimento = new JButton("Inserimento");
		btnAggiornare = new JButton("Aggiorna");
		btnCancellare = new JButton("Cancellare");
		btnVisualizzare = new JButton("Visualizzare");
		btnMenuOperazioni = new JButton("Torna al menu Operazioni");
		
		JTRisultato = new JTextArea();
		txtPassword = new JPasswordField(50);
		txtPassword.setEchoChar('*');
		
		// Inseriamo lo scroll a JTRisultato
		JScrollPane scrollPane = new JScrollPane(JTRisultato);

		// aggiunta dei componenti all'interafaccia grafica
		JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
		// oggetti
		panel.add(lblId);
		panel.add(txtId);
		panel.add(lblUsername);
		panel.add(txtUsername);
		panel.add(lblPassword);
		panel.add(txtPassword);
		panel.add(lblRuolo);
		panel.add(txtRuolo);

		panel.add(btnInserimento);
		panel.add(btnAggiornare);
		panel.add(btnCancellare);
		panel.add(btnVisualizzare);
		panel.add(btnMenuOperazioni);
		// panel.add(JTRisultato); andava in conflitto con scroll Pane
		panel.add(scrollPane, BorderLayout.CENTER);

		// gestione del bottone inserimento con il proprio listener

		btnInserimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dei dati inseriti dall'utente

				String Username = txtUsername.getText();
				String Password = txtPassword.getText();
				String Ruolo = txtRuolo.getText();

				// apertura e connessione del database ed inserimento del nuovo trattamento
				Connection conn = null;
				PreparedStatement pstmt = null;

				// blocco codice da eseguire all'intrnon del pulsante inserimento
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "INSERT INTO utente(username,password,ruolo) VALUES(?,?,?)";
					pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e
														// prepararel'ambiente dove inseriremo la stringa

					pstmt.setString(1, Username);
					pstmt.setString(2, Password);
					pstmt.setString(3, Ruolo);

					// avviare all'interno del databasecon execute
					int righeInserite = pstmt.executeUpdate();
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Nuovo Utente inserito con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

				}
				// reset dei campi del form

				txtId.setText("");
				txtUsername.setText("");
				txtPassword.setText("");
				txtRuolo.setText("");

			}

		});
		btnAggiornare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dei dati inseriti dall'utente
				int id = Integer.parseInt(txtId.getText());
				String Username = txtUsername.getText();
				String Password = txtPassword.getText();
				String Ruolo = txtRuolo.getText();

				// apertura e connessione del database ed inserimento del nuovo studente
				Connection conn = null;
				PreparedStatement pstmt = null;

				// blocco codice da eseguire all'intrno del pulsante salva
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "UPDATE utente SET username = ? , password = ? ,  ruolo = ?  WHERE id = ?";
					pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e
														// prepararel'ambiente dove inseriremo la stringa

					pstmt.setString(1, Username);
					pstmt.setString(2, Password);
					pstmt.setString(3, Ruolo);
					pstmt.setInt(4, id);

					// avviare all'interno del database con execute
					int righeInserite = pstmt.executeUpdate();
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Utente aggiornato con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}

				}
				// reset dei campi del form

				txtId.setText("");
				txtUsername.setText("");
				txtPassword.setText("");
				txtRuolo.setText("");
			}

		});// fine del pulsante aggiorna
		btnCancellare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dei dati inseriti dall'utente
				int Id = Integer.parseInt(txtId.getText());

				// apertura e connessione del database
				Connection conn = null;
				PreparedStatement pstmt = null;

				// blocco codice da eseguire all'intrno del pulsante cancella

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "DELETE FROM utente WHERE id=?";
					pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e
														// prepararel'ambiente dove inseriremo la stringa

					pstmt.setInt(1, Id);

					// avviare all'interno del database con execute
					int righeInserite = pstmt.executeUpdate();

					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Utenete cancellato con successo!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (pstmt != null)
							pstmt.close();

						if (conn != null)
							conn.close();

					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				// reset dei campi del form
				txtId.setText("");
				txtUsername.setText("");
				txtPassword.setText("");
				txtRuolo.setText("");

			}
		});
		// fine del pulsante delete
		// Pulsante Visualizzare
		btnVisualizzare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// apertura e connessione del database
				Connection conn = null;
				Statement stmt = null;
				// ripuliamo il campo per la visualizzazione dei risultati
				JTRisultato.setText("");
				// blocco codice da eseguire all'intrno del pulsante visualizza

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "SELECT * FROM utente";
					stmt = conn.createStatement(); // comando che dice: devi utilizzare i valori e preparare
					ResultSet rs = stmt.executeQuery(sql); // l'ambiente dove inseriremo la stringa
					while (rs.next()) {
						int id = rs.getInt("id");
						String Username = rs.getString("username");
						String Password = rs.getString("password");
						String Ruolo = rs.getString("ruolo");
						// stampo i record
						String risultato = "\nID: " + id + " ,USENAME: " + Username + " ,RUOLO: " + Ruolo;
								
						JTRisultato.append(risultato);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (stmt != null)
							stmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}

			}
		});
		
		btnMenuOperazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(ruolo) {
				case 1: 
					JOptionPane.showMessageDialog(null, "Bentornato al menu dell'Amministratore ");
					dispose(); // Close the Main JFrame
					new MenuAdministrator();			
					break;
				case 2: 
					JOptionPane.showMessageDialog(null, "Bentornato al menu del Segretario ");					
					dispose(); // Close the Main JFrame
					new MenuSecretary();							
					break;
				case 3: 
					JOptionPane.showMessageDialog(null, "Bentornato al menu dell'Operatore");
					dispose(); // Close the Main JFrame
					new MenuOperator();
					break;
				case 0:
					// Invalid value selected or default value selected
	            	JOptionPane.showMessageDialog(null, "Invalida autenticazione. Riavvia il programma");
					break;
				default:
					JOptionPane.showMessageDialog(null, "Errore nel check della password e dello username.");
					break;
				}
			}
		}
				
				);

		add(panel);
		setVisible(true);
	}

}
