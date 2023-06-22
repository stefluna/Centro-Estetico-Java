package net.codejava;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Trattamento extends JFrame {
	//attributi sql
	private static final String url = "jdbc:mysql://localhost:3306/dbcentroestetico";
	private static final String username = "root"; // passw di amministratore generale
	private static final String password = "#";
	//attributi interfaccia grafica
	private JLabel lblId, lblNomeTrattamento, lblDurata, lblIdOperatore, lblPrezzo;
	private JTextField txtId, txtNomeTrattamento, txtDurata, txtIdOperatore, txtPrezzo;
	
	private JButton btnInserimento;
	private JButton btnAggiornare;
	private JButton btnCancellare;
	private JButton btnVisualizzare;
	private JButton btnMenuOperazioni;
	private JTextArea JTRisultato;
	
	//costruttore
			
	public Trattamento(int ruolo) {
	setTitle("Trattamento");
	setSize(800,600);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	//inizializzazione dei componetnti dell'iterfaccia
	
	lblId = new JLabel("Id: ");
	lblNomeTrattamento = new JLabel("Nome Trattamento: ");
	lblDurata = new JLabel ("Durata Trattamento: ");
	lblIdOperatore = new JLabel("Inserisci l'id dell'operatore desiderato: ");
	lblPrezzo = new JLabel("Prezzo Trattamento: ");
	
	txtId = new JTextField(3);
	txtNomeTrattamento = new JTextField(100);
	txtDurata = new JTextField(5);
	txtIdOperatore = new JTextField(3);
	txtPrezzo = new JTextField(10);
	
	btnInserimento = new JButton("Inserimento");
	btnAggiornare = new JButton("Aggiorna");
	btnCancellare = new JButton("Cancellare");
	btnVisualizzare = new JButton("Visualizzare");
	btnMenuOperazioni = new JButton("Torna al menu Operazioni");
	JTRisultato = new JTextArea();
	// Inseriamo lo scroll a JTRisultato 
	JScrollPane scrollPane = new JScrollPane (JTRisultato);
	
	// aggiunta dei componenti all'interafaccia grafica
	JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
	
	//oggetti
	panel.add(lblId);
	panel.add(txtId);
	panel.add(lblNomeTrattamento);
	panel.add(txtNomeTrattamento);
	panel.add(lblDurata);
	panel.add(txtDurata);
	panel.add(lblIdOperatore);
	panel.add(txtIdOperatore);
	panel.add(lblPrezzo);
	panel.add(txtPrezzo);
	
	panel.add(btnInserimento);
	panel.add(btnAggiornare);
	panel.add(btnCancellare);
	panel.add(btnVisualizzare);
	panel.add(btnMenuOperazioni);
	//panel.add(JTRisultato); andava in conflitto con scroll Pane
	panel.add(scrollPane, BorderLayout.CENTER);
	
	//gestione del bottone inserimento con il proprio listener 
	
	btnInserimento.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//recupero dei dati inseriti dall'utente
			
			String NomeTrattamento = txtNomeTrattamento.getText();
			double Durata = Double.parseDouble(txtDurata.getText());
			int IdOperatore = Integer.parseInt(txtIdOperatore.getText());
			double Prezzo = Double.parseDouble(txtPrezzo.getText());
			// apertura e connessione del database ed inserimento del nuovo trattamento
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			// blocco codice da eseguire all'intrnon del pulsante inserimento
			try {
				conn = DriverManager.getConnection(url, username, password);
				String sql = "INSERT INTO trattamento(nomeTrattamento,durata,prezzo,idOperatore) VALUES(?,?,?,?)";
				pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e prepararel'ambiente dove inseriremo la stringa
			
				pstmt.setString(1, NomeTrattamento);
				pstmt.setDouble(2, Durata);
				pstmt.setDouble(3, Prezzo);
				pstmt.setInt(4, IdOperatore);
				
				// avviare all'interno del databasecon execute
				int righeInserite = pstmt.executeUpdate();
				if (righeInserite > 0) {
					JOptionPane.showMessageDialog(null, "Nuovo Trattamento inserito con successo!");
				}
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}finally {
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
			txtNomeTrattamento.setText("");
			txtDurata.setText("");
			txtPrezzo.setText("");
			txtIdOperatore.setText("");
		}
	
	}
	);
	//fine del pulsante Inserimento
	//inizio del pulsante aggiorna
	btnAggiornare.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//recupero dei dati inseriti dall'utente
			int id = Integer.parseInt(txtId.getText());
			String NomeTrattamento = txtNomeTrattamento.getText();
			double Durata = Double.parseDouble(txtDurata.getText());
			int IdOperatore = Integer.parseInt(txtIdOperatore.getText());
			double Prezzo = Double.parseDouble(txtPrezzo.getText());
			// apertura e connessione del database ed inserimento del nuovo studente
			Connection conn = null;
			PreparedStatement pstmt = null;
			
			// blocco codice da eseguire all'intrno del pulsante salva
			try {
				conn = DriverManager.getConnection(url, username, password);
				String sql = "UPDATE trattamento SET nomeTrattamento = ? , durata = ? ,  prezzo = ? , idOperatore = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e prepararel'ambiente dove inseriremo la stringa
			
				pstmt.setString(1, NomeTrattamento);
				pstmt.setDouble(2, Durata);
				pstmt.setDouble(3, Prezzo);
				pstmt.setInt(4, IdOperatore);
				pstmt.setInt(5, id);
				
				// avviare all'interno del database con execute
				int righeInserite = pstmt.executeUpdate();
				if (righeInserite > 0) {
					JOptionPane.showMessageDialog(null, "Trattamento aggiornato con successo!");
				}
				
				
			}catch(SQLException ex) {
				ex.printStackTrace();
			}finally {
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
			txtNomeTrattamento.setText("");
			txtDurata.setText("");
			txtPrezzo.setText("");
			txtIdOperatore.setText("");
		}
	
	}
	);// fine del pulsante aggiorna
	// Pulsante delete
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
				String sql = "DELETE FROM trattamento WHERE id=?";
				pstmt = conn.prepareStatement(sql); // comando che dice: devi utilizzare i valori e prepararel'ambiente dove inseriremo la stringa
				
				pstmt.setInt(1, Id);
				

				// avviare all'interno del database con execute
				int righeInserite = pstmt.executeUpdate();

				if (righeInserite > 0) {
					JOptionPane.showMessageDialog(null, "Trattamento cancellato con successo!");
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (pstmt != null)pstmt.close();
						
					if (conn != null)conn.close();
						
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			// reset dei campi del form
			txtId.setText("");
			txtNomeTrattamento.setText("");
			txtDurata.setText("");
			txtPrezzo.setText("");
			txtIdOperatore.setText("");
			
		}
	});
	// fine del pulsante delete
			// Pulsante Visualizzare
	btnVisualizzare.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			// apertura e connessione del database
			Connection conn = null;
			Statement stmt = null;
			//ripuliamo il campo per la visualizzazione dei risultati
			JTRisultato.setText("");
			// blocco codice da eseguire all'intrno del pulsante visualizza

			try {
				conn = DriverManager.getConnection(url, username, password);
				String sql = "SELECT * FROM trattamento";
				stmt = conn.createStatement(); // comando che dice: devi utilizzare i valori e preparare
				ResultSet rs = stmt.executeQuery(sql); // l'ambiente dove inseriremo la stringa
			while(rs.next()) {
				int id = rs.getInt("id");
				String nomeTrattamento =rs.getString("nomeTrattamento");
				double	durata = rs.getDouble("durata");
				double	prezzo = rs.getDouble("prezzo");
				int idOperatore = rs.getInt("idOperatore");
				//stampo i record
				String risultato ="\nID: " + id + " ,NOME TRATTAMENTO: " + nomeTrattamento + " DURATA: " + durata +" ,PREZZO: "+ prezzo+" ,ID OPERATORE: "+ idOperatore;
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
