package scheduler;
import java.util.List;

import io.DataReader;
import io.InputParser;
import io.OutputWriter;
import scheduler.basicmilestone.NodeWeightCalculator;
import scheduler.basicmilestone.Schedule;
import scheduler.basicmilestone.ScheduleGenerator;
import scheduler.basicmilestone.Sorter;
import scheduler.basicmilestone.Vertex;

/**
 * This is the main class for the task scheduler program.
 */
public class Main {

	public static void main(String[] args) {

		OutputWriter outWriter = new OutputWriter();
		InputParser inputParser = new InputParser(args);

		inputParser.parse();

		DataReader dataReader = new DataReader(inputParser.getFile());
		
		while(dataReader.hasMoreGraphs()) {
			System.out.println("More graphs in file? " + dataReader.hasMoreGraphs());
			dataReader.readNextGraph();
			
			//Create the optimal schedule
			NodeWeightCalculator.calculate(dataReader.getGraph());
			List<Vertex> tSort = Sorter.generateSort(dataReader.getGraph());
			Schedule sol = ScheduleGenerator.makeSolution(tSort);
			outWriter.createSchedule(inputParser.getOutputFileName(),dataReader.getGraphName(),dataReader.getInfo(),dataReader.getRead(),sol,dataReader.getMapping());
		}
	}
}
