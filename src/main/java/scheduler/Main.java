package scheduler;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import io.DataReader;
import io.InputParser;
import io.InputParserException;
import io.OutputWriter;
import scheduler.astar.AStar;
import scheduler.astar.AStarParallelised;
import scheduler.astar.Solution;
import visualization.gantt.Gantt;
import visualization.graph.Visualizer;
import visualization.gui.Gui;
import visualization.gui.StatisticTable;


/**
 * This is the main class for the task scheduler program.
 */
public class Main {
	
	public static long _startTime;

	public static void main(String[] args) {
		
		// 1. Instantiate InputParser to parse the command line arguments.
		InputParser inputParser = new InputParser(args);
		try {
			inputParser.parse();
		} catch (InputParserException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		// 2. Instantiate OutputWriter to write to the output file.
		OutputWriter outWriter = new OutputWriter(inputParser.getOutputFileName());
		outWriter.initialise();

		// 3. Instantiate DataReader to read the input file
		DataReader dataReader = new DataReader(inputParser.getFile());
		
		// 4. Declare the visualisers
		Visualizer graphVisualizer = null;
		Gantt gantt = null;
		StatisticTable stats = null;
		
		// 5. Find optimal solution for all the input task graphs.
		while(dataReader.hasMoreGraphs()) {
			dataReader.readNextGraph();
			
			if(inputParser.isVisualise() == true){
				graphVisualizer = new Visualizer();
				graphVisualizer.add(dataReader.getGraph());

				graphVisualizer.displayGraph();
				gantt = new Gantt("");
				stats = new StatisticTable(inputParser.getCores(),inputParser.getProcessors());
				final Visualizer graphVisualizer2 = graphVisualizer;
				final Gantt gant2 = gantt;
				final StatisticTable stats2 = stats;
				final int numProc = inputParser.getProcessors();
				//graphVisualizer.displayGraph();
				try {
					 // Set cross-platform Java L&F (also called "Metal")
			        UIManager.setLookAndFeel(
			            UIManager.getCrossPlatformLookAndFeelClassName());
					SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							
							Gui window = new Gui(graphVisualizer2,gant2,stats2,numProc);
							window._frame.setVisible(true);
							graphVisualizer2.setGuiListener(window);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				});
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// start timing
			long startTime = System.nanoTime();

			// decide whether to parallelise A* based on user input
			AStar aStar;
			if(inputParser.isParallelise() && inputParser.getCores() > 1){
				aStar = new AStarParallelised(dataReader.getGraph(), inputParser.getProcessors(), inputParser.getCores(), graphVisualizer, gantt, stats);
			} else {
				aStar = new AStar(dataReader.getGraph(),inputParser.getProcessors(), graphVisualizer, gantt, stats);
			}

			_startTime = System.nanoTime();
			
			// write the optimal schedule to the output file
			Solution optimalSolution = aStar.execute();
			outWriter.createScheduleAStar(dataReader.getGraphName(),dataReader.getVerticesAndEdgesInfo(),dataReader.getVerticesAndEdgesRead(),optimalSolution,dataReader.getMapping());
			long endTime = System.nanoTime();
			long totalTime = endTime - startTime;
			
			System.out.println("\n Took " + totalTime/1000000 + "ms" + " : " + totalTime/1000000000 + " seconds");
			
			if(graphVisualizer != null){
				gantt.updateSolution(optimalSolution);
				stats.updateStats(aStar.getSolCreated(), aStar.getSolPopped(), aStar.getSolPruned(), optimalSolution.getLastFinishTime());
				DefaultTableModel model = (DefaultTableModel)stats.getTable().getModel();
				model.setValueAt("Optimal Finish Time", 7, 0);
				graphVisualizer.updateGraph(optimalSolution);
				JOptionPane.showMessageDialog(null,
					    "Scheduling complete!");
			}
			
		}
	}
}
