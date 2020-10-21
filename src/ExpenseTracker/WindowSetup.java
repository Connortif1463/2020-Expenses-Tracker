package ExpenseTracker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Double;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JScrollPane;
import java.awt.SystemColor;

public class WindowSetup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final AbstractButton JTextAreaContent = null;
	private JPanel contentPane;
	private JFrame frame;
	private JTextField PurchaseNameField;
	private JTable expendituresTable;
	private JTextField txtTotal;
	private JTextField total2;
	private JTextField AmountSpentTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowSetup frame = new WindowSetup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WindowSetup() {
		setResizable(false);
		setForeground(Color.GRAY);
		setTitle("Budgeting Sheet ToolKit");
		setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 906, 700);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New Sheet");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					WindowSetup frame = new WindowSetup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open/Add...");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
				
				JFileChooser jFileChooser = new JFileChooser();
				
				StringBuilder sb = new StringBuilder();
				int lineCount = 0;
				
				jFileChooser.setDialogTitle("Open File");
				int result =  jFileChooser.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					try {
						File file = jFileChooser.getSelectedFile();
						Scanner input = new Scanner(file);
						while(input.hasNext()) {
							String row = input.nextLine();
							String[] columns = row.split(",");
							model.addRow(new Object[] {columns[0], columns[1], columns[2], columns[3], columns[4]});
							
							lineCount++;
						}
						
						input.close();
					}
					catch(Exception arg0) {
						sb.append("No File Selected");
					}	
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem menuSave = new JMenuItem("Save...");
		menuSave.addActionListener(new ActionListener() {
			public void toCSV(JTable table, File file){
			    try{
			        TableModel model = table.getModel();
			        FileWriter csvWriter = new FileWriter(file);
			        
			        for(int i=0; i< model.getRowCount(); i++) {
			            for(int j=0; j < model.getColumnCount(); j++) {
			                csvWriter.write(model.getValueAt(i,j).toString()+",");
			            }
			            csvWriter.write("\n");
			        }

			        csvWriter.close();

			    }
			    catch(IOException e){
			    	System.out.println(e);
			    	}
			}
			
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Save File");
				int result =  jFileChooser.showSaveDialog(null);
				
				File file = jFileChooser.getSelectedFile();
				
				if(result == JFileChooser.APPROVE_OPTION) {
					
					try {
						
						FileOutputStream fileOutputStream = 
								new FileOutputStream(file);
						fileOutputStream.write(JTextAreaContent.getText().getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
					}
					catch(Exception e) {
						//JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
				
				try {
					toCSV(expendituresTable, file);
					JOptionPane.showMessageDialog(frame, "Saved.");
				}
				catch(Exception e) {
					System.err.format("File not found.", e.getMessage());
				}
			}
		});
		mnNewMenu.add(menuSave);
		
		JMenuItem mntmNewMenuItem_12 = new JMenuItem("Print");
		mntmNewMenuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					expendituresTable.print();
				}
				catch(java.awt.print.PrinterException e) {
					System.err.format("Printer Not Found", e.getMessage());
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_12);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Reset");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame = new JFrame();
				if(expendituresTable.getRowCount() != 0) {
					if(JOptionPane.showConfirmDialog(frame,
							"Sheet will reset. Are you sure?",
							"Budgeting Sheet Toolkit",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
						model.setRowCount(0);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Sheet is already empty.");
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JMenu mnNewMenu_1 = new JMenu("Edit");
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_2 = new JMenu("Sort By");
		mnNewMenu_1.add(mnNewMenu_2);
		
		JMenu mnNewMenu_3 = new JMenu("Purchases");
		mnNewMenu_2.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("A to Z");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			
			private void sortRowsAscending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			public void actionPerformed(ActionEvent e) {
				sortRowsAscending(0);
			}
		});
		
		mnNewMenu_3.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Z to A");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			
			private void sortRowsDescending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			public void actionPerformed(ActionEvent e) {
				sortRowsDescending(0);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_6 = new JMenu("Methods");
		mnNewMenu_2.add(mnNewMenu_6);
		
		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Cash-\r\nCredit-\r\nDebit");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			private void sortRowsAscending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			public void actionPerformed(ActionEvent e) {
				sortRowsAscending(3);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_10);
		
		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Debit-Credit-Cash");
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			
			private void sortRowsDescending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			public void actionPerformed(ActionEvent e) {
				sortRowsDescending(3);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_11);
		
		JMenu mnNewMenu_8 = new JMenu("Categories");
		mnNewMenu_2.add(mnNewMenu_8);
		
		JMenuItem mntmNewMenuItem_15 = new JMenuItem("Categories (A to Z)");
		mntmNewMenuItem_15.addActionListener(new ActionListener() {
			private void sortRowsAscending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			public void actionPerformed(ActionEvent e) {
				sortRowsAscending(4);
			}
		});
		mnNewMenu_8.add(mntmNewMenuItem_15);
		
		JMenuItem mntmNewMenuItem_16 = new JMenuItem("Categories (Z to A)");
		mntmNewMenuItem_16.addActionListener(new ActionListener() {
			
			private void sortRowsDescending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			public void actionPerformed(ActionEvent e) {
				sortRowsDescending(4);
			}
		});
		mnNewMenu_8.add(mntmNewMenuItem_16);
		
		JMenu mnNewMenu_9 = new JMenu("Remove");
		mnNewMenu_1.add(mnNewMenu_9);
		
		JMenuItem mntmNewMenuItem_17 = new JMenuItem("Remove All Rows");
		mntmNewMenuItem_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame = new JFrame();
				if(expendituresTable.getRowCount() != 0) {
					if(JOptionPane.showConfirmDialog(frame,
							"Sheet will reset. Are you sure?",
							"Budgeting Sheet Toolkit",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
						model.setRowCount(0);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Sheet is already empty.");
				}
				
			}
		});
		mnNewMenu_9.add(mntmNewMenuItem_17);
		
		JMenuItem mntmNewMenuItem_18 = new JMenuItem("Remove Selected Row");
		mntmNewMenuItem_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(expendituresTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(frame, "Sheet is already empty.");
				}
				else {
					try {
						DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
						model.removeRow(expendituresTable.getSelectedRow());
					}
					catch(java.lang.ArrayIndexOutOfBoundsException arg0) {
						JOptionPane.showMessageDialog(frame, "Select a row.");
					}
				}
				
			}
		});
		mnNewMenu_9.add(mntmNewMenuItem_18);
		
		JMenu mnNewMenu_7 = new JMenu("Exit");
		menuBar.add(mnNewMenu_7);
		
		JMenuItem mntmNewMenuItem_13 = new JMenuItem("Exit and Save");
		mntmNewMenuItem_13.addActionListener(new ActionListener() {
			public void toCSV(JTable table, File file){
			    try{
			        TableModel model = table.getModel();
			        FileWriter csvWriter = new FileWriter(file);
			        
			        for(int i=0; i< model.getRowCount(); i++) {
			            for(int j=0; j < model.getColumnCount(); j++) {
			                csvWriter.write(model.getValueAt(i,j).toString()+",");
			            }
			            csvWriter.write("\n");
			        }

			        csvWriter.close();

			    }
			    catch(IOException e){
			    	System.out.println(e);
			    	}
			}
			
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Save File");
				int result =  jFileChooser.showSaveDialog(null);
				
				File file = jFileChooser.getSelectedFile();
				
				if(result == JFileChooser.APPROVE_OPTION) {
					
					try {
						
						FileOutputStream fileOutputStream = 
								new FileOutputStream(file);
						fileOutputStream.write(JTextAreaContent.getText().getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
					}
					catch(Exception arg1) {
						//JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
				
				try {
					toCSV(expendituresTable, file);
					JOptionPane.showMessageDialog(frame, "Saved.");
				}
				catch(Exception arg0) {
					System.err.format("File not found.", arg0.getMessage());
				}
				
				System.exit(0);
				
			}
		});
		mnNewMenu_7.add(mntmNewMenuItem_13);
		
		JMenuItem mntmNewMenuItem_14 = new JMenuItem("Exit without Saving");
		mntmNewMenuItem_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new JFrame();
				if(JOptionPane.showConfirmDialog(frame,
						"Are you sure?",
						"Budgeting Sheet Toolkit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		mnNewMenu_7.add(mntmNewMenuItem_14);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Purchase");
		lblNewLabel.setBounds(30, 69, 65, 27);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		contentPane.add(lblNewLabel);
		
		JLabel lblBusinessExpenses = new JLabel("Expenditures 2020");
		lblBusinessExpenses.setBounds(30, 22, 237, 36);
		lblBusinessExpenses.setForeground(Color.WHITE);
		lblBusinessExpenses.setFont(new Font("Segoe UI Light", Font.PLAIN, 26));
		contentPane.add(lblBusinessExpenses);
		
		PurchaseNameField = new JTextField();
		PurchaseNameField.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		PurchaseNameField.setForeground(Color.WHITE);
		PurchaseNameField.setBackground(Color.GRAY);
		PurchaseNameField.setBounds(30, 98, 237, 20);
		contentPane.add(PurchaseNameField);
		PurchaseNameField.setColumns(10);
		
		JLabel Date = new JLabel("Date of Purchase");
		Date.setForeground(Color.WHITE);
		Date.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		Date.setBounds(30, 229, 138, 22);
		contentPane.add(Date);
		
		JLabel lblAmount = new JLabel("Amount Spent");
		lblAmount.setForeground(Color.WHITE);
		lblAmount.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		lblAmount.setBounds(30, 150, 108, 27);
		contentPane.add(lblAmount);
		
		JButton AddPurchase = new JButton("Add Purchase");
		
		
		AddPurchase.setFont(new Font("Segoe UI Light", Font.PLAIN, 23));
		AddPurchase.setBackground(Color.LIGHT_GRAY);
		AddPurchase.setForeground(Color.BLACK);
		AddPurchase.setBounds(30, 374, 237, 50);
		contentPane.add(AddPurchase);
		
		JButton btnNewButton = new JButton("Current Date");
		
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnNewButton.setBounds(161, 229, 106, 23);
		contentPane.add(btnNewButton);
		
		expendituresTable = new JTable();
		expendituresTable.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		expendituresTable.setForeground(Color.BLACK);
		expendituresTable.setBackground(SystemColor.menu);
		expendituresTable.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
		expendituresTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Purchases", "Amounts", "Dates", "Methods", "Categories"
			}
		));
		expendituresTable.setBounds(296, 64, 573, 405);
		contentPane.add(expendituresTable);
		
		JButton btnPrintSpreadsheet = new JButton("Print Spreadsheet");
		btnPrintSpreadsheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					expendituresTable.print();
				}
				catch(java.awt.print.PrinterException e) {
					System.err.format("Printer Not Found", e.getMessage());
				}
			}
		});
		btnPrintSpreadsheet.setForeground(Color.BLACK);
		btnPrintSpreadsheet.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnPrintSpreadsheet.setBackground(Color.LIGHT_GRAY);
		btnPrintSpreadsheet.setBounds(537, 591, 174, 36);
		contentPane.add(btnPrintSpreadsheet);
		
		JButton btnBruh = new JButton("Save File");
		btnBruh.addActionListener(new ActionListener() {
			public void toCSV(JTable table, File file){
			    try{
			        TableModel model = table.getModel();
			        FileWriter csvWriter = new FileWriter(file);
			        
			        for(int i=0; i< model.getRowCount(); i++) {
			            for(int j=0; j < model.getColumnCount(); j++) {
			                csvWriter.write(model.getValueAt(i,j).toString()+",");
			            }
			            csvWriter.write("\n");
			        }

			        csvWriter.close();

			    }
			    catch(IOException e){
			    	System.out.println(e);
			    	}
			}
			
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("Save File");
				int result =  jFileChooser.showSaveDialog(null);
				
				File file = jFileChooser.getSelectedFile();
				
				if(result == JFileChooser.APPROVE_OPTION) {
					
					try {
						
						FileOutputStream fileOutputStream = 
								new FileOutputStream(file);
						fileOutputStream.write(JTextAreaContent.getText().getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
					}
					catch(Exception e) {
						//JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
				
				try {
					toCSV(expendituresTable, file);
					JOptionPane.showMessageDialog(frame, "Saved.");
				}
				catch(Exception e) {
					System.err.format("File not found.", e.getMessage());
				}
			}
		});
		
		btnBruh.setForeground(Color.BLACK);
		btnBruh.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnBruh.setBackground(Color.LIGHT_GRAY);
		btnBruh.setBounds(721, 591, 148, 36);
		contentPane.add(btnBruh);
		
		JLabel lblSortBy = new JLabel("Sort By:");
		lblSortBy.setForeground(Color.WHITE);
		lblSortBy.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		lblSortBy.setBounds(30, 489, 55, 20);
		contentPane.add(lblSortBy);
		
		JButton btnReset = new JButton("Reset File");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame = new JFrame();
				if(expendituresTable.getRowCount() != 0) {
					if(JOptionPane.showConfirmDialog(frame,
							"Sheet will reset. Are you sure?",
							"Budgeting Sheet Toolkit",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
						model.setRowCount(0);
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, "Sheet is already empty.");
				}
				
			}
		});
		btnReset.setForeground(Color.BLACK);
		btnReset.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnReset.setBackground(Color.LIGHT_GRAY);
		btnReset.setBounds(30, 591, 138, 36);
		contentPane.add(btnReset);
		
		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		txtTotal.setBackground(Color.LIGHT_GRAY);
		txtTotal.setText("total");
		txtTotal.setBounds(752, 557, 117, 23);
		contentPane.add(txtTotal);
		txtTotal.setColumns(10);
		
		JLabel lblMethod = new JLabel("Method:");
		lblMethod.setForeground(Color.WHITE);
		lblMethod.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblMethod.setBounds(148, 154, 64, 20);
		contentPane.add(lblMethod);
		
		JButton btnCalculateTotalCost = new JButton("Calculate Total");
		btnCalculateTotalCost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(expendituresTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(frame, "Sheet is empty.");
				}
				else {
					try {
						Double sum = 0.0;
						Double amount = 0.0;
						
						for(int i = 0; i < expendituresTable.getRowCount(); i++) {
							
							String value = String.valueOf(expendituresTable.getValueAt(i, 1));
							
							amount = Double.parseDouble(value);
							sum += amount;
						}
					
						txtTotal.setText(String.valueOf(sum));
					}
					
					catch(java.lang.NumberFormatException args0) {
						JOptionPane.showMessageDialog(frame, "(Make sure that empty amounts are 0)");
					}
					
				}
			}
		});
		
		btnCalculateTotalCost.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnCalculateTotalCost.setBackground(Color.LIGHT_GRAY);
		btnCalculateTotalCost.setBounds(607, 557, 135, 23);
		contentPane.add(btnCalculateTotalCost);
		
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setForeground(Color.WHITE);
		lblCategory.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		lblCategory.setBounds(30, 292, 78, 40);
		contentPane.add(lblCategory);
		
		JButton btnCalculateTotalFor = new JButton("Calculate Total for:");
		
		btnCalculateTotalFor.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnCalculateTotalFor.setBackground(Color.LIGHT_GRAY);
		btnCalculateTotalFor.setBounds(463, 523, 135, 23);
		contentPane.add(btnCalculateTotalFor);
		
		total2 = new JTextField();
		total2.setText("total");
		total2.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		total2.setColumns(10);
		total2.setBackground(Color.LIGHT_GRAY);
		total2.setBounds(752, 523, 117, 23);
		contentPane.add(total2);
		
		JComboBox MonthBox = new JComboBox();
		MonthBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		MonthBox.setModel(new DefaultComboBoxModel(new String[] {"Month", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		MonthBox.setForeground(Color.WHITE);
		MonthBox.setBackground(Color.GRAY);
		MonthBox.setBounds(30, 262, 65, 20);
		contentPane.add(MonthBox);
		
		JComboBox DayBox = new JComboBox();
		DayBox.setModel(new DefaultComboBoxModel(new String[] {"Day", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		DayBox.setForeground(Color.WHITE);
		DayBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		DayBox.setBackground(Color.GRAY);
		DayBox.setBounds(103, 262, 65, 20);
		contentPane.add(DayBox);
		
		JComboBox YearBox = new JComboBox();
		YearBox.setModel(new DefaultComboBoxModel(new String[] {"Year", "2019", "2020"}));
		YearBox.setForeground(Color.WHITE);
		YearBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		YearBox.setBackground(Color.GRAY);
		YearBox.setBounds(176, 262, 91, 20);
		contentPane.add(YearBox);
		
		JComboBox CategoryBox = new JComboBox();
		CategoryBox.setModel(new DefaultComboBoxModel(new String[] {"Select Category", "Housing", "Transportation", "Food", "Utilities", "Clothing", "Medical/Healthcare", "Insurance", "Household Items/Supplies", "Personal", "Debt", "Retirement", "Education", "Savings", "Gifts/Donations", "Quality of Life"}));
		CategoryBox.setForeground(Color.WHITE);
		CategoryBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		CategoryBox.setBackground(Color.GRAY);
		CategoryBox.setBounds(30, 329, 237, 20);
		contentPane.add(CategoryBox);
		
		JComboBox CategoryBox2 = new JComboBox();
		CategoryBox2.setModel(new DefaultComboBoxModel(new String[] {"Category", "Housing", "Transportation", "Food", "Utilities", "Clothing", "Medical/Healthcare", "Insurance", "Household Items/Supplies", "Personal", "Debt", "Retirement", "Education", "Savings", "Gifts/Donations", "Quality of Life"}));
		CategoryBox2.setForeground(Color.WHITE);
		CategoryBox2.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		CategoryBox2.setBackground(Color.GRAY);
		CategoryBox2.setBounds(607, 523, 135, 23);
		contentPane.add(CategoryBox2);
		
		JComboBox sortBySelect = new JComboBox();
		sortBySelect.setModel(new DefaultComboBoxModel(new String[] {"Purchases (A to Z)", "Purchases (Z to A)", "Methods (A to Z)", "Methods (Z to A)", "Categories (A to Z)", "Categories (Z to A)"}));
		sortBySelect.setForeground(Color.WHITE);
		sortBySelect.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		sortBySelect.setBackground(Color.GRAY);
		sortBySelect.setBounds(89, 489, 138, 23);
		contentPane.add(sortBySelect);
		
		JComboBox MethodBox = new JComboBox();
		MethodBox.setModel(new DefaultComboBoxModel(new String[] {"Select", "Cash", "Credit", "Debit"}));
		MethodBox.setForeground(Color.WHITE);
		MethodBox.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		MethodBox.setBackground(Color.GRAY);
		MethodBox.setBounds(202, 150, 65, 23);
		contentPane.add(MethodBox);
		
		JButton btnClearPurchase = new JButton("Clear Fields");
		btnClearPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PurchaseNameField.setText("");
				AmountSpentTextField.setText("");
				MonthBox.setSelectedItem("Month");
				DayBox.setSelectedItem("Day");
				YearBox.setSelectedItem("Year");
				CategoryBox.setSelectedItem("Select Category");
				MethodBox.setSelectedItem("Select");
			}
		});
		btnClearPurchase.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnClearPurchase.setBackground(Color.LIGHT_GRAY);
		btnClearPurchase.setBounds(30, 435, 135, 23);
		contentPane.add(btnClearPurchase);
		
		AmountSpentTextField = new JTextField();
		AmountSpentTextField.setForeground(Color.WHITE);
		AmountSpentTextField.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		AmountSpentTextField.setColumns(10);
		AmountSpentTextField.setBackground(Color.GRAY);
		AmountSpentTextField.setBounds(30, 178, 237, 20);
		contentPane.add(AmountSpentTextField);
		
		JButton btnRemovePurchase = new JButton("Remove Purchase");
		btnRemovePurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(expendituresTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(frame, "Sheet is already empty.");
				}
				else {
					try {
						DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
						model.removeRow(expendituresTable.getSelectedRow());
					}
					catch(java.lang.ArrayIndexOutOfBoundsException arg0) {
						JOptionPane.showMessageDialog(frame, "Select a row.");
					}
				}
			}
		});
		btnRemovePurchase.setForeground(Color.BLACK);
		btnRemovePurchase.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnRemovePurchase.setBackground(Color.LIGHT_GRAY);
		btnRemovePurchase.setBounds(178, 591, 167, 36);
		contentPane.add(btnRemovePurchase);
		
		JButton btnSortEntries = new JButton("Sort Entries");
		
		btnSortEntries.setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		btnSortEntries.setBackground(Color.LIGHT_GRAY);
		btnSortEntries.setBounds(30, 523, 135, 23);
		contentPane.add(btnSortEntries);
		
		JButton btnAddEmptyRow = new JButton("Add Empty Row");
		btnAddEmptyRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
				
				model .addRow(new Object[] {"", 0,"","",""});
				
			}
		});
		btnAddEmptyRow.setForeground(Color.BLACK);
		btnAddEmptyRow.setFont(new Font("Segoe UI Light", Font.PLAIN, 17));
		btnAddEmptyRow.setBackground(Color.LIGHT_GRAY);
		btnAddEmptyRow.setBounds(355, 591, 172, 36);
		contentPane.add(btnAddEmptyRow);
		
		JScrollPane scrollPane = new JScrollPane(expendituresTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(296, 22, 573, 487);
		contentPane.add(scrollPane);
		
		JLabel lblDates = new JLabel("Dates");
		lblDates.setForeground(Color.WHITE);
		lblDates.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblDates.setBounds(566, 31, 35, 27);
		contentPane.add(lblDates);
		
		JLabel lblAmounts = new JLabel("Amounts");
		lblAmounts.setForeground(Color.WHITE);
		lblAmounts.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblAmounts.setBounds(439, 31, 55, 27);
		contentPane.add(lblAmounts);
		
		
		
		JLabel lblPurchase = new JLabel("Purchases");
		lblPurchase.setForeground(Color.WHITE);
		lblPurchase.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblPurchase.setBounds(323, 31, 70, 27);
		contentPane.add(lblPurchase);
		
		JLabel lblMethods = new JLabel("Methods");
		lblMethods.setForeground(Color.WHITE);
		lblMethods.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblMethods.setBounds(669, 31, 55, 27);
		contentPane.add(lblMethods);
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setForeground(Color.WHITE);
		lblCategories.setFont(new Font("Segoe UI Light", Font.PLAIN, 15));
		lblCategories.setBounds(775, 31, 70, 27);
		contentPane.add(lblCategories);
		
		
		AddPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) expendituresTable.getModel();
				
				
				String date = MonthBox.getSelectedItem()+"/"+
				DayBox.getSelectedItem()+"/"+
				YearBox.getSelectedItem();
				
				if(		(MonthBox.getSelectedItem() == "2" && DayBox.getSelectedItem() == "29" && YearBox.getSelectedItem() == "2019") ||
						(MonthBox.getSelectedItem() == "2" && DayBox.getSelectedItem() == "30" && YearBox.getSelectedItem() == "2019") ||
						(MonthBox.getSelectedItem() == "4" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2019")
						) {
					JOptionPane.showMessageDialog(frame, "Not a real Date.");
				}
				else if((MonthBox.getSelectedItem() == "4" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2019") ||
						(MonthBox.getSelectedItem() == "6" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2019") ||
						(MonthBox.getSelectedItem() == "9" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2019") ||
						(MonthBox.getSelectedItem() == "11" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2019")
						) {
					JOptionPane.showMessageDialog(frame, "Not a real Date.");
				}
				else if((MonthBox.getSelectedItem() == "2" && DayBox.getSelectedItem() == "30" && YearBox.getSelectedItem() == "2020") ||
						(MonthBox.getSelectedItem() == "2" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2020") ||
						(MonthBox.getSelectedItem() == "4" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2020") ||
						(MonthBox.getSelectedItem() == "6" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2020") ||
						(MonthBox.getSelectedItem() == "9" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2020") ||
						(MonthBox.getSelectedItem() == "11" && DayBox.getSelectedItem() == "31" && YearBox.getSelectedItem() == "2020")
						) {
					JOptionPane.showMessageDialog(frame, "Not a real Date.");
				}
				
				else if(MonthBox.getSelectedItem() ==  "Month" 
						|| DayBox.getSelectedItem() == "Day" 
						|| YearBox.getSelectedItem() == "Year" 
						|| PurchaseNameField.getText() == ""
						|| AmountSpentTextField.getText() == ""
						|| MethodBox.getSelectedItem() == "Select"
						|| CategoryBox.getSelectedItem() == "Select Category"
						) {
					JOptionPane.showMessageDialog(frame, "Fields Incomplete.");
				}
				else {
					
					try
					{
					  Double.parseDouble(AmountSpentTextField.getText().trim());
					  
					  model.addRow(new Object[] {PurchaseNameField.getText(), Double.parseDouble(AmountSpentTextField.getText()), date, (String) MethodBox.getSelectedItem(), (String) CategoryBox.getSelectedItem()});
						
						PurchaseNameField.setText("");
						AmountSpentTextField.setText("");
						MonthBox.setSelectedItem("Month");
						DayBox.setSelectedItem("Day");
						YearBox.setSelectedItem("Year");
						CategoryBox.setSelectedItem("Select Category");
						MethodBox.setSelectedItem("Select");
					  
					}
					catch(java.lang.NumberFormatException e)
					{
						JOptionPane.showMessageDialog(frame, "Amount is not a number.");
					}
				}
				
			}
		});
		
		btnCalculateTotalFor.addActionListener(new ActionListener() {
			public void sumForCategory(String category) {
				Double sum = 0.0;
				Double amount = 0.0;
				
				for(int i = 0; i < expendituresTable.getRowCount(); i++) {
					if(expendituresTable.getValueAt(i, 4) == category) {
						String value = String.valueOf(expendituresTable.getValueAt(i, 1));
						
						amount = Double.parseDouble(value);
						sum += amount;
					}
				}
				
				total2.setText(String.valueOf(sum));
			}
			
			public void actionPerformed(ActionEvent arg0) {
				if(CategoryBox2.getSelectedItem() == "Category") {
					JOptionPane.showMessageDialog(frame, "Select Category.");
				}
				else if(expendituresTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(frame, "Sheet is empty.");
				}
				else {
					try {
						sumForCategory((String) CategoryBox2.getSelectedItem());
					}
					
					catch(java.lang.NumberFormatException args0) {
						JOptionPane.showMessageDialog(frame, "(Make sure that empty amounts are 0)");
					}
				}
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String dateString = java.time.LocalDate.now().format(dateTimeFormatter);
				MonthBox.setSelectedItem(dateString.substring(5,7));
				DayBox.setSelectedItem(dateString.substring(8,10));
				YearBox.setSelectedItem(dateString.substring(0,4));
			}
		});
		
		btnSortEntries.addActionListener(new ActionListener() {
			
			private void sortRowsAscending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			private void sortRowsDescending(int columnIndexToSort) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<>(expendituresTable.getModel());
				expendituresTable.setRowSorter(sorter);
				ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
				sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
				 
				sorter.setSortKeys(sortKeys);
				sorter.sort();
			}
			
			/*
			
			private void SortDoublesAscending(Object[][] array, int column) {
				
				int rows = expendituresTable.getRowCount();
				int columns = expendituresTable.getColumnCount();
				
				String[][] arrayString = new String[rows][columns];
				for(int i = 0; i < rows; i++) {
					for(int n = 0; n < columns; n++) {
						arrayString[i][n] = (String) array[i][n];
					}
				}
				
				for(int i = 0; i < rows; i++) {
					for(int n = 0; n < columns; n++) {
						System.out.print(arrayString[i][n]);
					}
				}
				
				for(int i = 0; i < rows; i++) {
					if(Double.parseDouble(arrayString[i][1]) < Double.parseDouble(arrayString[i + 1][1])) {
						Object[] tempValue = (Object[]) array[i + 1][1];
						array[i + 1][1] = array[i][1];
						array[i][1] = (Object[]) tempValue;
					}
					
				}
				
			}
			
			*/
			
			public void actionPerformed(ActionEvent arg0) {
				
				if(expendituresTable.getRowCount() == 0) {
					JOptionPane.showMessageDialog(frame, "Sheet is empty.");
				}
				else {
						if(sortBySelect.getSelectedIndex() == 0) {
							sortRowsAscending(0);
						}
						else if(sortBySelect.getSelectedIndex() == 1){
							sortRowsDescending(0);
						}
						
						/* method for sorting by amounts
						
						else if(sortBySelect.getSelectedIndex() == 2) {
							
							int rows = expendituresTable.getRowCount();
							int columns =  expendituresTable.getColumnCount();
							Object[][] tableArray = new Object[rows][columns];
							
							for(int i = 0; i < rows; i++) {
								for(int n = 0; n < columns; n++) {
									tableArray[i][n] = expendituresTable.getValueAt(i, n);
								}
							}
							
							for(int i = 0; i < rows; i++) {
								for(int n = 0; n < columns; n++) {
									System.out.print(tableArray[i][n]);
								}
								System.out.println();
							}
							
							SortDoublesAscending(tableArray, 1);
							
						}
							
						else if(sortBySelect.getSelectedIndex() == 3) {
							//code goes here
						}
						
						else if(sortBySelect.getSelectedIndex() == 2) {
							sortRowsAscending(2);
						}
						else if(sortBySelect.getSelectedIndex() == 3) {
							sortRowsDescending(2);
						}
						
						*/
						
						else if(sortBySelect.getSelectedIndex() == 2) {
							sortRowsAscending(3);
						}
						else if(sortBySelect.getSelectedIndex() == 3) {
							sortRowsDescending(3);
						}
						else if(sortBySelect.getSelectedIndex() == 4) {
							sortRowsAscending(4);
						}
						else if(sortBySelect.getSelectedIndex() == 5) {
							sortRowsDescending(4);
					}
				}
			}

		});
		
	}
}
