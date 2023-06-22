package net.codejava;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;

public class Pagamento extends JFrame {

	private static final String url = "jdbc:mysql://localhost:3306/dbcentroestetico";
	private static final String username = "root";
	private static final String password = "#";

	private JLabel jlbid, jlbIdCliente, jlbTrattamento, jlbProdotto;
	private JTextField textid, textIdCliente, textTrattamento, textProdotto;
	private JTextArea textArea;
	private JButton jbtGO, UPDATE, DELETE, SELECT, btnMenuOperazioni;

	public Pagamento(int ruolo) {
		setTitle("Modulo Pagamento: ");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// iniziamo le label, il testo e i bottoni
		jlbid = new JLabel("ID :");
		jlbIdCliente = new JLabel("Id del cliente: ");
		jlbTrattamento = new JLabel("Il Trattamento usufruito dal cliente e: ");
		jlbProdotto = new JLabel("Il prodotto utilizzato per il cliente e: ");

		textid = new JTextField(20);
		textIdCliente = new JTextField(20);
		textTrattamento = new JTextField(20);
		textProdotto = new JTextField(20);
		textArea = new JTextArea();

		jbtGO = new JButton("INSERIMENTO");
		UPDATE = new JButton("AGGIORNAMENTO");
		DELETE = new JButton("ELIMINAZIONE");
		SELECT = new JButton("VISUALIZZAZIONE");
		btnMenuOperazioni = new JButton("Torna al menu Operazioni");

		// AGGIUNTA del pannello dell'interfaccia grafica
		JPanel panel = new JPanel(new GridLayout(8, 2));

		panel.add(jlbid);
		panel.add(textid);

		panel.add(jlbIdCliente);
		panel.add(textIdCliente);

		panel.add(jlbTrattamento);
		panel.add(textTrattamento);

		panel.add(jlbProdotto);
		panel.add(textProdotto);

		panel.add(jbtGO);
		panel.add(UPDATE);
		panel.add(DELETE);
		panel.add(SELECT);
		panel.add(btnMenuOperazioni);
		panel.add(textArea);

		// Metodo per la creazione dei bottoni
		jbtGO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int IdCliente = Integer.parseInt(textIdCliente.getText());
				int Trattamento = Integer.parseInt(textTrattamento.getText());
				int Prodotto = Integer.parseInt(textProdotto.getText());

				try {

					String sql = "INSERT INTO pagamento(IdCliente,Trattamento,Prodotto) VALUES (?,?,?)";
					Connection conn = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = conn.prepareStatement(sql);

					stmt.setInt(1, IdCliente);
					stmt.setInt(2, Trattamento);
					stmt.setInt(3, Prodotto);

					// chiusura database sicurezza dati
					stmt.close();
					conn.close();

				} catch (SQLException ex) {
					ex.printStackTrace();

				}

			}
		}

		);

		UPDATE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int IdCliente = Integer.parseInt(textIdCliente.getText());
				int Trattamento = Integer.parseInt(textTrattamento.getText());
				int Prodotto = Integer.parseInt(textProdotto.getText());
				int id = Integer.parseInt(textid.getText());
				try {

					String UPDATE_QUERY = "UPDATE pagamento SET IdCliente=?,Trattamento=?,Prodotto =? WHERE id =?";
					Connection conn = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);

					stmt.setInt(1, IdCliente);
					stmt.setInt(2, Trattamento);
					stmt.setInt(3, Prodotto);
					stmt.setInt(4, id);

					// chiusura della connessione
					stmt.close();
					conn.close();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		});

		DELETE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int IdCliente = Integer.parseInt(textIdCliente.getText());
				int Trattamento = Integer.parseInt(textTrattamento.getText());
				int Prodotto = Integer.parseInt(textProdotto.getText());
				int id = Integer.parseInt(textid.getText());

				try {
					String DELETE_QUERY = "DELETE *FROM pagamento WHERE id =?";
					Connection conn = DriverManager.getConnection(url, username, password);
					PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY);

					stmt.setInt(1, IdCliente);
					stmt.setInt(2, Trattamento);
					stmt.setInt(3, Prodotto);
					stmt.setInt(4, id);

					// chiusura della connessione
					stmt.close();
					conn.close();

				} catch (SQLException ex) {
					ex.printStackTrace();

				}

			}

		});

		SELECT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String SELECT_QUERY = "SELECT *FROM pagamento";
					Connection conn = DriverManager.getConnection(url, username, password);
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(SELECT_QUERY);

					while (rs.next()) {
						int IdCliente = rs.getInt("id");
						int Trattamento = rs.getInt("Trattamento");
						int Prodotto = rs.getInt("Prodotto");
						int id = rs.getInt("ID");

						String Result = "IdCliente: " + IdCliente + "Trattamento; " + Trattamento + "Prodotto: "
								+ Prodotto + "id: " + id;
						textArea.append(Result);

					}

					// chiusura connessione

					stmt.close();
					conn.close();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}

		);

		btnMenuOperazioni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (ruolo) {
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
		
		
		// aggiungiamo comandi per aggiungere interfaccia grafica
		add(panel);
		setVisible(true);

	}

}