package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Articulos extends JFrame 
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Articulos frame = new Articulos();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Articulos() {
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArtculos = new JMenu("Art\u00EDculos");
		menuBar.add(mnArtculos);
		
		JMenuItem mntmAlta = new JMenuItem("Alta");
		mntmAlta.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				AltaArticulos altaArt = new AltaArticulos();
				altaArt.setLocationRelativeTo(null);
				altaArt.setVisible(true);
			}
		});
		mnArtculos.add(mntmAlta);
		
		JMenuItem mntmBaja = new JMenuItem("Baja");
		mntmBaja.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				BajaArticulos bajaArt = new BajaArticulos();
				bajaArt.setLocationRelativeTo(null);
				bajaArt.setVisible(true);
			}
		});
		mnArtculos.add(mntmBaja);
		
		JMenuItem mntmConsulta = new JMenuItem("Consulta");
		mntmConsulta.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ConsultaArticulos consultaArt = new ConsultaArticulos();
				consultaArt.setLocationRelativeTo(null);
				consultaArt.setVisible(true);
			}
		});
		mnArtculos.add(mntmConsulta);
		
		JMenuItem mntmModificacin = new JMenuItem("Modificaci\u00F3n");
		mntmModificacin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				ModificacionArticulos modifArt = new ModificacionArticulos();
				modifArt.setLocationRelativeTo(null);
				modifArt.setVisible(true);						
			}
		});
		mnArtculos.add(mntmModificacin);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
