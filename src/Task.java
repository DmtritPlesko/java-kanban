import java.util.Arrays;
import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    private int idTask;
    protected Status status;


    public Task() {

    }

    public Task(String _name, String _description) {
        this.name = _name;
        this.description = _description;
        this.status = Status.NEW;
        this.idTask = TaskManager.id;

    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description.length ='" + description.length() + '\'' +
                ", idTask=" + idTask +
                ", status=" + status +
                '}';
    }


}
