package net.codejava;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Macchinario extends JFrame {
	// attributi sql
	// connessione al database
	private static final String url = "jdbc:mysql://localhost:3306/macchinario";
	private static final String username = "root";
	private static final String password = "#";

	// attributi per l'interfaccia grafica
	private JLabel lblnMacchinario, lblnomeMacchinario, lblIdTrattamento, lblIdStanza, lblId;
	private JTextField txtnMacchinario, txtnomeMacchinario, txtIdTrattamento, txtIdStanza, txtId;
	private JButton btnInserisci, btnAggiorna, btnVisualizza, btnRimuovi, btnMenuOperazioni;
	private JCheckBox Macchinari_Funzionanti;
	private JTextArea textArea;

	public Macchinario(int ruolo) {
		// aggiunta componenti all'interfaccia grafica
		setTitle("Macchinari");

		// inizializzamo i componenti dell'interfaccia
		lblnMacchinario = new JLabel("Numero Macchinario: ");
		lblnomeMacchinario = new JLabel("Nome Macchinario: ");
		lblIdTrattamento = new JLabel("Id trattamento: ");
		lblIdStanza = new JLabel("Id stanza: ");
		lblId = new JLabel("Id: ");

		txtnMacchinario = new JTextField();
		txtnomeMacchinario = new JTextField();
		txtIdTrattamento = new JTextField();
		txtIdStanza = new JTextField();
		txtId = new JTextField();

		// inizializziamo i bottoni
		btnInserisci = new JButton("Inserisci");
		btnAggiorna = new JButton("Aggiorna");
		btnVisualizza = new JButton("Visualizza");
		btnRimuovi = new JButton("Rimuovi");
		btnMenuOperazioni = new JButton("Torna al menu Operazioni");

		JLabel lblMacchinariFunzionanti = new JLabel("Spunta se tutti i macchinari sono funzionanti: ");
		Macchinari_Funzionanti = new JCheckBox();

		// inizializzazione area di testo per visualizzare tutti i macchinari
		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		JPanel panelCampi = new JPanel(new GridLayout(6, 2));
		JPanel panelBottoni = new JPanel(new GridLayout(5, 1));
		JPanel panelTextArea = new JPanel(new GridLayout(1, 2));
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLayout(new BorderLayout());
		frame.add(panelCampi, BorderLayout.WEST);
		frame.add(panelBottoni, BorderLayout.SOUTH);
		frame.add(panelTextArea, BorderLayout.CENTER);

		// inseriamo gli oggetti creati nei panel
		panelCampi.add(lblnMacchinario);
		panelCampi.add(txtnMacchinario);

		panelCampi.add(lblnomeMacchinario);
		panelCampi.add(txtnomeMacchinario);

		panelCampi.add(lblIdTrattamento);
		panelCampi.add(txtIdTrattamento);

		panelCampi.add(lblIdStanza);
		panelCampi.add(txtIdStanza);

		panelCampi.add(lblMacchinariFunzionanti);
		panelCampi.add(Macchinari_Funzionanti);

		panelCampi.add(lblId);
		panelCampi.add(txtId);

		panelTextArea.add(scrollPane);

		// Gestione del bottone inserisci con il proprio listener
		btnInserisci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero i dati inseriti dall'utente
				int nMacchinario = Integer.parseInt(txtnMacchinario.getText());
				String nomeMacchinario = txtnomeMacchinario.getText();
				int idTrattamento = Integer.parseInt(txtIdTrattamento.getText());
				int idStanza = Integer.parseInt(txtIdStanza.getText());
				boolean macchinariFunzionanti = Macchinari_Funzionanti.isSelected();

				// apertura connessione al database ed inserimento del nuovo macchinario
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "INSERT INTO macchinario(nMacchinario, nomeMacchinario, idTrattamento, idStanza, Macchinari_Funzionanti) VALUES(?, ?, ?, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, nMacchinario);
					pstmt.setString(2, nomeMacchinario);
					pstmt.setInt(3, idTrattamento);
					pstmt.setInt(4, idStanza);
					pstmt.setBoolean(5, macchinariFunzionanti);

					int righeInserite = pstmt.executeUpdate();
					// creiamo una finestra di pop up che ci da conferma se l'nserimento del record
					// e avvennuto
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Macchinario inserito con successo!");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						// chiusura connessioni nel database
						if (pstmt != null)
							pstmt.close();
						if (conn != null)
							conn.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				}
				// reset dei campi del form
				txtnMacchinario.setText("");
				txtnomeMacchinario.setText("");
				txtIdTrattamento.setText("");
				txtIdStanza.setText("");
				txtId.setText("");
				Macchinari_Funzionanti.setSelected(false);
			}
		});
		panelBottoni.add(btnInserisci);
		frame.setVisible(true);

		// gestione del bottone aggiorna
		btnAggiorna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recupero i dati inseriti dall'utente
				int nMacchinario = Integer.parseInt(txtnMacchinario.getText());
				String nomeMacchinario = txtnomeMacchinario.getText();
				int idTrattamento = Integer.parseInt(txtIdTrattamento.getText());
				int idStanza = Integer.parseInt(txtIdStanza.getText());
				int id = Integer.parseInt(txtId.getText());
				boolean macchinariFunzionanti = Macchinari_Funzionanti.isSelected();

				// apertura connessione al database ed inserimento del nuovo macchinario
				Connection conn = null;
				PreparedStatement pstmt = null;

				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "UPDATE macchinario SET nMacchinario = ?, nomeMacchinario = ?, idTrattamento = ?, idStanza = ?, Macchinari_Funzionanti = ?  WHERE id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, nMacchinario);
					pstmt.setString(2, nomeMacchinario);
					pstmt.setInt(3, idTrattamento);
					pstmt.setInt(4, idStanza);
					pstmt.setBoolean(5, macchinariFunzionanti);
					pstmt.setInt(6, id);

					int righeInserite = pstmt.executeUpdate();
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Macchinario aggiornato con successo!");
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
				txtnMacchinario.setText("");
				txtnomeMacchinario.setText("");
				txtIdTrattamento.setText("");
				txtIdStanza.setText("");
				txtId.setText("");
				Macchinari_Funzionanti.setSelected(false);
			}
		});
		panelBottoni.add(btnAggiorna);
		frame.setVisible(true);

		// Gestione del bottone visualizza con il proprio listener
		btnVisualizza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// apertura connessione al database ed inserimento del nuovo cliente
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				// reset del form precedentemente stampato
				textArea.setText("");
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "SELECT * FROM macchinario";
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					// scorriamo all'interno del result set per visualizzare tutti i record presenti
					while (rs.next()) {
						int nMacchinario = rs.getInt("nMacchinario");
						String nomeMacchinario = rs.getString("nomeMacchinario");
						int idTrattamento = rs.getInt("idTrattamento");
						int idStanza = rs.getInt("idStanza");
						boolean macchinariFunzionanti = rs.getBoolean("Macchinari_Funzionanti");
						int id = rs.getInt("id");

						String ris = ("ID: " + id + "\nNumero macchinario: " + nMacchinario + "\nNome macchinario: "
								+ nomeMacchinario + "\nIdTrattameto: " + idTrattamento + "\nIdStanza: " + idStanza
								+ "\nMacchinari funzionanti: " + macchinariFunzionanti);
						// aggiungiamo risultati alla textArea
						textArea.append(ris);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				} finally {
					try {
						// chiudiamo le connessioni se sono state aperte
						if (rs != null)
							rs.close();
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
		panelBottoni.add(btnVisualizza);
		frame.setVisible(true);

		// gestione del bottone rimuovi
		btnRimuovi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(txtId.getText());

				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DriverManager.getConnection(url, username, password);
					String sql = "DELETE FROM macchinario WHERE id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, id);

					int righeInserite = pstmt.executeUpdate();
					if (righeInserite > 0) {
						JOptionPane.showMessageDialog(null, "Macchinario rimosso con successo!");
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
				// reset campi del form
				txtId.setText("");
			}

		});
		
		panelBottoni.add(btnRimuovi);
		
		btnMenuOperazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch(ruolo) {
				case 1: 
					JOptionPane.showMessageDialog(null, "Bentornato al menu dell'Amministratore ");
					frame.dispose(); // Close the Main JFrame
					new MenuAdministrator();			
					break;
				case 2: 
					JOptionPane.showMessageDialog(null, "Bentornato al menu del Segretario ");					
					frame.dispose(); // Close the Main JFrame
					new MenuSecretary();							
					break;
				case 3: 
					JOptionPane.showMessageDialog(null, "Bentornato al menu dell'Operatore");
					frame.dispose(); // Close the Main JFrame
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
		
		panelBottoni.add(btnMenuOperazioni);
		frame.setVisible(true);
	}

}
