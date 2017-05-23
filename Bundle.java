package co.cardseed.cardseed.components.heap;

class Bundle<E> extends Node<E> {

    private Node<Object> first;
    private Node<Object> last;
    private Node pointer;
    private int autoIndex;

    Bundle() {
        pointer = first = last = null;
        autoIndex = 0;
    }

    public void createList(Bundle bndl) {
        setFirst(bndl.getFirst());
        setLast(bndl.getLast());
    }

    protected void add(E value) {
        Node<Object> newNode = new Node<>();
        newNode.setValue(value);
        newNode.setIndex(autoIndex);
        autoIndex++;
        if (first == null) {
            first = last = newNode;
        } else {
            last.setRightNode(newNode);
            newNode.setLeftNode(last);
            newNode.setRightNode(null);
            last = newNode;
        }
    }

    protected void add(E index, E value) {
        Node<Object> newNode = new Node<>();
        newNode.setValue(value);
        newNode.setIndex(index);
        if (first == null) {
            first = last = newNode;
        } else {
            last.setRightNode(newNode);
            newNode.setLeftNode(last);
            newNode.setRightNode(null);
            last = newNode;
        }
    }

    protected boolean move() {
        if (pointer == null) {
            pointer = first;
        } else {
            pointer = pointer.rightNode();
        }
        return pointer != null;
    }

    protected E getValue() {
        return (E) pointer.getValue();
    }

    protected E getIndex() {
        return (E) pointer.getIndex();
    }

    protected void setPointer(Node pntr) {
        pointer = pntr;
    }

    protected Node getPointer() {
        return pointer;
    }

    protected void restart() {
        pointer = first;
    }

    protected Node getFirst() {
        return first;
    }

    protected Node getLast() {
        return last;
    }

    protected void setFirst(Node first) {
        this.first = first;
    }

    protected void setLast(Node last) {
        this.last = last;
    }
}
