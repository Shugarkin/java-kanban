package Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void before() {
        setTaskManager(new InMemoryTaskManager());
    }


    @Test
    void addTaskTest() {
        super.addTaskTest();
    }

    @Test
    void addEpicTest() {
        super.addEpicTest();
    }

    @Test
    void addSubTaskTest() {
        super.addSubTaskTest();
    }

    @Test
    public void printAllTaskTest() {
        super.printAllTaskTest();
    }

    @Test
    public void printAllEpicTest() {
        super.printAllEpicTest();
    }

    @Test
    public void printAllSubtaskTest() {
        super.printAllSubtaskTest();
    }

    @Test
    public void printTaskTest() {
        super.printTaskTest();
    }

    @Test
    public void printEpicTest() {
        super.printEpicTest();
    }

    @Test
    public void printSubTaskTest() {
        super.printSubTaskTest();
    }

    @Test
    public void deleteTaskTest() {
        super.deleteTaskTest();
    }

    @Test
    public void deleteEpicTest() {
        super.deleteEpicTest();
    }

    @Test
    public void deleteSubtaskTest() {
        super.deleteSubtaskTest();
    }

    @Test
    public void deleteTaskForIdTest() {
        super.deleteTaskForIdTest();
    }

    @Test
    public void deleteEpicForIdTest() {
        super.deleteEpicForIdTest();
    }

    @Test
    public void deleteSubtaskForIdTest() {
        super.deleteSubtaskForIdTest();
    }

    @Test
    public void newTaskTest() {
        super.newTaskTest();
    }

    @Test
    public void newEpicTest() {
        super.newEpicTest();
    }

    @Test
    public void newSubtaskTest()  {
        super.newSubtaskTest();
    }
}