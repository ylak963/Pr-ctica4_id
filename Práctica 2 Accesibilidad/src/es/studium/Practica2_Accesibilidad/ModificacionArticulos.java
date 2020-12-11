package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class ModificacionArticulos extends JFrame 
{
	public static int idArticuloModificar;
	//int idArticuloModificar=0;
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
				try {
					ModificacionArticulos frame = new ModificacionArticulos();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModificacionArticulos() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnModificacinDeArticulos = new JMenu("Modificaci\u00F3n de articulos");
		menuBar.add(mnModificacinDeArticulos);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Articulos articulo = new Articulos();
				articulo.setLocationRelativeTo(null);
				articulo.setVisible(true);
			}
		});
		btnVolver.setBounds(248, 190, 89, 23);
		contentPane.add(btnVolver);

		JLabel lblArticulo = new JLabel("Articulo:");
		lblArticulo.setBounds(75, 40, 70, 14);
		contentPane.add(lblArticulo);

		Choice choice = new Choice();
		choice.setBounds(231, 40, 150, 20);
		contentPane.add(choice);

		Connection con = conectar();

		
		String sentencia = "SELECT * FROM Articulos";

		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sentencia);
			while (rs.next()) 
			{
				choice.add(rs.getInt("idArticulo")+
						"-"+rs.getInt("cantidadArticulo")+
						", "+rs.getDouble("precioArticulo")+
						", "+rs.getString("descripcionArticulo"));
			}
			rs.close();
			st.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
					// El idArticulo que quiero editar está en tabla[0]
					idArticuloModificar = Integer.parseInt(choice.getSelectedItem().split("-")[0]);
					new Modificacion2Articulos(); 
			
			}
		});
		btnAceptar.setBounds(88, 190, 89, 23);
		contentPane.add(btnAceptar);


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
