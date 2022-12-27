package service;

import model.Tasks;

import java.util.ArrayList;

public interface HistoryManager {
    void add(Tasks task);

    ArrayList<Tasks> getHistory();
    @Override
    String toString();

}

