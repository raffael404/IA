package br.ufpi.visao;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JTextArea;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import br.ufpi.controle.buscaAEstrela;
import br.ufpi.controle.buscaEmLargura;
import br.ufpi.controle.buscaEmProfundidade;
import br.ufpi.controle.buscaGulosa;
import br.ufpi.modelo.Estado;
import br.ufpi.modelo.Margem;
import javax.swing.SwingConstants;


public class Tela {
	
	JTextArea textArea;
	buscaEmLargura largura;
	buscaEmProfundidade profundidade;
	buscaGulosa gulosa;
	buscaAEstrela a;

	private JFrame frmProblemaDosMissionrios;
	private JTextField textFieldMissionarios;
	private JTextField textFieldCanibais;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela window = new Tela();
					window.frmProblemaDosMissionrios.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tela() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProblemaDosMissionrios = new JFrame();
		frmProblemaDosMissionrios.setResizable(false);
		frmProblemaDosMissionrios.setTitle("Problema dos Mission\u00E1rios e Canibais");
		frmProblemaDosMissionrios.setBounds(100, 100, 600, 420);
		frmProblemaDosMissionrios.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProblemaDosMissionrios.getContentPane().setLayout(null);
		
		textFieldMissionarios = new JTextField();
		textFieldMissionarios.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldMissionarios.setBounds(99, 330, 28, 29);
		frmProblemaDosMissionrios.getContentPane().add(textFieldMissionarios);
		textFieldMissionarios.setColumns(10);
		
		textFieldCanibais = new JTextField();
		textFieldCanibais.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldCanibais.setColumns(10);
		textFieldCanibais.setBounds(211, 330, 28, 29);
		frmProblemaDosMissionrios.getContentPane().add(textFieldCanibais);
		
		JLabel lblMissionrios = new JLabel("Mission\u00E1rios:");
		lblMissionrios.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblMissionrios.setBounds(10, 335, 79, 14);
		frmProblemaDosMissionrios.getContentPane().add(lblMissionrios);
		
		JLabel lblCanibais = new JLabel("Canibais:");
		lblCanibais.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCanibais.setBounds(144, 335, 60, 14);
		frmProblemaDosMissionrios.getContentPane().add(lblCanibais);
		
		JButton btnBuscaEmLargura = new JButton("Busca em Largura");
		btnBuscaEmLargura.setBounds(260, 321, 183, 23);
		frmProblemaDosMissionrios.getContentPane().add(btnBuscaEmLargura);
		btnBuscaEmLargura.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int missionarios = Integer.parseInt(textFieldMissionarios.getText());
				int canibais = Integer.parseInt(textFieldCanibais.getText());
				Estado estadoInicial = new Estado(new Margem(missionarios, canibais), new Margem(0, 0)).mover(0, 0, 'E', -1);
				largura = new buscaEmLargura();
				double tempo = System.currentTimeMillis();
				StringBuffer log = largura.iniciar(estadoInicial);
				tempo = System.currentTimeMillis() - tempo;
				log.append("\nTempo de execução: " + tempo + "ms");
				textArea.setText(textArea.getText() + log.toString());
			}
		});
		
		JButton btnBuscaEmProfundidade = new JButton("Busca em Profundidade");
		btnBuscaEmProfundidade.setBounds(260, 347, 183, 23);
		frmProblemaDosMissionrios.getContentPane().add(btnBuscaEmProfundidade);
		
		btnBuscaEmProfundidade.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int missionarios = Integer.parseInt(textFieldMissionarios.getText());
				int canibais = Integer.parseInt(textFieldCanibais.getText());
				Estado estadoInicial = new Estado(new Margem(missionarios, canibais), new Margem(0, 0)).mover(0, 0, 'E', -1);
				profundidade = new buscaEmProfundidade();
				double tempo = System.currentTimeMillis();
				StringBuffer log = profundidade.iniciar(estadoInicial);
				tempo = System.currentTimeMillis() - tempo;
				log.append("\nTempo de execução: " + tempo + "ms");
				textArea.setText(textArea.getText() + log.toString());
			}
		});
		
		JButton btnBuscaGulosa = new JButton("Busca Gulosa");
		btnBuscaGulosa.setBounds(453, 321, 121, 23);
		frmProblemaDosMissionrios.getContentPane().add(btnBuscaGulosa);
		
		btnBuscaGulosa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int missionarios = Integer.parseInt(textFieldMissionarios.getText());
				int canibais = Integer.parseInt(textFieldCanibais.getText());
				Estado estadoInicial = new Estado(new Margem(missionarios, canibais), new Margem(0, 0)).mover(0, 0, 'E', -1);
				gulosa = new buscaGulosa(textArea);
				double tempo = System.currentTimeMillis();
				StringBuffer log = gulosa.iniciar(estadoInicial);
				tempo = System.currentTimeMillis() - tempo;
				log.append("\nTempo de execução: " + tempo + "ms");
				textArea.setText(textArea.getText() + log.toString());
			}
		});
		
		JButton btnA = new JButton("A*");
		btnA.setBounds(453, 347, 121, 23);
		frmProblemaDosMissionrios.getContentPane().add(btnA);
		
		btnA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				int missionarios = Integer.parseInt(textFieldMissionarios.getText());
				int canibais = Integer.parseInt(textFieldCanibais.getText());
				Estado estadoInicial = new Estado(new Margem(missionarios, canibais), new Margem(0, 0)).mover(0, 0, 'E', -1);
				a = new buscaAEstrela();
				double tempo = System.currentTimeMillis();
				StringBuffer log = a.iniciar(estadoInicial);
				tempo = System.currentTimeMillis() - tempo;
				log.append("\nTempo de execução: " + tempo + "ms");
				textArea.setText(textArea.getText() + log.toString());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 564, 300);
		frmProblemaDosMissionrios.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
	}
}
