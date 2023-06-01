package carFactory.factory.providers;

import java.util.ArrayDeque;

public class TaskQueue {
    private ArrayDeque<Integer> queue;

    public TaskQueue(){
        queue = new ArrayDeque<>();
    }

    public synchronized int getSize(){
        return queue.size();
    }

    public void addTask(int taskId) {
        queue.add(taskId);
    }

    public void getTask() {
        queue.pollFirst();
    }
}
