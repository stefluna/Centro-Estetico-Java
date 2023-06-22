package net.codejava;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;

public class Stanza extends JFrame {
		private static final String url ="jdbc:mysql://localhost:3306/dbcentroestetico";
		private static final String username ="root";
		private static final String password ="#";
		
		
		private JLabel jblid,jblN_Stanza,jblNomeStanza,jblTrattamento;
		private JTextField textid, textN_Stanza, textNomeStanza, textTrattamento;
		private JTextArea textArea;
		private JButton GO, UPDATE, DELETE,SELECT, btnMenuOperazioni;
		
		
	public Stanza(int ruolo) {
		setTitle("ROOM: ");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		jblid = new JLabel("ID: ");
		jblN_Stanza = new JLabel( "ROOM's NUMBER: ");
		jblNomeStanza = new JLabel ("ROOM's NAME: ");
		jblTrattamento = new JLabel("ROOM's SERVICE: ");
		
		textid = new JTextField(20);
		textN_Stanza = new JTextField(5);
		textNomeStanza = new JTextField(20);
		textTrattamento =new JTextField(7);
		textArea = new JTextArea();
		
		GO = new JButton("INSERIMENTO");
		UPDATE = new JButton ("AGGIORNAMENTO");
		DELETE = new JButton("ELIMINAZIONE");
		SELECT = new JButton("VISUALIZZAZIONE");
		btnMenuOperazioni = new JButton("Torna al menu Operazioni");
		
		//inizializziamo il pannello delle interfacce
		JPanel panel = new JPanel( new GridLayout(8,2));
		
		panel.add(jblid);
		panel.add(textid);
		panel.add(jblN_Stanza);
		panel.add(textN_Stanza);
		panel.add(jblNomeStanza);
		panel.add(textNomeStanza);
		panel.add(jblTrattamento);
		panel.add(textTrattamento);
		
		
		panel.add(GO);
		panel.add(UPDATE);
		panel.add(DELETE);
		panel.add(SELECT);
		panel.add(btnMenuOperazioni);
		panel.add(textArea); 
		
		//creazione metodi per avviare i metodi
		
		GO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				
				int N_Stanza = Integer.parseInt(textN_Stanza.getText());
				String NomeStanza = textNomeStanza.getText();
				int Trattamento =Integer.parseInt(textTrattamento.getText());
				
				
				try {
					String sql ="INSERT INTO Stanza (N_Stanza,NomeStanza,Trattamento) VALUES (?,?,?)";
					Connection conn = DriverManager.getConnection(url,username,password);
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setInt(1,N_Stanza);
					stmt.setString(2, NomeStanza);
					stmt.setInt(3, Trattamento);
					
					//contatore delle righe
					int righeInserite = stmt.executeUpdate();
					
					//finestra di popup
					if(righeInserite >0) {
						JOptionPane.showMessageDialog(null," Stanza inserita con successo!!");
					}
					
					//Chiusura sicurezza database
					stmt.close();
					conn.close();
					
				}catch(SQLException ex) {
					
					ex.printStackTrace();
					
				}
			
			
			
			
			}	
			
			
			
			
		}
		);
		
		UPDATE.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {	
			
				
			
				int id= Integer.parseInt(textid.getText());
				int N_Stanza = Integer.parseInt(textN_Stanza.getText());
				String NomeStanza = textNomeStanza.getText();
				int Trattamento =Integer.parseInt(textTrattamento.getText());
				
				try {
				
				String UPDATE_QUERY = "UPDATE Stanza SET N_Stanza=?,NomeStanza =?,Trattamento =?, WHERE id =?";
				Connection conn = DriverManager.getConnection(url,username,password);
				PreparedStatement stmt= conn.prepareStatement(UPDATE_QUERY);
				
				stmt.setInt(1,N_Stanza);
				stmt.setString(2, NomeStanza);
				stmt.setInt(3, Trattamento);
				stmt.setInt(4,id);
			
				//chiusura connessione database
				
				stmt.close();
				conn.close();
				
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				
				
				
			
			}}
				
				
				);
		
		DELETE.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					int id= Integer.parseInt(textid.getText());
					int N_Stanza = Integer.parseInt(textN_Stanza.getText());
					String NomeStanza = textNomeStanza.getText();
					int Trattamento =Integer.parseInt(textTrattamento.getText());
		
					
					
		try {			
		String	DELETE_QUERY ="DELETE *FROM Stanza WHERE id =?";
		Connection conn = DriverManager.getConnection(url,username, password);
		PreparedStatement stmt =conn.prepareStatement(DELETE_QUERY);
		
		stmt.setInt (1, N_Stanza);
		stmt.setString(2, NomeStanza);
		stmt.setInt(3, Trattamento);
		stmt.setInt(4, id);
		
		
		stmt.close();
		conn.close();
		
		
		
		
		
		
				}catch(SQLException ex) {
					ex.printStackTrace();
				}
				}});
		
		
		SELECT.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				

					
					
					
					
			try {
				
			String SELECT_QUERY ="SELECT *FROM Stanza";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SELECT_QUERY);
			while(rs.next()) {
				int id = rs.getInt("id");
				int N_Stanza =rs.getInt("N_Stanza");
				String NomeStanza =rs.getString("NomeStanza");
				int Trattamento =rs.getInt("Trattamento");
				
				String Result = "ID: "+ id + "N_Stanza: "+ N_Stanza + "NomeStanza: "+ NomeStanza+ "Trattamento: "+ Trattamento;
				textArea.append(Result);
				
				
			}
			
			//chiusura connessione database
				rs.close();
				stmt.close();
				conn.close();
			
			
			
			
				
				}catch(SQLException ex) {
				ex.printStackTrace();
				
			}
				
		
				}}
				);
		
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
