package service;

import model.Node;
import model.Tasks;

import java.util.HashMap;
import java.util.List;

public interface HistoryManager {
    void add(Tasks task);
    void remove(int id);
    List<Tasks> getHistory(); //метод для передачи обычного листа с историей
    InMemoryHistoryManager.CustomLinkedList<Tasks> getHistoryList(); //метод для передачи кастомного листа с историей
    HashMap<Integer, Node> getHistoryMap(); //метод для передачи мапы с id и node ми
}