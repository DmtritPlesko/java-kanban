import java.util.ArrayList;

public class Subtask extends Task {


    protected Integer idSubtask;

    public Subtask() {

    }

    public Subtask (String _name,String _description) {
        super(_name,_description);
        TaskManager.countSubtask++;
        this.idSubtask = TaskManager.countSubtask;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "idSubtask=" + idSubtask +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
