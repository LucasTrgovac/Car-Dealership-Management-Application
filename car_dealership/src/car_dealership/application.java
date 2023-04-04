package car_dealership;

import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

//import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

public class application {

	JFrame frame;
	private JTextField txtbname;
	private JTextField txtmname;
	private JTextField txtyear;
	private JTable table;
	private JTextField txtcid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					application window = new application();
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
	public application() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JTextField txtmileage;
	private JTextField txtfuel;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/car_dealership", "root", "");
			
		}
		catch (ClassNotFoundException ex)
		{
			
		}
		catch (SQLException ex)
		{
			
		}
	}
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from car");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 883, 515);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Car Dealership");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(46, 11, 763, 58);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(46, 80, 317, 240);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Brand");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(22, 30, 86, 26);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Model");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(22, 70, 86, 26);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Year");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(22, 107, 86, 26);
		panel.add(lblNewLabel_1_2);
		
		txtbname = new JTextField();
		txtbname.setBounds(118, 32, 130, 26);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtmname = new JTextField();
		txtmname.setBounds(118, 72, 130, 26);
		panel.add(txtmname);
		txtmname.setColumns(10);
		
		txtyear = new JTextField();
		txtyear.setBounds(118, 109, 130, 26);
		panel.add(txtyear);
		txtyear.setColumns(10);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Mileage");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2_1.setBounds(22, 144, 86, 26);
		panel.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("Fuel");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2_1_1.setBounds(22, 181, 86, 26);
		panel.add(lblNewLabel_1_2_1_1);
		
		txtmileage = new JTextField();
		txtmileage.setColumns(10);
		txtmileage.setBounds(118, 149, 130, 26);
		panel.add(txtmileage);
		
		txtfuel = new JTextField();
		txtfuel.setColumns(10);
		txtfuel.setBounds(118, 186, 130, 26);
		panel.add(txtfuel);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Save - Add car to database
				String brand, model, year, mileage, fuel;
				
				brand = txtbname.getText();
				model = txtmname.getText();
				year = txtyear.getText();
				mileage = txtmileage.getText();
				fuel = txtfuel.getText();
				
				try {
					pst = con.prepareStatement("insert into car(brand, model, year, mileage, fuel) values (?,?,?,?,?)");
					pst.setString(1, brand);
					pst.setString(2, model);
					pst.setString(3, year);
					pst.setString(4, mileage);
					pst.setString(5, fuel);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Car Added to Database.");
					table_load();
					txtbname.setText("");
					txtmname.setText("");
					txtyear.setText("");
					txtmileage.setText("");
					txtfuel.setText("");
					txtbname.requestFocus();
				}
			
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		
		});
		btnNewButton.setBounds(46, 331, 89, 40);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Exit out of Application
				System.exit(0);
				
			}
		});
		btnExit.setBounds(159, 331, 89, 40);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Clear
				txtbname.setText("");
				txtmname.setText("");
				txtyear.setText("");
				txtmileage.setText("");
				txtfuel.setText("");
				txtbname.requestFocus();
				
			}
		});
		btnClear.setBounds(269, 331, 94, 40);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(492, 80, 317, 199);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(46, 382, 317, 83);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Car ID");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(28, 28, 97, 26);
		panel_1.add(lblNewLabel_1_1_1);
		
		txtcid = new JTextField();
		txtcid.setBounds(129, 30, 130, 26);
		panel_1.add(txtcid);
		txtcid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			//search function	
			try {
				String id = txtcid.getText();
					
				pst = con.prepareStatement("select brand, model, year, mileage, fuel from car where id = ?");
				pst.setString(1, id);
				ResultSet rs = pst.executeQuery();
					
				if(rs.next()==true)
				{
					String brand = rs.getString(1);
					String model = rs.getString(2);
					String year = rs.getString(3);
					String mileage = rs.getString(4);
					String fuel = rs.getString(5);
						
					txtbname.setText(brand);
					txtmname.setText(model);
					txtyear.setText(year);
					txtmileage.setText(mileage);
					txtfuel.setText(fuel);
				}
				else {
					txtbname.setText("");
					txtmname.setText("");
					txtyear.setText("");
					txtmileage.setText("");
					txtfuel.setText("");
				}
				
				
				
			}
			catch(SQLException e1) {
					}
				
			finally {	
			}
			}
		});
		txtcid.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//Update from Database	
				String cid, brand, model, year, mileage, fuel;
				
				brand = txtbname.getText();
				model = txtmname.getText();
				year = txtyear.getText();
				mileage = txtmileage.getText();
				fuel = txtfuel.getText();
				cid = txtcid.getText();
				
				try {
				
				pst = con.prepareStatement("update car set brand = ?, model = ?, year = ?, mileage = ?, fuel = ? where id = ?");
				pst.setString(1, brand);
				pst.setString(2, model);
				pst.setString(3, year);
				pst.setString(4, mileage);
				pst.setString(5, fuel);
				pst.setString(6, cid);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Car Database Updated.");
				table_load();
				txtbname.setText("");
				txtmname.setText("");
				txtyear.setText("");
				txtmileage.setText("");
				txtfuel.setText("");
				txtbname.requestFocus();
			}
			
			catch (SQLException e1) 
			{
			e1.printStackTrace();
			}
			}
		});
		btnUpdate.setBounds(492, 297, 144, 70);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//Delete from Database
			String cid;
			cid = txtcid.getText();
				
			try {
					
				pst = con.prepareStatement("delete from car where id = ?");
				
				pst.setString(1, cid);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Car Deleted from Database.");
				table_load();
				txtbname.setText("");
				txtmname.setText("");
				txtyear.setText("");
				txtmileage.setText("");
				txtfuel.setText("");
				txtbname.requestFocus();
			}
			
			catch (SQLException e1) {
			e1.printStackTrace();
			}
					
			}
			
		});
		btnDelete.setBounds(665, 297, 144, 70);
		frame.getContentPane().add(btnDelete);
	}
}
