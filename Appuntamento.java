package net.codejava;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

public class Appuntamento extends JFrame {
	private static final String url = "jdbc:mysql://localhost:3306/dbCentroEstetico";
	private static final String username = "root";
	private static final String password = "#";

	private static final String INSERT_APPUNTAMENTO = "INSERT INTO appuntamento(data, ora, idTrattamento, idCliente, idOperatore) VALUES(?,?,?,?,?)";
	private static final String VISUALIZE_APPUNTAMENTI = "SELECT * FROM appuntamento";
	private static final String DELETE_APPUNTAMENTO = "DELETE FROM appuntamento WHERE id = ?";
	private static final String UPDATE_APPUNTAMENTO = "UPDATE appuntamento SET data = ?, ora = ?, idTrattamento = ?, idCliente = ?, idOperatore = ? WHERE id = ?";
	private static final String VISUALIZE_TRATTAMENTI = "SELECT * FROM trattamento";
	private static final String VISUALIZE_TABLE = "SELECT * FROM %s";
	private static final String COUNT_RECORDS_TABLE = "SELECT COUNT(*) AS record_count FROM ?";
	
	private static final String idTABLEChoice = "Selezionare il %s";
	

	private JTextField txtData, txtOra;
	private JLabel lblData, lblOra, lblIdTrattamento, lblIdCliente, lblIdOperatore, lblDescription, lblEmpty,
			lblIdAppuntamento;
	private JTextArea textArea;
	private JButton btnSave, btnUpdate, btnDelete, btnVisualize, btnMenuOperazioni;
	private JComboBox<String> cbIdTrattamento, cbIdCliente, cbIdOperatore, cbIdAppuntamento;

	
	
