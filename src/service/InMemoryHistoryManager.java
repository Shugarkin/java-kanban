package service;

import model.Tasks;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {




    ArrayList<Tasks> historyList = new ArrayList<>();



    @Override
    public ArrayList<Tasks> getHistory() { //метод для истории
        return historyList;
    }


    @Override
    public void add(Tasks task) {
        historyList.add(task);
        if(historyList.size() > 10) {
            historyList.remove(0);
        }
    }
}


