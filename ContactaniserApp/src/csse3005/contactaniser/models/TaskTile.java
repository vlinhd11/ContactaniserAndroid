package csse3005.contactaniser.models;

public class TaskTile {
	private int type;
	private String text;
	private int color;
	private long taskID;
	
	public TaskTile(long taskID, int color, int type, String text) {
		super();
		this.type = type;
		this.text = text;
		this.color = color;
		this.taskID = taskID;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public long getTaskID() {
		return taskID;
	}
	public void setTaskID(long taskID) {
		this.taskID = taskID;
	}
}
