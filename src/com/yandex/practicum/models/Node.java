package com.yandex.practicum.models;
import java.util.Objects;

public class Node <T> {

    T data;
    private Node<T> next;
    private Node<T> prev;

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

    public Node <T> getNodeNext () {return this.next;}

    public Node<T> getNodePrev() {return this.prev;}

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public T getData () {return this.data;}
}
