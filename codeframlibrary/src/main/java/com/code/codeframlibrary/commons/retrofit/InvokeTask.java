package com.code.codeframlibrary.commons.retrofit;

import android.os.AsyncTask;

public class InvokeTask extends AsyncTask<Void, Void, Object> {

	private Task task;

	public interface Task {
		public Object doing();

		public void backThread(Object data);

		public void mainThread(Object data);
	}

	public InvokeTask(Task dt) {
		task = dt;
		this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		Object data = null;
		if (task != null) {
			data = task.doing();
			task.backThread(data);

		}
		return data;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (task != null) {
			task.mainThread(result);
		}
	}
}
