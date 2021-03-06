package visualization.graph;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.MouseInputListener;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import visualization.gui.Gui; 

/**
 * Listener to handle click of nodes. 
 * Instead of using a tight loop to pump mouse click events, a signal based approach is used instead.
 * Prints out the name of the node upon click event on a specific node.
 * 
 */ 
public class NodeClickListener implements ViewerListener , MouseInputListener{  
	private ViewerPipe _viewerPipe = null; 
	private View _view = null; 
	private Graph _graph = null; 
	private HashMap<String, List<Object>> _scheduledVertices = null;
	protected Gui _gui;

	/**
	 *  Constructor with no parameters
	 */
	public NodeClickListener(){

	}

	/**
	 * Constructor with three parameters
	 * @param _viewerPipe Viewer Pipe of the graph UI 
	 * @param _view View of the current graph
	 * @param _graph The current Graph displayed/ in used
	 */ 
	public NodeClickListener(ViewerPipe viewerPipe, View view, Graph graph) { 
		this._viewerPipe = viewerPipe; 
		this._view = view; 
		this._graph = graph; 
		this._view.addMouseListener(this); 

	} 

	/**
	 * Detach all listeners if the graph is closed
	 * @param id Unused parameter inherited from the interface
	 */ 
	public void viewClosed(String id) { 
		_view.removeMouseListener(this); 

	} 

	/**
	 * Displays the node/task information once clicked
	 * @param id Name of the node
	 */ 
	public void buttonPushed(String id) { 

		//Prints out the node name
		Node n = _graph.getNode(id); 

		if(_scheduledVertices == null){

		} else if(n.getAttribute("ui.style").toString().contains("fill-color:#000000;")){

			_gui.noInfoToShow(id);

		} else {

			List<Object> scheduleInfo = _scheduledVertices.get(id);
			_gui.showInfoOnTextArea(id,(int) scheduleInfo.get(0),scheduleInfo.get(1),scheduleInfo.get(2));

		}
	} 

	@Override 
	/**
	 * Pump the action on mouse release event
	 */ 
	public void mouseReleased(MouseEvent e) { 

		_viewerPipe.pump(); 

	} 

	/**
	 * Method to set the current solution
	 * @param scheduledVertices
	 */
	public void setCurrentSolution(HashMap<String, List<Object>> scheduledVertices) { 
		_scheduledVertices = scheduledVertices;
	} 

	/**
	 * Inherited function unused 
	 */ 
	public void buttonReleased(String id) { 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseClicked(MouseEvent e) { 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseEntered(MouseEvent e) { 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseExited(MouseEvent e) { 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mousePressed(MouseEvent e) { 

	} 


	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseDragged(MouseEvent arg0) { 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseMoved(MouseEvent arg0) { 
	} 



}
