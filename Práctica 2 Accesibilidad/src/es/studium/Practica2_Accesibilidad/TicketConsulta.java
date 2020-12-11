package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class TicketConsulta extends JFrame 
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
					TicketConsulta frame = new TicketConsulta();
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
	public TicketConsulta() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnConsultaTicket = new JMenu("Consulta Tickets");
		menuBar.add(mnConsultaTicket);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(97, 183, 89, 23);
		contentPane.add(btnImprimir);

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBounds(224, 183, 89, 23);
		contentPane.add(btnVolver);
		
		JTextArea textAConsulta = new JTextArea();
		textAConsulta.setBounds(41, 11, 334, 148);
		contentPane.add(textAConsulta);
		Connection conexion = conectar();
		rellenar(conexion,textAConsulta);
	}
	
	private void rellenar(Connection conexion, JTextArea textAConsulta) {
		
		String sentencia = "SELECT * FROM tickets";
		try {
			
			Statement st = conexion.createStatement();
			Statement st2 = conexion.createStatement();
			ResultSet rs = st.executeQuery(sentencia);
			ResultSet rs2 = null;

			while (rs.next()) 
			{
				String sentencia2 = "SELECT * FROM articulos WHERE idArticuloFK = " + rs.getInt("idTicket") + ";";
				rs2 = st2.executeQuery(sentencia2);
				
				if(textAConsulta.getText().length()==0)
				{
					
					textAConsulta.setText(rs.getInt("idTicket")+
							"-"+rs.getString("fechaTicket")+
							"-"+rs.getInt("idArticuloFK")+
							", "+rs.getDouble("totalTicket")+
							"\n");
					
					while (rs2.next()) 
					{
						int idArt = rs2.getInt("idArticuloFK");
						textAConsulta.setText(textAConsulta.getText() + localizar(conexion, idArt) + "\n");

					}

				}
				else
				{
					textAConsulta.setText(textAConsulta.getText() + "\n" +
							(rs.getInt("idTicket")+
									"-"+rs.getString("fechaTicket")+
									"-"+rs.getInt("idArticuloFK")+
									", "+rs.getDouble("totalTicket")+
									"\n"));

					
					while (rs2.next()) 
					{
						int idArt = rs2.getInt("idArticuloFK");
						textAConsulta.setText(textAConsulta.getText() + localizar(conexion, idArt) + "\n");
					}
				}
			}
			rs2.close();
			rs.close();
			st.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR en la consulta");
			ex.printStackTrace();
		}
		
	}
	public String localizar(Connection conexion, int id) 
	{
		String respuesta ="";
		String sentencia3 = "SELECT * FROM articulos WHERE idArticulo = " + id;
		try {
			Statement stmt3 = conexion.createStatement();
			ResultSet rs3 = stmt3.executeQuery(sentencia3);
			while (rs3.next()) 
			{
				respuesta = rs3.getString("descripcionArticulo");
			}
			rs3.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return respuesta;
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
