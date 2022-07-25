import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrud {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect()
	{
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost/javacrud","root","");
		}
		catch (ClassNotFoundException ex)
		{
			
			
		}
		catch (SQLException ex)
		{
			
			
		}
	}

	
	public void table_load() {
		try
		{
			pst = con.prepareStatement("select * from book");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 902, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book Shop");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(333, -14, 259, 71);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(24, 59, 396, 229);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(23, 61, 92, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Price");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(23, 170, 92, 14);
		panel.add(lblNewLabel_1_1_1);
		
		txtbname = new JTextField();
		txtbname.setBounds(180, 60, 143, 20);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setBounds(180, 116, 143, 20);
		panel.add(txtedition);
		txtedition.setColumns(10);
		
		txtprice = new JTextField();
		txtprice.setBounds(180, 169, 143, 20);
		panel.add(txtprice);
		txtprice.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Edition");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(23, 117, 81, 14);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String bname,edition,price;
				
				bname = txtbname.getText();
				edition = txtedition.getText();
				price = txtprice.getText();
				
				
				try {
					  pst = con.prepareStatement("insert into book(name,edition,price)values(?,?,?)");
		              pst.setString(1, bname);
		              pst.setString(2, edition);
		              pst.setString(3, price);
		              pst.executeUpdate();
		              JOptionPane.showMessageDialog(null, "Record Addedddd!!!!");
		              table_load();
		              txtbname.setText("");
		              txtedition.setText("");
		              txtprice.setText("");
		              txtbname.requestFocus();
							  
					}
				    
				    catch (SQLException e1) {
				    	
				    	e1.printStackTrace();
				    }
				
				
				
				
				
				
			}
		});
	
		btnNewButton.setBounds(26, 325, 114, 39);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.setBounds(172, 325, 103, 39);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Clear");
		btnNewButton_2.setBounds(308, 325, 112, 39);
		frame.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(454, 68, 410, 288);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(25, 391, 408, 59);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Book ID");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(22, 23, 66, 14);
		panel_1.add(lblNewLabel_3);
		
		txtbid = new JTextField();
		txtbid.addKeyListener (new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				
				try {
					String id = txtbid.getText();
					
					    pst = con.prepareStatement("select name,edition,price from book where id = ?");
					    pst.setString(1,  id);
					    ResultSet rs = pst.executeQuery();
					if(rs.next()==true)
					{
						final String name = rs.getString(1);
						final String edition = rs.getString(2);
						final String price = rs.getString(3);
						
						txtbname.setText(name);
						txtedition.setText(edition);
						txtprice.setText(price);
						
					}
					else
					{
						txtbname.setText("");
						txtedition.setText("");
						txtprice.setText("");
					}
				
		   } 
				catch(SQLException ex) {
				
				
				
	
			
		      }
			
		});
		txtbid.setBounds(187, 22, 136, 20);
		panel_1.add(txtbid);
		txtbid.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Update");
		btnNewButton_3.setBounds(536, 379, 103, 39);
		frame.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Delete");
		btnNewButton_4.setBounds(649, 379, 103, 39);
		frame.getContentPane().add(btnNewButton_4);
	}

	
