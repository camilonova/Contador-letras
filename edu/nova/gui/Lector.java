package edu.nova.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Clase que evalua un texto ingresado en la area de texto
 * @author Camilo Nova
 * @version 1.0
 */
public class Lector extends JApplet {

	/**
	 * Crea la interfaz grafica para mostrar el area de texto donde
	 * se ingresa el texto a evaluar.
	 * <p>Creation date 6/04/2006 - 11:07:10 AM
	 *
	 * @since 1.0
	 */
	public Lector() {
		JPanel upPanel = new JPanel();
		JPanel lowPanel = new JPanel();
		final JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		JButton okButton = new JButton("Evaluar");
		JButton cancelButton = new JButton("Cancelar");

		scrollPane.setPreferredSize(new Dimension(570, 280));

		upPanel.setBorder(BorderFactory.createTitledBorder("Texto a evaluar"));
		scrollPane.setBorder(BorderFactory.createEtchedBorder());

		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new TextEvaluator(textArea.getText());
				System.exit(0);
			}

		});
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		upPanel.add(scrollPane);
		lowPanel.add(okButton);
		lowPanel.add(cancelButton);

		add(upPanel, BorderLayout.NORTH);
		add(lowPanel, BorderLayout.SOUTH);

		setSize(600, 400);
		setVisible(true);
	}

	/**
	 * Clase que provee los metodos para evaluar el texto.
	 * @author Camilo Nova
	 * @version 1.0
	 */
	public class TextEvaluator {
		// El caracter 'a' equivale a 97 en entero

		// Arreglo que contiene la cantidad de letras de 0 a 25
		int[] letterCounter = new int[26];

		// Arreglo que contiene la probabilidad de aparicion de cada letra
		float[] probabilityCounter = new float[26];

		// Contador de los caracteres diferentes a las letras
		int otherCounter;

		// Total de letras (efectivas) en el texto
		int totalLetters;

		/**
		 * Cuenta la ocurrencia de cada letra, calcula su probabilidad
		 * de ocurrencia y ordena los datos de mayor a menor.
		 * <p>Creation date 6/04/2006 - 11:04:42 AM
		 * @param text		Texto a evaluar
		 *
		 * @since 1.0
		 */
		public TextEvaluator(String text) {
			// Contamos la ocurrencias de cada letra
			for (int i = 0; i < text.length(); i++) {
				// Tomamos 'A' y 'a' como una misma letra.
				char letter = Character.toLowerCase(text.charAt(i));
				if ('a' <= letter && letter <= 'z') {
					letterCounter[letter - 97]++;
					totalLetters++;
				} else
					otherCounter++;
			}

			// Creamos un arreglo con el alfabeto
			char[] alphabeth = new char[26];
			for (char c = 'a'; c <= 'z'; c++)
				alphabeth[c - 97] = c;

			// Ordenamos los datos
			BubbleSort(letterCounter, alphabeth);

			// Calculamos la probabilidad de ocurrencia de cada letra
			for (int i = 0; i < letterCounter.length; i++) {
				probabilityCounter[i] = (float) letterCounter[i] / totalLetters;
			}

			// Mostramos los resultados de las ocurrencias
			for (int i = 0; i < 26; i++) {
				if (letterCounter[i] > 0) {
					System.out.println("La letra " + alphabeth[i] + " tiene "
							+ letterCounter[i] + " ocurrencias. "
							+ "Con probabilidad " + probabilityCounter[i]);
				}
			}

			System.out.println("\nCaracteres letras " + totalLetters);
			System.out.println("Caracteres diferentes " + otherCounter);
			System.out.println("Caracteres totales "
					+ (totalLetters + otherCounter) + " de " + text.length());
			System.out.println("\nDone");

			//Mostramos el resumen
			String cad = "Las seis letras mas repetidas son:\n";
			for (int i = 0; i < 6; i++) {
				cad += "La letra " + alphabeth[i] + " tiene "
						+ letterCounter[i] + " ocurrencias. "
						+ "Probabilidad de " + probabilityCounter[i] + "\n";
			}
			JOptionPane.showMessageDialog(Lector.this, cad);

		}

		/**
		 * Ordenamiento por Burbuja de los datos de mayor a menor.
		 * Coorelacionando ambos parametros
		 * <p>Creation date 6/04/2006 - 10:44:02 AM
		 *
		 * @param data		Array de datos a ordenar
		 * @param alphabeth Alfabeto para correlacionar los datos ordenados
		 * @since 1.0
		 */
		public void BubbleSort(int[] data, char[] alphabeth) {
			for (int i = 0; i < data.length; i++)
				for (int k = 0; k < data.length - 1; k++)
					if (data[k] < data[k + 1]) {
						int a = data[k];
						data[k] = data[k + 1];
						data[k + 1] = a;

						// Coorelacionamos
						char c = alphabeth[k];
						alphabeth[k] = alphabeth[k + 1];
						alphabeth[k + 1] = c;
					}
		}
	}
}
