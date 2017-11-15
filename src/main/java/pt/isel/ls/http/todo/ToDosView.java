package pt.isel.ls.http.todo;

import pt.isel.ls.common.Writable;
import pt.isel.ls.html.HtmlElem;
import pt.isel.ls.html.HtmlPage;
import pt.isel.ls.todo.ToDo;

public class ToDosView extends HtmlPage {

    public ToDosView(Iterable<ToDo> list) {
        super("To Do list",
                h1(text("To Do list")),
                todoItems(list),
                h1(text("Create new To Do")),
                form("POST", "/todos",
                        label("description", "Description: "),
                        textInput("description"),
                        submit()
                )
        );
    }

    private static HtmlElem submit() {
        return new HtmlElem("input")
                .withAttr("type", "submit")
                .withAttr("value", "create");
    }

    private static Writable todoItems(Iterable<ToDo> list) {
        HtmlElem ul = new HtmlElem("ul");
        for (ToDo todo : list) {
            ul.withContent(
                    li(a(ResolveUrl.of(todo), todo.toString()))
            );
        }
        return ul;
    }
}
