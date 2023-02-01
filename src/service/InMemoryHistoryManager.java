package service;

import model.Tasks;
import model.Node;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    public HashMap<Integer, Node> historyMap = new HashMap<>(); //мапа для id задач с их node
    public CustomLinkedList<Tasks> historyList = new CustomLinkedList<>(); //кастомный лист для хранения истории

    @Override
    public List<Tasks> getHistory() { //метод для истории в ArrayListе
        List<Tasks> arrayHistoryList = new ArrayList<>(historyList.getTasks());
        return arrayHistoryList;
    }


    @Override
    public void add(Tasks task) { //метод для добавления задач в кастомный лист с учетом того, что в нем не было такой же задачи
        if (!historyList.contains(task)) {
            historyList.linkLast(task);
            historyMap.put(task.getId(), historyList.getLast());
        } else {
            historyList.removeNode(historyMap.get(task.getId()));
            historyList.linkLast(task);
            historyMap.put(task.getId(), historyList.getLast());
        }
    }
    @Override
    public void remove(int id) { //метод для удаления задач из листа и мапы
        historyList.remove(id);
        historyMap.remove(id);
    }
    @Override
    public CustomLinkedList<Tasks> getHistoryList() { //геттер для передачи листа
        return historyList;
    }
    @Override
    public HashMap<Integer, Node> getHistoryMap() { //геттер для передачи мапы
        return historyMap;
    }

    public class CustomLinkedList<T>  { //кастомный лист

        public Node<T> head;
        public Node<T> tail;
        private int size = 0;

        public boolean contains(Object o) { //метод для проверки на существующую задачу в листе
            return indexOf(o) != -1;
        }

        public int indexOf(Object o) { //честно говоря найденный в интернете метод для работы contains()
            int index = 0;
            if (o == null) {
                for (Node<T> x = head; x != null; x = x.next) {
                    if (x.data == null)
                        return index;
                    index++;
                }
            } else {
                for (Node<T> x = head; x != null; x = x.next) {
                    if (o.equals(x.data))
                        return index;
                    index++;
                }
            }
            return -1;
        }

        public void linkLast(T task) { //метод добавления в лист хвоста
            final Node<T> l = tail;
            final Node<T> newNode = new Node<>(l, task, null);
            tail = newNode;
            if (l == null) {
                head = newNode;
            } else {
                l.next = newNode;
            }
            size++;
        }

        public List getTasks() { //метод для передачи значений из кастомного листа в обычный
            List<Tasks> arrayHistoryList = new ArrayList<>();
            for(int i = 0; i < historyList.size(); i++) {
                arrayHistoryList.add(historyList.get(i));
            }
            return arrayHistoryList;
        }

        public T get(int index) {//метод для передачи значения из кастомного листа
            return node(index).data;
        }

        public T removeNode(Node<T> node) { //метод удаления Node из кастомного списка
            if(node != null) {
                final T data = node.data;
                final Node<T> next = node.next;
                final Node<T> prev = node.prev;

                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                    node.prev = null;
                }

                if (next == null) {
                    tail = prev;
                } else {
                    next.prev = prev;
                    node.next = null;
                }

                node.data = null;
                size--;
                return data;
            }
            return null;
        }

        public T remove(int id) { //метод через который удаляется значение из листа
            return (T) removeNode(historyMap.get(id));
        }

        Node<T> node(int index) { //метод для передачи Node с указанным индексом
            if (index < (size >> 1)) {
                Node<T> x = head;
                for (int i = 0; i < index; i++)
                    x = x.next;
                return x;
            } else {
                Node<T> x = tail;
                for (int i = size - 1; i > index; i--)
                    x = x.prev;
                return x;
            }
        }

        public int size() { //метод для передачи размера листа
            return this.size;
        }

        public Node<T> getLast() { //метод который передает последнее значение в листе
            final Node<T> l = tail;
            return l;
        }
    }
}
