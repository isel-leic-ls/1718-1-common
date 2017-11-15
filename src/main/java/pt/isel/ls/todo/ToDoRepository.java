package pt.isel.ls.todo;

public interface ToDoRepository {
    Iterable<ToDo> getAll();

    ToDo getById(int id);

    void add(ToDo t);

    boolean remove(int id);
}