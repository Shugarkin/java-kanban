package service;

import model.Tasks;
import org.w3c.dom.Node;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, nodes> historyMap = new HashMap<>(); //мапа для id задач с их nodes
    private CustomLinkedList<Tasks> historyList = new CustomLinkedList<>(); //кастомный лист для хранения истории

    @Override
    public List<Tasks> getHistory() { //метод для истории в ArrayListе
        return historyList.getTasks();
    }


    @Override
    public void add(Tasks task) { //метод для добавления задач в кастомный лист с учетом того, что в нем не было такой же задачи
        if(historyMap.containsKey(task.getId())) {
            historyList.removeNodes(historyMap.get(task.getId()));
        }
        historyList.linkLast(task);
        historyMap.put(task.getId(), historyList.getLast());
    }
    @Override
    public void remove(int id) { //метод для удаления задач из листа и мапы
        historyList.removeNodes(historyMap.remove(id));
    }


    private static class CustomLinkedList<T>  { //кастомный лист

        public nodes<T> head;
        public nodes<T> tail;

        public void linkLast(T task) { //метод добавления в лист хвоста
            final nodes<T> oldTail = tail;
            final nodes<T> newnodes = new nodes<>(oldTail, task, null);
            tail = newnodes;
            if (oldTail == null) {
                head = newnodes;
            } else {
                oldTail.next = newnodes;
            }
        }

        public List<Tasks> getTasks() { //метод для передачи значений из кастомного листа в обычный
            List<Tasks> arrayHistoryList = new ArrayList<>();
            nodes<Tasks> current = (nodes<Tasks>) head;
            while(current != null) {
                arrayHistoryList.add(current.data);
                current = current.next;
            }
            return arrayHistoryList;
        }

        public void removeNodes(nodes<T> nodes) { //метод удаления nodes из кастомного списка
            if(nodes != null) {
                final nodes<T> next = nodes.next;
                final nodes<T> prev = nodes.prev;

                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                    nodes.prev = null;
                }

                if (next == null) {
                    tail = prev;
                } else {
                    next.prev = prev;
                    nodes.next = null;
                }
                nodes.data = null;
            }
        }

        public nodes<T> getLast() { //метод который передает последнее значение в листе
            return tail;
        }
    }

    static private class nodes <T> {

        private T data;
        private nodes<T> next;
        private nodes<T> prev;

        public nodes(nodes<T> prev, T data, nodes<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}


