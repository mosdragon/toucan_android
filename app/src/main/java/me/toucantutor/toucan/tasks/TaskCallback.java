package me.toucantutor.toucan.tasks;

import com.google.gson.JsonObject;

/**
 * Created by osama on 2/19/15.
 */
public interface TaskCallback {

    public void taskSuccess(JsonObject json);

    public void taskFail(JsonObject json);

}
