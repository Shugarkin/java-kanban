package service;

import model.Tasks;

import java.util.List;


public interface HistoryManager {
    void add(Tasks task);
    void remove(int id);
    List<Tasks> getHistory(); //метод для передачи обычного листа с историей

}