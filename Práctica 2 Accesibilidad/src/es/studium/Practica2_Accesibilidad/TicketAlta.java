package es.studium.Practica2_Accesibilidad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class TicketAlta extends JFrame 
{

	private JPanel contentPane;
	private JTextField txtFecha;
	private JTextField textIdArtFK;
	private JTextField textTotal;

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
					TicketAlta frame = new TicketAlta();
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
	public TicketAlta() 
	{
		setTitle("Tiendecita");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAltaTickets = new JMenu("Alta Tickets");
		menuBar.add(mnAltaTickets);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFecha = new JLabel("Fecha: ");
		lblFecha.setBounds(36, 54, 46, 14);
		contentPane.add(lblFecha);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(159, 51, 86, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		JLabel lblArticulos = new JLabel("Articulos:");
		lblArticulos.setBounds(36, 91, 46, 14);
		contentPane.add(lblArticulos);
		
		textIdArtFK = new JTextField();
		textIdArtFK.setColumns(10);
		textIdArtFK.setBounds(159, 88, 146, 20);
		contentPane.add(textIdArtFK);
		
		JLabel lblNewLabel = new JLabel("Total:");
		lblNewLabel.setBounds(36, 129, 46, 14);
		contentPane.add(lblNewLabel);
		
		textTotal = new JTextField();
		textTotal.setBounds(159, 126, 86, 20);
		contentPane.add(textTotal);
		textTotal.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				// Conectar BD
				Connection conexion = conectar();
				//Proceder el INSERT
				String [] fechaAmericana = txtFecha.getText().split("/");
				int respuesta = insertar(conexion, "tickets", fechaAmericana,textIdArtFK.getText(),textTotal.getText());
				
				// Mostramos resultado
				if(respuesta == 0)
				{
					System.out.println("ALTA de artículo correcta");
				}
				else
				{
					System.out.println("Error en Alta de artículo");//
				}
				// Desconectar de la base
				desconexion(conexion);
			}
		});
		btnGuardar.setBounds(49, 179, 89, 23);
		contentPane.add(btnGuardar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtFecha.selectAll();
				txtFecha.setText("");
				txtFecha.requestFocus();

				textIdArtFK.selectAll();
				textIdArtFK.setText("");


				textTotal.selectAll();
				textTotal.setText("");
			}
		});
		btnLimpiar.setBounds(177, 179, 89, 23);
		contentPane.add(btnLimpiar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
			
		});
		btnVolver.setBounds(304, 179, 89, 23);
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
	
	public int insertar(Connection conexion, String tickets, String [] fechaAmericana, 
			String idArticuloFK, String totalTicket) 
	{		
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = conexion.createStatement();
			String cadenaSQL = "INSERT INTO " + tickets+" VALUES (null, '" +fechaAmericana[2]+"-"+fechaAmericana[1]+"-"+fechaAmericana[0]+"' ,'"+idArticuloFK+"', '"+totalTicket +"');";

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
