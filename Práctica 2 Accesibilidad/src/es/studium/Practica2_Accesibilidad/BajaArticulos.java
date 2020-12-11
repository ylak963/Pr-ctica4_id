package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

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
import java.awt.event.ActionEvent;
import java.awt.Choice;

public class BajaArticulos extends JFrame 
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
				try {
					BajaArticulos frame = new BajaArticulos();
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
	public BajaArticulos() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnBajaArticulo = new JMenu("Baja articulos");
		menuBar.add(mnBajaArticulo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Choice choiceArticulo = new Choice();
		choiceArticulo.setBounds(205, 77, 190, 20);
		contentPane.add(choiceArticulo);

		choiceArticulo.add("seleccion de artículos");
		// Conectar a la base de datos
		Connection conexion = conectar();

		// Sacar los datos de la tabla articulos
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM articulos";
		try 
		{
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = (Statement) conexion.createStatement();
			//Para sentencia SELECT
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choiceArticulo.add("Id:"+ rs.getInt("idArticulo")+"-"+rs.getString("descripcionArticulo"));
			}
			rs.close();
			stmt.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR: En la baja");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		//desconexion(conexion);
		
		JButton btnbaja = new JButton("baja");
		btnbaja.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Choice choiceArticulo = new Choice();
				// Conectar a BD
				Connection conexion = conectar(); 
				// Borrar
				String[] Articulo=choiceArticulo.getSelectedItem().split("-");//Revisar
				int respuesta = baja(conexion, Integer.parseInt(Articulo[0]));
				// Mostramos resultado
				if(respuesta == 0)
				{					
					System.out.println("Borrado del articulo correcto");
				}
				else
				{					
					System.out.println("Error en borrado del articulo");
				}
				
				// Actualizar el Choice
				choiceArticulo.removeAll();
				choiceArticulo.add("Seleccionar uno...");
				// Desconectar
				desconexion(conexion);
				
			}	
			});
		btnbaja.setBounds(39, 187, 89, 23);
		contentPane.add(btnbaja);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{

			}
		});
		btnLimpiar.setBounds(179, 187, 89, 23);
		contentPane.add(btnLimpiar);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		});
		btnVolver.setBounds(306, 187, 89, 23);
		contentPane.add(btnVolver);

		JLabel lblArticulo = new JLabel("Articulo");
		lblArticulo.setBounds(67, 83, 93, 14);
		contentPane.add(lblArticulo);

		
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
	public int baja(Connection conexion, int idArticulo)
	{
		int respuesta = 0;
		String sql = "DELETE FROM articulos WHERE idArticulo = " + idArticulo;
		System.out.println(sql);
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = (Statement) conexion.createStatement();
			sta.executeUpdate(sql);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
			//Dialog set.Visible(true);
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
