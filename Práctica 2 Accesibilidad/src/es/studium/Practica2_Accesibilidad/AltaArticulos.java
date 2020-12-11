package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AltaArticulos extends JFrame 
{	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textCantidad;
	private JTextField textPrecio;
	private JTextField textDescrip;

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
					AltaArticulos frame = new AltaArticulos();
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
	public AltaArticulos() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAltaDeArticulos = new JMenu("Alta articulos");
		menuBar.add(mnAltaDeArticulos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(27, 38, 79, 14);
		contentPane.add(lblCantidad);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(27, 76, 46, 14);
		contentPane.add(lblPrecio);
		
		JLabel lblDescrip = new JLabel("Descripci\u00F3n:");
		lblDescrip.setBounds(27, 116, 79, 14);
		contentPane.add(lblDescrip);
		
		
		textCantidad = new JTextField();
		textCantidad.setBounds(171, 35, 222, 20);
		contentPane.add(textCantidad);
		textCantidad.setColumns(10);
		
		textPrecio = new JTextField();
		textPrecio.setBounds(171, 73, 222, 20);
		contentPane.add(textPrecio);
		textPrecio.setColumns(10);
		
		textDescrip = new JTextField();
		textDescrip.setBounds(171, 113, 222, 20);
		contentPane.add(textDescrip);
		textDescrip.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				// Conectar BD
				Connection conexion = conectar();
				//Proceder el INSERT

				int respuesta = insertar(conexion, "articulos", textCantidad.getText(),textPrecio.getText(),textDescrip.getText());
				
				// Mostramos resultado
				if(respuesta == 0)
				{
					System.out.println("ALTA de artículo correcta");
					textCantidad.selectAll();
					textCantidad.setText("");
					textCantidad.requestFocus();

					textPrecio.selectAll();
					textPrecio.setText("");


					textCantidad.selectAll();
					textCantidad.setText("");
				}
				else
				{
					System.out.println("Error en Alta de artículo");//
				}
				// Desconectar de la base
				desconexion(conexion);
			}
		});
		btnGuardar.setBounds(56, 185, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				textCantidad.selectAll();
				textCantidad.setText("");
				textCantidad.requestFocus();

				textPrecio.selectAll();
				textPrecio.setText("");


				textDescrip.selectAll();
				textDescrip.setText("");
			}
		});
		btnLimpiar.setBounds(185, 185, 89, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		btnVolver.setBounds(304, 185, 89, 23);
		contentPane.add(btnVolver);
			
	}
	
	public static Connection conectar() 
	{
		String driver ="com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tienda?useSSL=false&allowPublicKeyRetrieval=true";
		String user = "root";
		String password ="1234";
		Connection conexion = null;

		try
		{
			//Nos aseguramos que arranca el driver
			Class.forName(driver);
			//Establecemos la conexión con la base de datos hotel
			conexion=DriverManager.getConnection(url,user,password);
			if(conexion !=null)
			{
				System.out.println("Conectado a la base de datos");
			}
		}
	
		catch(SQLException ex)
		{
			System.out.println("Error en la conexión");
			ex.printStackTrace();
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("Error"+ cnfe.getMessage());
		}
		return conexion;
	}
	
	public int insertar(Connection conexion, String articulos, String cantidadArticulo, 
			String precioArticulo, String descripcionArticulo) 
	{		
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = conexion.createStatement();
			String cadenaSQL = "INSERT INTO " + articulos+" VALUES (null, '"+cantidadArticulo+"' ,'"+precioArticulo+"', '"+descripcionArticulo +"');";

			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
		
	}
	
	public static void desconexion(Connection conexion)
	{
		try
		{
			conexion.close();
			System.out.println("Desconexión de la base datos");
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}