	public Appuntamento(int ruolo) {
		setTitle("Appuntamenti del Centro Estetico BitCamp");
		setSize(600, 820);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		String[] idTrattamentoChoice = { "Selezionare il Trattamento" };
		String[] idClienteChoice = { "Selezionare il Cliente" };
		String[] idOperatoreChoice = { "Selezionare l'Operatore" };
		String[] idAppuntamentoChoice = { "Selezionare l'Appuntamento" };

		lblDescription = new JLabel("Operazioni Consetite sugli appuntamenti:");
		lblEmpty = new JLabel(
				"<html><br/>- Inserisci nuovo appuntamento<br/>- Cancela Appuntamento<br/>- Aggiorna Appuntamento<br/>- Visualizza gli appuntamenti </html>");

		lblData = new JLabel("Data appuntamento");
		txtData = new JTextField("YYYY-MM-DD (2023-06-20)");

		lblOra = new JLabel("Ora appuntamento");
		txtOra = new JTextField("HH:MM (23:09)");

		lblIdTrattamento = new JLabel("Id Trattamento");
		cbIdTrattamento = new JComboBox<String>(idTrattamentoChoice);

		lblIdCliente = new JLabel("Id Cliente");
		cbIdCliente = new JComboBox<String>(idClienteChoice);

		lblIdOperatore = new JLabel("Id Operatore");
		cbIdOperatore = new JComboBox<String>(idOperatoreChoice);

		lblIdAppuntamento = new JLabel("Id Appuntamento");
		cbIdAppuntamento = new JComboBox<String>(idAppuntamentoChoice);

		btnSave = new JButton("Save appuntamento");
		btnUpdate = new JButton("Update appuntamento");
		btnDelete = new JButton("Delete appuntamento");
		btnVisualize = new JButton("Visualize appuntamenti");
		btnMenuOperazioni = new JButton("Torna al menu");

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		ArrayList<JComboBox> cbIdTABLES = new ArrayList<JComboBox>();
		ArrayList<String> tabelle = new ArrayList<String>();
		cbIdTABLES.addAll(Arrays.asList(cbIdTrattamento, cbIdCliente, cbIdOperatore, cbIdAppuntamento));
		tabelle.addAll(Arrays.asList("Trattamento", "Cliente", "Operatore", "Appuntamento"));
		riempiCbs(cbIdTABLES, tabelle);
		
		
		// Create a map to store the selected values for each JComboBox
		Map<JComboBox, Object> selectedValues = new HashMap<>();

		// Initialize the selected values map
		for (JComboBox cb : cbIdTABLES) {
		    selectedValues.put(cb, null);
		}

		// Add ActionListener for each JComboBox
		for (JComboBox cb : cbIdTABLES) {
		    cb.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            // Get the selected value
		            Object selectedItem = cb.getSelectedItem();

		            // Check if the selected value is different from the previously selected value
		            if (!Objects.equals(selectedItem, selectedValues.get(cb))) {
		                // Update the selected value in the map
		                selectedValues.put(cb, selectedItem);

		                // Update the JComboBox values
		                riempiCbs2(cbIdTABLES, tabelle, selectedValues);
		            }
		        }
		    });
		}
		

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!txtData.getText().isEmpty()) {
					if (!txtOra.getText().isEmpty()) {

							if(checkIdSelected(cbIdTABLES, tabelle)) {
				                // Valid value selected
								saveAppuntamento(txtData, txtOra, cbIdTABLES);
								clearAllFields(txtData, txtOra);
								riempiCbs2(cbIdTABLES, tabelle, selectedValues);
				            } else {
				                // Invalid value selected or default value selected
				            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected. ");
				            }						
					} else {
						JOptionPane.showMessageDialog(null, "Non è stata inserito un orario");
					}
				} else if (txtData.getText().isEmpty()) {
					if (txtOra.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Non sono stati inseriti la data e l'orario");
					} else
						JOptionPane.showMessageDialog(null, "Non è stata inserita una data");
				}
			}
		}

		);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Object selectedItem = cbIdAppuntamento.getSelectedItem();

				if (!txtData.getText().isEmpty()) {
					if (!txtOra.getText().isEmpty()) {
						//if (selectedItem != null && selectedItem instanceof String) {
							//String selectedValue = (String) selectedItem;		            
							//String choicesAppuntamento = String.format(idTABLEChoice, "Appuntamento");
				            //if (!selectedValue.equals(choicesAppuntamento)) {
							if(checkIdSelected(cbIdTABLES, tabelle)) {
				                // Valid value selected
				                //System.out.println("Selected value is valid: " + selectedValue);				            	
								updateAppuntamento(txtData, txtOra, cbIdTABLES);
								clearAllFields(txtData, txtOra);
								riempiCbs2(cbIdTABLES, tabelle, selectedValues);
				            } else {
				                // Invalid value selected or default value selected
				            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected. ");
				            }

					} else {
						JOptionPane.showMessageDialog(null, "Non è stata inserita un orario");
					}
				} else if (txtData.getText().isEmpty()) {
					if (txtOra.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Non è stata inserita una data e un orario");
					} else
						JOptionPane.showMessageDialog(null, "Non è stata inserita una data");
				}
			}
		}

		);

		btnDelete.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {
				Object selectedItem = cbIdAppuntamento.getSelectedItem();
		        if (selectedItem != null && selectedItem instanceof String) {
		            String selectedValue = (String) selectedItem;		            
					String menu = String.format(idTABLEChoice, "Appuntamento");
		            if (!selectedValue.equals(menu)) {
		                // Valid value selected
		                //System.out.println("Selected value is valid: " + selectedValue);
		            	deleteAppuntamento(txtData, txtOra, cbIdAppuntamento);
		            	riempiCbs2(cbIdTABLES, tabelle, selectedValues);
		            } else {
		                // Invalid value selected or default value selected
		            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected: " + selectedValue);
		            }
		        } else {
		            // No value selected
		        	JOptionPane.showMessageDialog(null, "No value is selected");
		        }
				
			}

		});

		btnVisualize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualizeAppuntamenti(txtData, txtOra, textArea);
				riempiCbs2(cbIdTABLES, tabelle, selectedValues);
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
		
		
		JPanel panel = new JPanel(new GridLayout(10, 2));
		panel.add(lblDescription);
		panel.add(lblEmpty);
		panel.add(lblData);
		panel.add(txtData);
		panel.add(lblOra);
		panel.add(txtOra);
		panel.add(lblIdTrattamento);
		panel.add(cbIdTrattamento);
		panel.add(lblIdCliente);
		panel.add(cbIdCliente);
		panel.add(lblIdOperatore);
		panel.add(cbIdOperatore);
		panel.add(lblIdAppuntamento);
		panel.add(cbIdAppuntamento);

		panel.add(btnSave);
		panel.add(btnUpdate);
		panel.add(btnDelete);
		panel.add(btnVisualize);
		panel.add(btnMenuOperazioni);

		panel.add(scrollPane, BorderLayout.CENTER);

		add(panel);
		setVisible(true);

	}

	private static void saveAppuntamento(JTextField txtData, JTextField txtOra, ArrayList<JComboBox> cbIdTABLES) {
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(INSERT_APPUNTAMENTO)) {
			pstmt.setString(1, txtData.getText());
			pstmt.setString(2, txtData.getText() + " " + txtOra.getText());
			pstmt.setString(3, extractIdFromRecord(cbIdTABLES.get(1).getSelectedItem().toString())); // IdTrattamento
			pstmt.setString(4, extractIdFromRecord(cbIdTABLES.get(2).getSelectedItem().toString())); // IdCliente
			pstmt.setString(5, extractIdFromRecord(cbIdTABLES.get(3).getSelectedItem().toString())); // IdOperatore

			int recordInseriti = pstmt.executeUpdate();

			if (recordInseriti > 0) {
				JOptionPane.showMessageDialog(null, "E' stato inserito corretamente un nuovo appuntamento");
			} else {
				JOptionPane.showMessageDialog(null, "Non è stato possibile inserire il nuovo appuntamento");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore durante la connessione al database");
		} finally {

		}
	}

	private static void updateAppuntamento(JTextField txtData, JTextField txtOra, ArrayList<JComboBox> cbIdTABLES) {
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(UPDATE_APPUNTAMENTO)) {
			pstmt.setString(1, txtData.getText());
			pstmt.setString(2, txtData.getText() + " " + txtOra.getText());
			pstmt.setString(3, extractIdFromRecord(cbIdTABLES.get(1).getSelectedItem().toString())); // IdTrattamento
			pstmt.setString(4, extractIdFromRecord(cbIdTABLES.get(2).getSelectedItem().toString())); // IdCliente
			pstmt.setString(5, extractIdFromRecord(cbIdTABLES.get(3).getSelectedItem().toString())); // IdOperatore
			pstmt.setString(6, extractIdFromRecord(cbIdTABLES.get(4).getSelectedItem().toString())); // IdAppuntamenti

			int recordAggiornati = pstmt.executeUpdate();

			if (recordAggiornati > 0) {
				JOptionPane.showMessageDialog(null, "E' stato aggiornato corretamente l'appuntamento con ID: "
						+ extractIdFromRecord(cbIdTABLES.get(4).getSelectedItem().toString()));
			} else {
				JOptionPane.showMessageDialog(null, "Non è stato possibile aggiornare l'appuntamento con ID: "
						+ extractIdFromRecord(cbIdTABLES.get(4).getSelectedItem().toString()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore durante la connessione al database");
		} finally {

		}
	}

	private static void deleteAppuntamento(JTextField txtData, JTextField txtOra, JComboBox cbIdAppuntamento) {
		extractIdFromRecord(cbIdAppuntamento.getSelectedItem().toString());
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(DELETE_APPUNTAMENTO)) {
			pstmt.setString(1, extractIdFromRecord(cbIdAppuntamento.getSelectedItem().toString()));

			int recordAggiornati = pstmt.executeUpdate();

			if (recordAggiornati > 0) {
				JOptionPane.showMessageDialog(null, "E' stato corretamente eliminato l'appuntamento con ID: "
						+ extractIdFromRecord(cbIdAppuntamento.getSelectedItem().toString()));
			} else {
				JOptionPane.showMessageDialog(null, "Non è stato possibile eliminare l'appuntamento con ID: "
						+ extractIdFromRecord(cbIdAppuntamento.getSelectedItem().toString()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore durante la connessione al database");
		} finally {

		}
	}

	private static void visualizeAppuntamenti(JTextField txtData, JTextField txtOra, JTextArea textArea) {
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(VISUALIZE_APPUNTAMENTI)) {
			textArea.setText("");
			int i = 0;
			while (rs.next()) {
				i++;
				String record = i + ") Data: " + rs.getString(3) + ", ID Appuntamento: " + rs.getInt(1) + "\n"
						+ "ID Trattamento: " + rs.getInt(4) + ", ID Cliente: " + rs.getString(5) + ", ID Operatore: "
						+ rs.getString(6) + "\n\n";
				textArea.append(record);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante la connessione al database");
			e.printStackTrace();
		} finally {
			// clearAllFields(txtData, txtOra, txtIdTrattamento, txtIdCliente,
			// txtIdOperatore, txtIdAppuntamento, textArea);
		}
	}

	private static void clearAllFields(JTextField txtData, JTextField txtOra) {
		txtData.setText("");
		txtOra.setText("");
		// txtIdTrattamento.setText("");
		// txtIdCliente.setText("");
		// txtIdOperatore.setText("");
		// txtIdAppuntamento.setText("");
	}

	private static void riempiCbIdTrattamento(JComboBox cbIdTrattamento) {
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(VISUALIZE_TRATTAMENTI)) {
			// int i = 0;
			ArrayList<String> trattamenti = new ArrayList<String>();
			while (rs.next()) {
				// i++;
				String record = (trattamenti.size() + 1) + ") " + rs.getString("nomeTrattamento") + ", ID Trattamento: "
						+ rs.getInt(1) + "\n\n";
				trattamenti.add(record);
			}
			String[] arrayTrattamenti = trattamenti.toArray(new String[0]);
			cbIdTrattamento.setModel(new DefaultComboBoxModel<>(arrayTrattamenti));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Errore durante la connessione al database, per recuperare i trattamenti");
			e.printStackTrace();
		} finally {

		}
	}

	private static void riempiCbIdTABLE(JComboBox cbIdTABLE, String TABLE_NAME) {
		String query = String.format(VISUALIZE_TABLE, TABLE_NAME);
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			int i = 0;
			ArrayList<String> trattamenti = new ArrayList<String>();
			//String idTABLEChoice = "Selezionare il %s";
			String menu = String.format(idTABLEChoice, TABLE_NAME);
			trattamenti.add(menu);

			while (rs.next()) {
				i++;
				String record = (trattamenti.size() + 1) + ") " + rs.getString(2) + ", ID %s: " + rs.getInt(1);
				record = String.format(record, TABLE_NAME);
				trattamenti.add(record);
			}
			if (i > 0) {
				String[] arrayTrattamenti = trattamenti.toArray(new String[0]);
				cbIdTABLE.setModel(new DefaultComboBoxModel<>(arrayTrattamenti));
			} else {

				String[] choices = { menu };
				// String[] arrayTrattamenti = trattamenti.toArray(new String[0]);
				cbIdTABLE.setModel(new DefaultComboBoxModel<>(choices));
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Errore durante la connessione al database, per recuperare i trattamenti");
			e.printStackTrace();
		} finally {

		}
	}

	private void riempiCbs(ArrayList<JComboBox> cbIdTABLES, ArrayList<String> tabelle) {
		int i = 0;
		for (JComboBox cb : cbIdTABLES) {
			riempiCbIdTABLE(cb, tabelle.get(i));
			i++;
		}
	}

	private static String extractIdFromRecord(String record) {
		int startIndex = record.indexOf(", ID Trattamento: ") + 18; // 18 is the length of ", ID Trattamento: "
		int endIndex = record.length();

		String idString = record.substring(startIndex, endIndex).trim();
		// int id = Integer.parseInt(idString);

		return idString;
	}
	
	private static boolean checkIdSelected(ArrayList<JComboBox> cbIdTABLES, ArrayList<String> tabelle) {
		int i = 0;
		
		for(JComboBox cb: cbIdTABLES) {
			Object selectedItem = cb.getSelectedItem();
	        if (selectedItem != null && selectedItem instanceof String) {
	            String selectedValue = (String) selectedItem;		            
				String menu = String.format(idTABLEChoice, tabelle.get(i));
	            if (!selectedValue.equals(menu)) {
	                // Valid value selected
	                //System.out.println("Selected value is valid: " + selectedValue);
	            } else {
	                // Invalid value selected or default value selected
	            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected: " + selectedValue);
	            	return false;
	            }
	        } else {
	            // No value selected
	        	JOptionPane.showMessageDialog(null, "No value is selected");
	        	return false;
	        }
			
		}
		return true;
		
	}


	public static int countingRecordsInTable(String TABLE_NAME) {
		try (Connection conn = DriverManager.getConnection(url, username, password);
				PreparedStatement pstmt = conn.prepareStatement(COUNT_RECORDS_TABLE);) {
			pstmt.setString(1, TABLE_NAME);
			try (ResultSet resultSet = pstmt.executeQuery()) {
				if (resultSet.next()) {
					int recordCount = resultSet.getInt("record_count");
					return recordCount;
				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore nel counting dei Trattamenti");
			e.printStackTrace();
		}
		return 0;
	}
	
	private void riempiCbs2(ArrayList<JComboBox> cbIdTABLES, ArrayList<String> tabelle, Map<JComboBox, Object> selectedValues) {
	    int i = 0;
	    for (JComboBox cb : cbIdTABLES) {
	        // Get the selected value for the current JComboBox
	        Object selectedValue = selectedValues.get(cb);

	        // Update the JComboBox values
	        riempiCbIdTABLE(cb, tabelle.get(i));

	        // Restore the selected value if it exists in the updated values
	        if (selectedValue != null) {
	            cb.setSelectedItem(selectedValue);
	        }

	        i++;
	    }
	}


}


/*
		Versione 3


		public Appuntamento() {
		setTitle("Appuntamenti del Centro Estetico BitCamp");
		setSize(600, 820);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		String[] idTrattamentoChoice = { "Selezionare il Trattamento" };
		String[] idClienteChoice = { "Selezionare il Cliente" };
		String[] idOperatoreChoice = { "Selezionare l'Operatore" };
		String[] idAppuntamentoChoice = { "Selezionare l'Appuntamento" };

		lblDescription = new JLabel("Operazioni Consetite sugli appuntamenti:");
		lblEmpty = new JLabel(
				"<html><br/>- Inserisci nuovo appuntamento<br/>- Cancela Appuntamento<br/>- Aggiorna Appuntamento<br/>- Visualizza gli appuntamenti </html>");

		lblData = new JLabel("Data appuntamento");
		txtData = new JTextField("YYYY-MM-DD (2023-06-20)");

		lblOra = new JLabel("Ora appuntamento");
		txtOra = new JTextField("HH:MM (23:09)");

		lblIdTrattamento = new JLabel("Id Trattamento");
		cbIdTrattamento = new JComboBox<String>(idTrattamentoChoice);

		lblIdCliente = new JLabel("Id Cliente");
		cbIdCliente = new JComboBox<String>(idClienteChoice);

		lblIdOperatore = new JLabel("Id Operatore");
		cbIdOperatore = new JComboBox<String>(idOperatoreChoice);

		lblIdAppuntamento = new JLabel("Id Appuntamento");
		cbIdAppuntamento = new JComboBox<String>(idAppuntamentoChoice);

		btnSave = new JButton("Save appuntamento");
		btnUpdate = new JButton("Update appuntamento");
		btnDelete = new JButton("Delete appuntamento");
		btnVisualize = new JButton("Visualize appuntamenti");

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		ArrayList<JComboBox> cbIdTABLES = new ArrayList<JComboBox>();
		ArrayList<String> tabelle = new ArrayList<String>();
		cbIdTABLES.addAll(Arrays.asList(cbIdTrattamento, cbIdCliente, cbIdOperatore, cbIdAppuntamento));
		tabelle.addAll(Arrays.asList("Trattamento", "Cliente", "Operatore", "Appuntamento"));
		riempiCbs(cbIdTABLES, tabelle);
		
		
		// Create a map to store the selected values for each JComboBox
		Map<JComboBox, Object> selectedValues = new HashMap<>();

		// Initialize the selected values map
		for (JComboBox cb : cbIdTABLES) {
		    selectedValues.put(cb, null);
		}

		// Add ActionListener for each JComboBox
		for (JComboBox cb : cbIdTABLES) {
		    cb.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            // Get the selected value
		            Object selectedItem = cb.getSelectedItem();

		            // Check if the selected value is different from the previously selected value
		            if (!Objects.equals(selectedItem, selectedValues.get(cb))) {
		                // Update the selected value in the map
		                selectedValues.put(cb, selectedItem);

		                // Update the JComboBox values
		                riempiCbs2(cbIdTABLES, tabelle, selectedValues);
		            }
		        }
		    });
		}
		

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!txtData.getText().isEmpty()) {
					if (!txtOra.getText().isEmpty()) {

							if(checkIdSelected(cbIdTABLES, tabelle)) {
				                // Valid value selected
								saveAppuntamento(txtData, txtOra, cbIdTABLES);
								clearAllFields(txtData, txtOra);
								riempiCbs2(cbIdTABLES, tabelle, selectedValues);
				            } else {
				                // Invalid value selected or default value selected
				            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected. ");
				            }						
					} else {
						JOptionPane.showMessageDialog(null, "Non è stata inserito un orario");
					}
				} else if (txtData.getText().isEmpty()) {
					if (txtOra.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Non sono stati inseriti la data e l'orario");
					} else
						JOptionPane.showMessageDialog(null, "Non è stata inserita una data");
				}
			}
		}

		);

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Object selectedItem = cbIdAppuntamento.getSelectedItem();

				if (!txtData.getText().isEmpty()) {
					if (!txtOra.getText().isEmpty()) {
						//if (selectedItem != null && selectedItem instanceof String) {
							//String selectedValue = (String) selectedItem;		            
							//String choicesAppuntamento = String.format(idTABLEChoice, "Appuntamento");
				            //if (!selectedValue.equals(choicesAppuntamento)) {
							if(checkIdSelected(cbIdTABLES, tabelle)) {
				                // Valid value selected
				                //System.out.println("Selected value is valid: " + selectedValue);				            	
								updateAppuntamento(txtData, txtOra, cbIdTABLES);
								clearAllFields(txtData, txtOra);
								riempiCbs2(cbIdTABLES, tabelle, selectedValues);
				            } else {
				                // Invalid value selected or default value selected
				            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected. ");
				            }

					} else {
						JOptionPane.showMessageDialog(null, "Non è stata inserita un orario");
					}
				} else if (txtData.getText().isEmpty()) {
					if (txtOra.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Non è stata inserita una data e un orario");
					} else
						JOptionPane.showMessageDialog(null, "Non è stata inserita una data");
				}
			}
		}

		);

		btnDelete.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e) {
				Object selectedItem = cbIdAppuntamento.getSelectedItem();
		        if (selectedItem != null && selectedItem instanceof String) {
		            String selectedValue = (String) selectedItem;		            
					String menu = String.format(idTABLEChoice, "Appuntamento");
		            if (!selectedValue.equals(menu)) {
		                // Valid value selected
		                //System.out.println("Selected value is valid: " + selectedValue);
		            	deleteAppuntamento(txtData, txtOra, cbIdAppuntamento);
		            	riempiCbs2(cbIdTABLES, tabelle, selectedValues);
		            } else {
		                // Invalid value selected or default value selected
		            	JOptionPane.showMessageDialog(null, "Invalid value or default value selected: " + selectedValue);
		            }
		        } else {
		            // No value selected
		        	JOptionPane.showMessageDialog(null, "No value is selected");
		        }
				
			}

		});

		btnVisualize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				visualizeAppuntamenti(txtData, txtOra, textArea);
				riempiCbs2(cbIdTABLES, tabelle, selectedValues);
			}
		});
		JPanel panel = new JPanel(new GridLayout(10, 2));
		panel.add(lblDescription);
		panel.add(lblEmpty);
		panel.add(lblData);
		panel.add(txtData);
		panel.add(lblOra);
		panel.add(txtOra);
		panel.add(lblIdTrattamento);
		panel.add(cbIdTrattamento);
		panel.add(lblIdCliente);
		panel.add(cbIdCliente);
		panel.add(lblIdOperatore);
		panel.add(cbIdOperatore);
		panel.add(lblIdAppuntamento);
		panel.add(cbIdAppuntamento);

		panel.add(btnSave);
		panel.add(btnUpdate);
		panel.add(btnDelete);
		panel.add(btnVisualize);

		panel.add(scrollPane, BorderLayout.CENTER);

		add(panel);
		setVisible(true);

	}

*/

/*
    VERSION 2 SU NOTEPAD++



/*
 * Version1
 * 
 *
 * package net.codejava;
 * 
 * import java.sql.*; import javax.swing.*; import java.awt.*; import
 * java.awt.event.ActionListener; import java.awt.event.ActionEvent;
 * 
 * public class Appuntamento extends JFrame{ private static final String url =
 * "jdbc:mysql://localhost:3306/dbCentroEstetico"; private static final String
 * username = "root"; private static final String password = "Va3489275169";
 * 
 * private static final String INSERT_APPUNTAMENTO =
 * "INSERT INTO appuntamento(data, ora, idTrattamento, idCliente, idOperatore) VALUES(?,?,?,?,?)"
 * ; private static final String VISUALIZE_APPUNTAMENTI =
 * "SELECT * FROM appuntamento"; private static final String DELETE_APPUNTAMENTO
 * = "DELETE FROM appuntamento WHERE id = ?"; private static final String
 * UPDATE_APPUNTAMENTO =
 * "UPDATE appuntamento SET data = ?, ora = ?, idTrattamento = ?, idCliente = ?, idOperatore = ? WHERE id = ?"
 * ;
 * 
 * private JTextField txtData, txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento; private JLabel lblData, lblOra,
 * lblIdTrattamento, lblIdCliente, lblIdOperatore, lblDescription, lblEmpty,
 * lblIdAppuntamento; private JTextArea textArea; private JButton btnSave,
 * btnUpdate, btnDelete, btnVisualize;
 * 
 * public Appuntamento() { setTitle("Appuntamenti del Centro Estetico BitCamp");
 * setSize(600,820); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * setLocationRelativeTo(null);
 * 
 * lblDescription = new JLabel("Operazioni Consetite sugli appuntamenti:");
 * lblEmpty = new
 * JLabel("<html><br/>- Inserisci nuovo appuntamento<br/>- Cancela Appuntamento<br/>- Aggiorna Appuntamento<br/>- Visualizza gli appuntamenti </html>"
 * );
 * 
 * lblData = new JLabel("Data appuntamento"); txtData = new
 * JTextField("YYYY-MM-DD (2023-06-20)");
 * 
 * lblOra = new JLabel("Ora appuntamento"); txtOra = new
 * JTextField("HH:MM (23:09)");
 * 
 * lblIdTrattamento = new JLabel("Id Trattamento"); txtIdTrattamento = new
 * JTextField();
 * 
 * lblIdCliente = new JLabel("Id Cliente"); txtIdCliente = new JTextField();
 * 
 * lblIdOperatore = new JLabel("Id Operatore"); txtIdOperatore = new
 * JTextField();
 * 
 * lblIdAppuntamento = new JLabel("Id Appuntamento"); txtIdAppuntamento = new
 * JTextField();
 * 
 * btnSave = new JButton("Save appuntamento"); btnUpdate = new
 * JButton("Update appuntamento"); btnDelete = new
 * JButton("Delete appuntamento"); btnVisualize = new
 * JButton("Visualize appuntamenti");
 * 
 * textArea = new JTextArea(); JScrollPane scrollPane = new
 * JScrollPane(textArea);
 * 
 * btnSave.addActionListener(new ActionListener() { public void
 * actionPerformed(ActionEvent e) { if(!txtData.getText().isEmpty()) {
 * if(!txtOra.getText().isEmpty()) { saveAppuntamento(txtData,txtOra,
 * txtIdTrattamento, txtIdCliente, txtIdOperatore, txtIdAppuntamento, textArea);
 * }else { JOptionPane.showMessageDialog(null,
 * "Non è stata inserita un orario"); } }else if(txtData.getText().isEmpty()){
 * if(txtOra.getText().isEmpty()){ JOptionPane.showMessageDialog(null,
 * "Non è stata inserita una data e un orario"); }else
 * JOptionPane.showMessageDialog(null, "Non è stata inserita una data"); } } }
 * 
 * );
 * 
 * btnUpdate.addActionListener(new ActionListener() { public void
 * actionPerformed(ActionEvent e) {
 * 
 * 
 * if(!txtData.getText().isEmpty()) { if(!txtOra.getText().isEmpty()) {
 * if(!txtIdAppuntamento.getText().isEmpty()) {
 * updateAppuntamento(txtData,txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento, textArea); }else {
 * JOptionPane.showMessageDialog(null,
 * "Non è stata inserito l'id dell'appuntamento"); } }else {
 * JOptionPane.showMessageDialog(null, "Non è stata inserita un orario"); }
 * }else if(txtData.getText().isEmpty()){ if(txtOra.getText().isEmpty()){
 * JOptionPane.showMessageDialog(null,
 * "Non è stata inserita una data e un orario"); }else
 * JOptionPane.showMessageDialog(null, "Non è stata inserita una data"); } } }
 * 
 * );
 * 
 * btnDelete.addActionListener(new ActionListener() { public void
 * actionPerformed(ActionEvent e) { if(!txtIdAppuntamento.getText().isEmpty()) {
 * deleteAppuntamento(txtData,txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento, textArea); }else {
 * JOptionPane.showMessageDialog(null,
 * "Non è stata inserito l'id dell'appuntamento"); } }
 * 
 * } );
 * 
 * btnVisualize.addActionListener(new ActionListener() { public void
 * actionPerformed(ActionEvent e) { visualizeAppuntamenti(txtData, txtOra,
 * txtIdTrattamento, txtIdCliente, txtIdOperatore, txtIdAppuntamento, textArea);
 * } }); JPanel panel = new JPanel(new GridLayout(10,2));
 * panel.add(lblDescription); panel.add(lblEmpty); panel.add(lblData);
 * panel.add(txtData); panel.add(lblOra); panel.add(txtOra);
 * panel.add(lblIdTrattamento); panel.add(txtIdTrattamento);
 * panel.add(lblIdCliente); panel.add(txtIdCliente); panel.add(lblIdOperatore);
 * panel.add(txtIdOperatore); panel.add(lblIdAppuntamento);
 * panel.add(txtIdAppuntamento);
 * 
 * panel.add(btnSave); panel.add(btnUpdate); panel.add(btnDelete);
 * panel.add(btnVisualize);
 * 
 * panel.add(scrollPane, BorderLayout.CENTER);
 * 
 * add(panel); setVisible(true);
 * 
 * }
 * 
 * 
 * 
 * private static void saveAppuntamento(JTextField txtData, JTextField txtOra,
 * JTextField txtIdTrattamento, JTextField txtIdCliente, JTextField
 * txtIdOperatore, JTextField txtIdAppuntamento, JTextArea textArea) { try
 * (Connection conn = DriverManager.getConnection(url,username, password);
 * PreparedStatement pstmt = conn.prepareStatement(INSERT_APPUNTAMENTO)){
 * pstmt.setString(1, txtData.getText()); pstmt.setString(2, txtData.getText() +
 * " " + txtOra.getText()); pstmt.setString(3, txtIdTrattamento.getText());
 * pstmt.setString(4, txtIdCliente.getText()); pstmt.setString(5,
 * txtIdOperatore.getText());
 * 
 * int recordInseriti = pstmt.executeUpdate();
 * 
 * if(recordInseriti > 0) { JOptionPane.showMessageDialog(null,
 * "E' stato inserito corretamente un nuovo appuntamento"); }else {
 * JOptionPane.showMessageDialog(null,
 * "Non è stato possibile inserire il nuovo appuntamento"); }
 * 
 * }catch(SQLException e) { e.printStackTrace();
 * JOptionPane.showMessageDialog(null,
 * "Errore durante la connessione al database"); }finally {
 * clearAllFields(txtData,txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento, textArea); } }
 * 
 * private static void updateAppuntamento(JTextField txtData, JTextField txtOra,
 * JTextField txtIdTrattamento, JTextField txtIdCliente, JTextField
 * txtIdOperatore, JTextField txtIdAppuntamento, JTextArea textArea) { try
 * (Connection conn = DriverManager.getConnection(url,username, password);
 * PreparedStatement pstmt = conn.prepareStatement(UPDATE_APPUNTAMENTO)){
 * pstmt.setString(1, txtData.getText()); pstmt.setString(2, txtData.getText() +
 * " " + txtOra.getText()); pstmt.setString(3, txtIdTrattamento.getText());
 * pstmt.setString(4, txtIdCliente.getText()); pstmt.setString(5,
 * txtIdOperatore.getText()); pstmt.setString(6, txtIdAppuntamento.getText());
 * 
 * int recordAggiornati = pstmt.executeUpdate();
 * 
 * if(recordAggiornati > 0) { JOptionPane.showMessageDialog(null,
 * "E' stato aggiornato corretamente l'appuntamento con ID: " +
 * txtIdAppuntamento.getText()); }else { JOptionPane.showMessageDialog(null,
 * "Non è stato possibile aggiornare l'appuntamento con ID: " +
 * txtIdAppuntamento.getText()); }
 * 
 * }catch(SQLException e) { e.printStackTrace();
 * JOptionPane.showMessageDialog(null,
 * "Errore durante la connessione al database"); }finally {
 * clearAllFields(txtData,txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento, textArea); } }
 * 
 * 
 * private static void deleteAppuntamento(JTextField txtData, JTextField txtOra,
 * JTextField txtIdTrattamento, JTextField txtIdCliente, JTextField
 * txtIdOperatore, JTextField txtIdAppuntamento, JTextArea textArea) { try
 * (Connection conn = DriverManager.getConnection(url, username, password);
 * PreparedStatement pstmt = conn.prepareStatement(DELETE_APPUNTAMENTO)){
 * pstmt.setString(1, txtIdAppuntamento.getText());
 * 
 * int recordAggiornati = pstmt.executeUpdate();
 * 
 * if(recordAggiornati > 0) { JOptionPane.showMessageDialog(null,
 * "E' stato corretamente eliminato l'appuntamento con ID: " +
 * txtIdAppuntamento.getText()); }else { JOptionPane.showMessageDialog(null,
 * "Non è stato possibile eliminare l'appuntamento con ID: " +
 * txtIdAppuntamento.getText()); }
 * 
 * }catch(SQLException e) { e.printStackTrace();
 * JOptionPane.showMessageDialog(null,
 * "Errore durante la connessione al database"); }finally {
 * clearAllFields(txtData, txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento, textArea); } }
 * 
 * private static void visualizeAppuntamenti(JTextField txtData, JTextField
 * txtOra, JTextField txtIdTrattamento, JTextField txtIdCliente, JTextField
 * txtIdOperatore, JTextField txtIdAppuntamento, JTextArea textArea) {
 * try(Connection conn = DriverManager.getConnection(url, username, password);
 * Statement stmt = conn.createStatement(); ResultSet rs =
 * stmt.executeQuery(VISUALIZE_APPUNTAMENTI)){ textArea.setText(""); int i = 0;
 * while(rs.next()) { i++; String record = i + ") Data: " + rs.getString(3) +
 * ", ID Appuntamento: " + rs.getInt(1) + "\n" + "ID Trattamento: " +
 * rs.getInt(4) + ", ID Cliente: " + rs.getString(5) + ", ID Operatore: " +
 * rs.getString(6) +"\n\n"; textArea.append(record); } }catch(SQLException e) {
 * JOptionPane.showMessageDialog(null,
 * "Errore durante la connessione al database"); e.printStackTrace(); }finally {
 * clearAllFields(txtData, txtOra, txtIdTrattamento, txtIdCliente,
 * txtIdOperatore, txtIdAppuntamento, textArea); } }
 * 
 * 
 * 
 * private static void clearAllFields(JTextField txtData, JTextField txtOra,
 * JTextField txtIdTrattamento, JTextField txtIdCliente, JTextField
 * txtIdOperatore, JTextField txtIdAppuntamento, JTextArea txtArea) {
 * txtData.setText(""); txtOra.setText(""); txtIdTrattamento.setText("");
 * txtIdCliente.setText(""); txtIdOperatore.setText("");
 * txtIdAppuntamento.setText(""); }
 * 
 * public static void main(String[] args) { // TODO Auto-generated method stub
 * new Appuntamento();
 * 
 * }
 * 
 * }
 * 
 * 
 * 
 */