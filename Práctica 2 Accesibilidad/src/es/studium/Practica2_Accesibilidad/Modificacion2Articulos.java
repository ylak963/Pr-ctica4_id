package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class Modificacion2Articulos extends JFrame
{
	static int idArticuloModificar;
	private JPanel contentPane;
	private JTextField txtCantidad;
	private JTextField txtPrecio;
	private JTextField txtDescrip;

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
					Modificacion2Articulos frame = new Modificacion2Articulos();
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
	public Modificacion2Articulos()
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cantidad:");
		lblNewLabel.setBounds(38, 58, 67, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(38, 105, 67, 14);
		contentPane.add(lblPrecio);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(38, 153, 67, 14);
		contentPane.add(lblDescripcion);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(152, 55, 184, 20);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(152, 102, 184, 20);
		contentPane.add(txtPrecio);
		
		txtDescrip = new JTextField();
		txtDescrip.setColumns(10);
		txtDescrip.setBounds(152, 150, 184, 20);
		contentPane.add(txtDescrip);
		
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		btnModificar.setBounds(61, 205, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
			}
		});
		btnLimpiar.setBounds(178, 205, 89, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			}
		});
		btnNewButton.setBounds(298, 205, 89, 23);
		contentPane.add(btnNewButton);
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

	public void rellenar(Connection conexion, int idArticulo, JTextField txtCantidad , JTextField txtPrecio, JTextField txtDescrip)
	{
		String sentencia = "SELECT * FROM Articulos WHERE idArticulo = "+ idArticulo;
		try 
		{
			// Creamos un statement para una consulta.
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery(sentencia);
			while(rs.next())
			{
				Integer cant = rs.getInt("cantidadArticulo");
				this.txtCantidad.setText(cant.toString());
				Double prec = rs.getDouble("precioArticulo");
				this.txtPrecio.setText(prec.toString());
				String descrip = rs.getString("descripcionArt");
				txtDescrip.setText(descrip);
			}
			st.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer SELECT");
			ex.printStackTrace();
		}
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
