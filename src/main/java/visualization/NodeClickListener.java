package visualization;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.event.MouseInputListener; 

import org.graphstream.graph.Edge; 
import org.graphstream.graph.Graph; 
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe; 

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

	/**
	 * Constructor 
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
		System.out.println("Button pushed on node "+id); 
		Node n = _graph.getNode(id); 

	} 

	@Override 
	/**
	 * Pump the action on mouse release event
	 */ 
	public void mouseReleased(MouseEvent e) { 

		_viewerPipe.pump(); 

	} 

	/**
	 * Inherited function unused 
	 */ 
	public void buttonReleased(String id) { 
		// TODO Auto-generated method stub 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseClicked(MouseEvent e) { 
		// TODO Auto-generated method stub 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseEntered(MouseEvent e) { 
		// TODO Auto-generated method stub 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseExited(MouseEvent e) { 
		// TODO Auto-generated method stub 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mousePressed(MouseEvent e) { 
		// TODO Auto-generated method stub 
		//_viewerPipe.pump(); 
		//System.out.println("Pump it!"); 
	} 


	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseDragged(MouseEvent arg0) { 
		// TODO Auto-generated method stub 

	} 

	/**
	 * Inherited function unused 
	 */ 
	@Override 
	public void mouseMoved(MouseEvent arg0) { 
		// TODO Auto-generated method stub 

	} 



}