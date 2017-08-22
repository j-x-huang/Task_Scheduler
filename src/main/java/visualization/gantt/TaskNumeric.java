package visualization.gantt;

import java.util.Date;

import org.jfree.data.gantt.Task;

public class TaskNumeric extends Task {
    private String _desc;
	private long _start;
	private long _end;

	public TaskNumeric(String description, long start, long end) {
        super(description, new Date(start), new Date(end));
        _desc = description;
        _start = start;
        _end = end;
    }

    public static TaskNumeric duration(String description, long start, long duration) {
        return new TaskNumeric(description, start, start + duration);
    }
    
    public String getParam(int param) {
    	if (param == 0) {
    		return _desc;
    	} else if (param == 1) {
    		return Long.toString(_start);
    	} else {
    		return Long.toString(_end);
    	}
    }
}