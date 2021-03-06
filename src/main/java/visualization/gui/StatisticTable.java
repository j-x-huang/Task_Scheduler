package visualization.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import scheduler.Main;

/**
 * Generates a panel containing a table with relevant information about the current process. 
 * The information includes followings:
 * -The number of processors being used by the computer to produce the optimal schedule
 * -The number of solutions created during the calculation
 * -The number of solutions popped during the calculation
 * -The number of solution pruned during the calculation
 * -The amount of memory being used in megabytes (MB)
 * -The time since the program has started in seconds (s)
 * -The length/finish time of the current optimal solution 
 */
@SuppressWarnings("serial")
public class StatisticTable extends JPanel{

	private JTable _table;
	private final static String[] _columns = { "Statistics", "No." };

	/**
	 * Constructor for the statistics table
	 * @param Core
	 */
	public StatisticTable(int Cores, int processors){

		setLayout(new BorderLayout());

		Object[][] data = new Object[8][2];

		data[0][0] = "Number of cores";

		if(Cores == 0){
			data[0][1] = 1;
		} else {
			data[0][1] = Cores;
		}

		data[1][0] = "Number of processors";
		data[1][1] = processors;
		data[2][0] = "Solutions created";
		data[2][1] = "Processing...";
		data[3][0] = "Solutions popped";
		data[3][1] = "Processing...";
		data[4][0] = "Solutions pruned";
		data[4][1] = "Processing...";
		data[5][0] = "Memory usage (MB)";
		data[5][1] = "Processing...";
		data[6][0] = "Time taken (sec)";
		data[6][1] = "Processing...";
		data[7][0] = "Current Optimal Solution Time";
		data[7][1] = "Processing...";

		_table = new JTable(new Model(data, _columns));
		_table.getTableHeader().setReorderingAllowed(false);
		_table.getTableHeader().setResizingAllowed(false);
		_table.setRowSelectionAllowed(false);
		_table.setFont(new Font("SansSerif", Font.PLAIN, 18));

		_table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 25));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		_table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		_table.setRowHeight(62);



		TableCellRenderer leftRenderer = new TableCellRenderer();
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		_table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);

		for (int row = 0; row < _table.getRowCount(); row++){
			int rowHeight = _table.getRowHeight();

			for (int column = 0; column < _table.getColumnCount(); column++){
				Component component = _table.prepareRenderer(_table.getCellRenderer(row, column), row, column);
				rowHeight = Math.max(rowHeight, component.getPreferredSize().height);
			}
			_table.setRowHeight(row, rowHeight);
		}

		JScrollPane scroll = new JScrollPane(_table);
		add(scroll);
	}

	/**
	 * Method to update all the statistics in the table with finish time
	 * @param solCreated
	 * @param solPopped
	 * @param solPruned
	 * @param time
	 * @param finishTime
	 */
	public void updateStats(int solCreated, int solPopped, int solPruned, Integer finishTime){

		DefaultTableModel model = (DefaultTableModel)_table.getModel();

		model.setValueAt(solCreated, 2, 1);
		model.setValueAt(solPopped, 3, 1);
		model.setValueAt(solPruned, 4, 1);
		model.setValueAt((Runtime.getRuntime().totalMemory())/1024/1024, 5, 1);
		
		model.setValueAt((double)(System.nanoTime()-Main._startTime)/1000000000, 6, 1);

		
		if(finishTime != null){
			model.setValueAt(finishTime, 7, 1);
		}

	}
	
	/**
	 * Model for the statistics table
	 */
	private class Model extends DefaultTableModel {

		Model(Object[][] data, String[] column) {
			super(data, column);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	/**
	 * Getter method to return the statistics table
	 * @return the StatisticTable
	 */
	public JTable getTable(){

		return _table;
	}
	
	/**
	 * This class creates a renderer that gives padding to the cells.
	 * referenced from stackoverflow
	 * https://stackoverflow.com/questions/17188117/i-would-like-to-add-a-right-padding-to-a-jtable-column-is-it-possible
	 *
	 */
	class TableCellRenderer extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
			setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
			return this;
		}
	}
}
