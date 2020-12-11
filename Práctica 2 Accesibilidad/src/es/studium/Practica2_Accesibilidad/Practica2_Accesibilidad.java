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

public class Practica2_Accesibilidad extends JFrame 
{

	private static final long serialVersionUID = 1L;
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
					Practica2_Accesibilidad frame = new Practica2_Accesibilidad();
					frame.setLocationRelativeTo(null);
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
	public Practica2_Accesibilidad() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Menu principal");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmArticulos = new JMenuItem("Articulos");
		mntmArticulos.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Articulos articulo = new Articulos();
				articulo.setLocationRelativeTo(null);
				articulo.setVisible(true);
			}
		});
		mnNewMenu.add(mntmArticulos); 
			
		JMenuItem mntmTickets = new JMenuItem("Tickets");
		mntmTickets.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Tickets tickets = new Tickets();
				tickets.setLocationRelativeTo(null);
				tickets.setVisible(true);
				
			}
		});
		mnNewMenu.add(mntmTickets);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
