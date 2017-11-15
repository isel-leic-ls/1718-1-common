package pt.isel.ls.html2;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static pt.isel.ls.html2.Dsl.*;

// Non-unsupervised tests :(

public class DslTests {

    private static class Student {
        private final int number;
        private final String name;

        public Student(int number, String name) {

            this.number = number;
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void listTable_works() throws IOException {

        List<Student> students = Arrays.asList(
                new Student(123, "Alice"),
                new Student(456, "Bruno"));

        StringWriter wr = new StringWriter();

        listTable(students, headers("Número", "Nome"),
                s -> Integer.toString(s.getNumber()),
                s -> s.getName())
                .withAttr("border", "1")
                .writeTo(wr);

        System.out.println(wr.toString());
    }

    @Test
    public void detailTable_works() throws IOException {
        Student alice = new Student(123, "Alice");
        StringWriter wr = new StringWriter();

        detailTable(
                prop("Número", Integer.toString(alice.getNumber())),
                prop("Nome", alice.getName()))
                .writeTo(wr);

        System.out.println(wr.toString());
    }

    @Test
    public void list_works() throws IOException {

        List<Student> students = Arrays.asList(
                new Student(123, "Alice"),
                new Student(456, "Bruno"));

        StringWriter wr = new StringWriter();

        list(students, s -> p(t(s.getName()), t(" "), emph(t(Integer.toString(s.getNumber())))))
                .writeTo(wr);

        System.out.println(wr.toString());
    }

}
