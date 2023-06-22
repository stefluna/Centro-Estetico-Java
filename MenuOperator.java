package net.codejava;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;

public class MenuOperator extends JFrame {
	private static final String url = "jdbc:mysql://localhost:3306/dbCentroEstetico";
	private static final String username = "root";
	private static final String password = "#";

	private static final String RUOLO = "SELECT ruolo FROM utente WHERE username = %s AND password = %s";

	private JLabel lblSelezionaOperazione, lblDescription, lblEmpty;
	private JComboBox<String> cbOperazioneSelezionata;
	private JButton btnConfirmTheChoice;

	public MenuOperator() {
		setTitle("Menu Operatore");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		String[] administratorChoice = { "1) Operazioni sui Clienti", "2) Operazioni sui Appuntamenti", "3) Operazioni sui Trattamenti"};

		lblDescription = new JLabel("Seleziona l'operazione che si vuole eseguire:");
		lblEmpty = new JLabel("<html></html>");

		lblSelezionaOperazione = new JLabel("Scegli l'operazione");
		cbOperazioneSelezionata = new JComboBox<String>(administratorChoice);

		btnConfirmTheChoice = new JButton("Prosegui con l'operazione");

		// Create a map to store the selected values for each JComboBox
		Map<JComboBox, Object> selectedValues = new HashMap<>();

		cbOperazioneSelezionata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the selected value
				Object selectedItem = cbOperazioneSelezionata.getSelectedItem();

				// Check if the selected value is different from the previously selected value
				if (!Objects.equals(selectedItem, selectedValues.get(cbOperazioneSelezionata))) {
					// Update the selected value in the map
					selectedValues.put(cbOperazioneSelezionata, selectedItem);
				}
			}
		});

		btnConfirmTheChoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rs = checkOperationSelected(cbOperazioneSelezionata, administratorChoice);
					switch(rs) {
					case 1: 
						JOptionPane.showMessageDialog(null, "Benvenuto in Operazioni sui Clienti ");
						dispose(); // Close the Main JFrame
						new Cliente(3);			
						break;
					case 2: 
						JOptionPane.showMessageDialog(null, "Benvenuto in Operazioni sui Appuntamenti ");
						dispose(); // Close the Main JFrame
						new Appuntamento(3);			
						break;
					case 3: 
						JOptionPane.showMessageDialog(null, "Benvenuto in Operazioni sui Trattamenti  ");
						dispose(); // Close the Main JFrame
						new Trattamento(3);							
						break;
					case 0:
						// Invalid value selected or default value selected
		            	JOptionPane.showMessageDialog(null, "Invalid choice");
						break;
					default:
						JOptionPane.showMessageDialog(null, "Errore nella selezione dell'operazione da eseguire.");
						break;
					}				

			}
		}

		);
		
		JPanel panel = new JPanel(new GridLayout(4, 2));
		panel.add(lblDescription);
		panel.add(lblEmpty);
		panel.add(lblSelezionaOperazione);
		panel.add(cbOperazioneSelezionata);
		
		panel.add(btnConfirmTheChoice);

		add(panel);
		setVisible(true);

	}

	private static int checkOperationSelected(JComboBox cbOperazione, String[] opzioni) {
		int i = 0;
		

			Object selectedItem = cbOperazione.getSelectedItem();
	        if (selectedItem != null && selectedItem instanceof String) {
	        	
	        	String selectedValue = (String) selectedItem;
	    		for(String opzione: opzioni) {
	    			i++;
		            if (selectedValue.equals(opzione)) {
		                // Valid value selected
		                return i;
		            }	 
		            
	    		}
	    		return 0;
	        } else {
	            // No value selected
	        	JOptionPane.showMessageDialog(null, "No value is selected");
	        	return 0;
	        }
			
		}
	
	
	public static void main(String[] args) {
		new MenuOperator();
	}

}

