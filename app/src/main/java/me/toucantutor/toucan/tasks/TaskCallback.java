package me.toucantutor.toucan.tasks;

/**
 * Created by osama on 2/19/15.
 */
public interface TaskCallback {

    public void taskSuccess(Object ... input);

    public void taskFail(Object ... input);
}
