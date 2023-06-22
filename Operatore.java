package net.codejava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;

public class Operatore  extends JFrame{
//inizializzazione dei parametri della classe
		
		
		private static final String url ="jdbc:mysql://localhost:3306/dbcentroestetico";
		private static final String username ="root";
		private static final String password ="#";
		
		
		
		private JLabel lblid,lblNome, lblCognome, lblemail,lbltelefono,lblindirizzo,lblruolo;
		private JTextField textid,textNome, textCognome, textemail, texttelefono,textindirizzo,textruolo ;
		private JTextArea textArea;
		private JButton btnGO, UPDATE, DELETE, SELECT, btnMenuOperazioni;

		
		public  Operatore(int  ruolo) {
			
			//imp. dell'interfaccio grafica
			setTitle(" Classe Operatore: ");
			setSize(800,600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			
			//inizializzo componeti inerfaccia grafica
			lblid = new JLabel("id:");
			lblNome = new JLabel("Nome: ");
			lblCognome = new JLabel("Cognome");
			lblemail = new JLabel("email");
			lbltelefono = new JLabel("telefono");
			lblindirizzo = new JLabel("indirizzo");
			lblruolo = new JLabel("ruolo");
			
			
			textid = new JTextField(20);
			textNome = new JTextField(20);
			textCognome = new JTextField(20);
			textemail = new JTextField(20);
			texttelefono = new JTextField(20);
			textindirizzo = new JTextField(20);
			textruolo = new JTextField(20);
			textArea= new JTextArea();
			btnGO =new JButton("Inserimento");
			UPDATE =new JButton("Aggiornamento");
			DELETE =new JButton("Eliminazione");
			SELECT = new JButton("Visualizzazione");
			btnMenuOperazioni = new JButton("Torna al menu Operazioni");
			//aggiunta dei componenti all'interfaccia grafica panel
			JPanel panel = new JPanel(new GridLayout(12,2));
			
			panel.add(lblid);
			panel.add(textid);
			panel.add(lblNome);
			panel.add(textNome);
			panel.add(lblCognome);
			panel.add(textCognome);
			panel.add(lblemail);
			panel.add(textemail);
			panel.add(lbltelefono);
			panel.add(texttelefono);
			panel.add(lblindirizzo);
			panel.add(textindirizzo);
			panel.add(lblruolo);
			panel.add(textruolo);
			
			
			
			panel.add(btnGO);
			panel.add(UPDATE);
			panel.add(DELETE);
			panel.add(SELECT);
			panel.add(btnMenuOperazioni);
			
			panel.add(textArea);
			//metodo bottoni
			btnGO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String Nome =textNome.getText();
					String Cognome =textCognome.getText();
					String email =textemail.getText();
					String telefono =texttelefono.getText();
					String indirizzo =textindirizzo.getText();
					String ruolo =textruolo.getText();

				try {
					String sql = "INSERT INTO operatore(Nome,Cognome, email, telefono,indirizzo,ruolo) VALUES (?,?,?,?,?,?)";
					Connection conn = DriverManager.getConnection(url,username,password);
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, Nome);
					stmt.setString(2, Cognome);
					stmt.setString(3, email);
					stmt.setString(4, telefono);
					stmt.setString(5, indirizzo);
					stmt.setString(6, ruolo);
					
					
					
					//contatore delle righe
					int righeInserite = stmt.executeUpdate();
					
					//finestra di popup
					if(righeInserite >0) {
						JOptionPane.showMessageDialog(null," Operatore inserito con successo!!");
					}
					
					//chiusura del database per sicurazza dati
					stmt.close();
					conn.close();
					
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
		
}
		}		);

			UPDATE.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Nome =textNome.getText();
					String Cognome =textCognome.getText();
					String email =textemail.getText();
					String telefono =texttelefono.getText();
					String indirizzo =textindirizzo.getText();
					String ruolo =textruolo.getText();
					int id = Integer.parseInt(textid.getText()); 
					try {
					String UPDATE_TABLE ="UPDATE operatore SET  Nome =?, Cognome= ?,email =?,telefono=?,indirizzo=?, ruolo =? where id =?";
					Connection conn = DriverManager.getConnection(url,username,password);
					PreparedStatement stmt = conn.prepareStatement(UPDATE_TABLE);
					stmt.setString(1, Nome);
					stmt.setString(2, Cognome);
					stmt.setString(3, email);
					stmt.setString(4, telefono);
					stmt.setString(5, indirizzo);
					stmt.setString(6, ruolo);
					stmt.setInt(7,id);
					//contatore che tiene conto delle righe. Non necessario
					int righe1Inserite = stmt.executeUpdate();
					
					//finestra di popup
					if(righe1Inserite >0) {
						JOptionPane.showMessageDialog(null," Operatore MOdificato con successo!!");
					}
					stmt.close();
					conn.close();
					}catch(SQLException ex) {
						ex.printStackTrace();
					}
					}}
			);
			DELETE.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String Nome =textNome.getText();
					String Cognome =textCognome.getText();
					String email =textemail.getText();
					String telefono =texttelefono.getText();
					String indirizzo =textindirizzo.getText();
					String ruolo =textruolo.getText();
					int id = Integer.parseInt(textid.getText());
					try {
						String DELETE_TABLE = "DELETE *FROM operatore where Id =?";
						Connection conn = DriverManager.getConnection(url,username,password);
						PreparedStatement stmt = conn.prepareStatement(DELETE_TABLE);
						stmt.setString(1, Nome);
						stmt.setString(2, Cognome);
						stmt.setString(3, email);
						stmt.setString(4, telefono);
						stmt.setString(5, indirizzo);
						stmt.setString(6, ruolo);
						stmt.setInt(7,id);			
						//contatore che tiene conto delle righe. Non necessario
						int righeInserite3 = stmt.executeUpdate();
						
						//finestra di popup
						if(righeInserite3 >0) {
							JOptionPane.showMessageDialog(null," Utente eliminato con successo!!");
						}
						stmt.close();
						conn.close();
						
						
						}catch(SQLException ex) {
							ex.printStackTrace();
						
						}
						}
					
					
					
					
				}
			);
			SELECT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
					try {
						String SELECT_QUERY ="SELECT *FROM Operatore ";
						Connection conn = DriverManager.getConnection(url,username,password);
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery(SELECT_QUERY);
						while(rs.next()){
						
						String Nome= rs.getString("nome");
						String Cognome = rs.getString("cognome");
						String email = rs.getString("email");
						String telefono= rs.getString("telefono");
						String indirizzo = rs.getString("indirizzo");
						String ruolo = rs.getString("ruolo");
						int id	= rs.getInt	("id");
						
						String Result = "Nome: "+ Nome + "Cognome" + Cognome + "email: " +email + "Telefono: " +telefono +"Indirizzo: " +indirizzo + "Ruolo: "+ ruolo+ "ID: " +id;
						textArea.append(Result);
						
						}
						
						stmt.close();
						conn.close();
						
						
						}catch(SQLException ex) {
							ex.printStackTrace();
						
						}
						}
					
					
			}	);
			
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
		