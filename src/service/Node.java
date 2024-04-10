package service;

import com.yandex.practicum.models.Epic;

import java.util.Objects;

public class Node <T> {

        T data;
        Node<T> next;
        Node<T> prev;

        public Node (Node<T> prev,T data,Node<T> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node<T> node = (Node<T>) o;
        return Objects.equals(data, node.data);
    }

}
