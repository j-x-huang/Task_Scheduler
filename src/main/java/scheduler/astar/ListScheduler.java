package scheduler.astar;

import java.util.ArrayDeque;

import scheduler.graphstructures.DefaultDirectedWeightedGraph;
import scheduler.graphstructures.Vertex;

/**
 * ListScheduler finds a possible finish time using task scheduling that's
 * based on assigning tasks to the processor which allows the earliest possible start 
 * after doing a topological sort.
 * 
 * The finish time can be used to set an upper bound for the A* algorithm .
 */
public class ListScheduler {
	private DefaultDirectedWeightedGraph _digraph;
	private int _numberOfProcessors;

	/**
	 * Constructor for ListScheduler
	 * @param digraph digraph to perform topological sort on
	 * @param numberOfProcessors number of processors
	 */
	public ListScheduler(DefaultDirectedWeightedGraph digraph, int numberOfProcessors){
		_digraph = digraph;
		_numberOfProcessors = numberOfProcessors;
	}
	
	/**
	 * Obtain the result of list scheduling
	 * @return possible finish time using task scheduling that's based on a topological sort.
	 */
	public int getResult(){
		// stack of sorted tasks
		ArrayDeque<Vertex> sortedTasks = getTopologicallySortedTasks(); 
		// create an empty solution to be filled with tasks
		Solution solution = new Solution(_numberOfProcessors, _digraph);
		// fill the empty solution in the order of popping off the stack of sorted tasks
		// tasks being added will always have their dependencies ran
		while(!sortedTasks.isEmpty()){
			solution.addProcessAtEarliestPossibleTime(sortedTasks.pollLast());
		}	
		return solution.getLastFinishTime();
	}
	
	/**
	 * Orders and return tasks in a topological order starting from the root task
	 * @return list of task vertices sorted in topological order
	 */
	private ArrayDeque<Vertex> getTopologicallySortedTasks(){
		// queue acting like a stack
		ArrayDeque<Vertex> sortedTasks = new ArrayDeque<>();
		
		// sort all the tasks in the vertex set
		for(Vertex vertex:_digraph.vertexSet()){
			if(!vertex.isVisited()){
				topologicalSort(vertex, sortedTasks);
			}
		}
		return sortedTasks;
	}
	
	/**
	 * Recursive function that inserts task vertices into a queue that's
	 * acting as a stack, as the vertices run out of unvisited neigbours.
	 * 
	 * The topologically sorted tasks can be obtained by popping the queue.
	 * 
	 * @param vertex vertex to process in topological sorting
	 * @param sortedTasks queue to be filled with topologically sorted tasks
	 */
	private void topologicalSort(Vertex vertex, ArrayDeque<Vertex> sortedTasks){
		// task vertex considered
		vertex.setVisited();
		
		// recursively visit unvisited children until no more unvisited children left
		for(Vertex child:_digraph.getDirectChildren(vertex)){
			if(!child.isVisited()){
				topologicalSort(child,sortedTasks);
			}
		}
		
		// add to stack when no more unvisited children left
		sortedTasks.add(vertex);
	}
}
