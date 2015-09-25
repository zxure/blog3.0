package zx.blog.asyncTask;

public class AsyncTask {
	private Runnable task;
	public AsyncTask() {
		super();
	}
	public AsyncTask(Runnable task) {
		super();
		this.task = task;
	}
	public Runnable getTask() {
		return task;
	}
	public void setTask(Runnable task) {
		this.task = task;
	}
}
