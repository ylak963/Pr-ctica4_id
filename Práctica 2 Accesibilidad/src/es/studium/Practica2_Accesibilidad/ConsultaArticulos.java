package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ConsultaArticulos extends JFrame 
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
					ConsultaArticulos frame = new ConsultaArticulos();
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
	public ConsultaArticulos() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtArea = new JTextArea();
		txtArea.setBounds(52, 29, 333, 170);
		contentPane.add(txtArea);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(52, 210, 89, 23);
		contentPane.add(btnImprimir);
		
		JButton btnExportarAPdf = new JButton("Exportar a PDF");
		btnExportarAPdf.setBounds(165, 210, 105, 23);
		contentPane.add(btnExportarAPdf);
		
		// Conectar a la base de datos
		Connection conexion = conectar();

		// Seleccionar de la tabla articulos
		// Sacar la información
		txtArea.setText(consulta(conexion));

		// Cerrar la conexión
		desconexion(conexion);
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			}
		});
		btnNewButton.setBounds(298, 210, 89, 23);
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
	
	
	public String consulta(Connection conexion)
	{
		String resultado = "";
				try
		{
			String sentencia = "SELECT idArticulo,cantidadArticulo,precioArticulo,descripcionArticulo FROM articulos order by idArticulo ";
			//Crear una sentencia
			Statement stm = conexion.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			ResultSet rs = stm.executeQuery(sentencia);
			while (rs.next())
			{	

				resultado = resultado + rs.getInt("idArticulo") +
						"-"+rs.getInt("cantidadArticulo")+
						"-"+rs.getDouble("precioArticulo")+
						"-"+rs.getString("descripcionArticulo")+"\n";

			}
		
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return (resultado);
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
