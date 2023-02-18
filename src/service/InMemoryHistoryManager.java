package service;

import model.Tasks;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, Node<Tasks>> nodes = new HashMap<>(); //мапа для id задач с их Node
    private CustomLinkedList<Tasks> historyList = new CustomLinkedList<>(); //кастомный лист для хранения истории

    @Override
    public List<Tasks> getHistory() { //метод для истории в ArrayListе
        return historyList.getTasks();
    }


    @Override
    public void add(Tasks task) { //метод для добавления задач в кастомный лист с учетом того, что в нем не было такой же задачи
        if (task == null) {
            return;
        }
        if(nodes.containsKey(task.getId())) {
            historyList.removeNode(nodes.get(task.getId()));
        }
        historyList.linkLast(task);
        nodes.put(task.getId(), historyList.getLast());
    }
    @Override
    public void remove(int id) { //метод для удаления задач из листа и мапы
        historyList.removeNode(nodes.remove(id));
    }


    private static class CustomLinkedList<T>  { //кастомный лист

        public Node<T> head;
        public Node<T> tail;

        public void linkLast(T task) { //метод добавления в лист хвоста
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, task, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.next = newNode;
            }
        }

        public List<T> getTasks() { //метод для передачи значений из кастомного листа в обычный
            List<T> arrayHistoryList = new ArrayList<>();
            Node<T> current = head;
            while(current != null) {
                arrayHistoryList.add(current.data);
                current = current.next;
            }
            return arrayHistoryList;
        }

        public void removeNode(Node<T> Node) { //метод удаления Node из кастомного списка
            if(Node != null) {
                final Node<T> next = Node.next;
                final Node<T> prev = Node.prev;

                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                    Node.prev = null;
                }

                if (next == null) {
                    tail = prev;
                } else {
                    next.prev = prev;
                    Node.next = null;
                }
                Node.data = null;
            }
        }

        public Node<T> getLast() { //метод который передает последнее значение в листе
            return tail;
        }
    }

    static private class Node <T> {

        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}


