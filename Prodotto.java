package net.codejava;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class Prodotto extends JFrame {
	private static final String url = "jdbc:mysql://localhost:3306/dbcentroestetico";
	private static final String username = "root";
	private static final String password = "#";

	// attributi interfaccia grafica
	private JLabel lblId, lblNome, lblPrezzo, lblQuantita, lblIdTrattamento;
	private JTextField txtId, txtNome, txtPrezzo, txtQuantita, txtIdTrattamento;

	private JButton btnInserimento;
	private JButton btnAggiornare;
	private JButton btnCancellare;
	private JButton btnVisualizzare;
	private JButton btnMenuOperazioni;
	private JTextArea JTRisultato;

	// costruttore
	public Prodotto(int ruolo) {
		setTitle("Prodotto");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// inizializzazione dei componenti
		lblId = new JLabel("Id: ");
		lblNome = new JLabel("Nome Prodotto: ");
		lblPrezzo = new JLabel("Prezzo Prodotto: ");
		lblQuantita = new JLabel("Quantit� Prodotti: ");
		lblIdTrattamento = new JLabel("Inserisci Id del Trattamento: ");

		txtId = new JTextField(3);
		txtNome = new JTextField(50);
		txtPrezzo = new JTextField(10);
		txtQuantita = new JTextField(3);
		txtIdTrattamento = new JTextField(3);

		btnInserimento = new JButton("Inserimento");
		btnAggiornare = new JButton("Aggiorna");
		btnCancellare = new JButton("Cancellare");
		btnVisualizzare = new JButton("Visualizzare");
		btnMenuOperazioni = new JButton("Torna al menu Operazioni");
		JTRisultato = new JTextArea();

		// inserimento dello scroll Pane
		JScrollPane scrollPane = new JScrollPane(JTRisultato);

		// aggiunta dei componenti dell'interfaccia grafica
		JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));

		// oggetti
		panel.add(lblId);
		panel.add(txtId);
		panel.add(lblNome);
		panel.add(txtNome);
		panel.add(lblPrezzo);
		panel.add(txtPrezzo);
		panel.add(lblQuantita);
		panel.add(txtQuantita);
		panel.add(lblIdTrattamento);
		panel.add(txtIdTrattamento);
		// bottoni
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
				String NomeProdotto = txtNome.getText();
				double Prezzo = Double.parseDouble(txtPrezzo.getText());
				int Quantita = Integer.parseInt(txtQuantita.getText());
				int IdTrattamento = Integer.parseInt(txtIdTrattamento.getText());

				// apertura e connessione del database ed inserimento del nuovo Prodotto
				Connection conn = null;
				PreparedStatement pstmt = null;

				// blocco delc codice da eseguire all'interno del pulsante inserimento
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "INSERT INTO prodotto (nome,prezzo,quantita,idTrattamento)VALUES (?,?,?,?)";
					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, NomeProdotto);
					pstmt.setDouble(2, Prezzo);
					pstmt.setInt(3, Quantita);
					pstmt.setInt(4, IdTrattamento);
					// avviare all'interno del databasecon execute
					int righeInserite = pstmt.executeUpdate();
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Nuovo prodotto inserito con successo!");
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
					// reset dei campi del form
					txtId.setText("");
					txtNome.setText("");
					txtPrezzo.setText("");
					txtQuantita.setText("");
					txtIdTrattamento.setText("");
				}
			}
		});// fine pulsante inserimento
			// inizio pulsente aggiornamento
		btnAggiornare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dei dati inseriti dall'utente
				int id = Integer.parseInt(txtId.getText());
				String Nome = txtNome.getText();
				double Prezzo = Double.parseDouble(txtPrezzo.getText());
				int Quantita = Integer.parseInt(txtQuantita.getText());
				int IdTrattamento = Integer.parseInt(txtIdTrattamento.getText());

				// apertura connessione
				Connection conn = null;
				PreparedStatement pstmt = null;
				// blocco di codice da eseguire all'interno del pulsante aggiorna
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "UPDATE prodotto SET nome =? , prezzo= ?, quantita = ?, idTrattamento=? WHERE id = ?";
					pstmt = conn.prepareStatement(sql);

					pstmt.setString(1, Nome);
					pstmt.setDouble(2, Prezzo);
					pstmt.setInt(3, Quantita);
					pstmt.setInt(4, IdTrattamento);
					pstmt.setInt(5, id);

					// avviare all'interno del database con execute
					int righeInserite = pstmt.executeUpdate();
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Prodotto aggiornato con successo!");
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
				txtNome.setText("");
				txtPrezzo.setText("");
				txtQuantita.setText("");
				txtIdTrattamento.setText("");
			}

		});// fine del pulsante aggiorna
			// inizio del pulsante delete
		btnCancellare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero dei dati inseriti dall'utente
				int Id = Integer.parseInt(txtId.getText());
				// apertura e connesione del database
				Connection conn = null;
				PreparedStatement pstmt = null;
				// blocco codice da eseguire all'interno del pulsante cancella
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "DELETE FROM prodotto WHERE id = ?";
					pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e
														// prepararel'ambiente dove inseriremo la stringa

					pstmt.setInt(1, Id);
					// avviare all'interno del database con execute
					int righeInserite = pstmt.executeUpdate();

					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Prodotto Cancellato con successo!");
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
			}

		}

		);// fine del pulsante cancellare
			// inizio del pulsante Visualizzare
		btnVisualizzare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// apertura de connesione del database
				Connection conn = null;
				Statement stmt = null;
				// ripuliamo il campo visualizza dei risultati precedenti
				JTRisultato.setText("");
				// blocco codice da eseguire all'interno del visualizza

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "SELECT * FROM prodotto";
					stmt = conn.createStatement(); // comando che dice: devi utilizzare i valori e preparare
					ResultSet rs = stmt.executeQuery(sql); // l'ambiente dove inseriremo la stringa
					while (rs.next()) {
						int id = rs.getInt("id");
						String nome = rs.getString("nome");
						double prezzo = rs.getDouble("prezzo");
						int quantita = rs.getInt("quantita");
						int IdTrattamento = rs.getInt("idTrattamento");
						// stampo i record
						String risultato = "\n ID: " + id + " , NOME: " + nome + " ,PREZZO: " + prezzo + " ,QUANTITA:  "
								+ quantita + " ,ID TRATTAMENTO: " + IdTrattamento;
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
