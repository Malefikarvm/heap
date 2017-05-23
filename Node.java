package co.cardseed.cardseed.components.heap;

/**
 *
 * @author Analista
 * @param <E>
 */
class Node<E> {

    private Node right;
    private E value;
    private E index;
    private Node left;

    Node(){
        index = null;
        value = null;
        right=left=null;
    }

    //SET METHODS

    /**
     * Set the value of an specific item into a node on a list.
     *
     * @param v as a value of any type
     */
    protected void setValue(E v) { value = v; }

    /**
     * Set the index of a node
     *
     * @param i as the index of the node
     */
    protected void setIndex(E i) { index = i; }

    /**
     * Set the pointer of right node
     *
     * @param r as the direction of the right node
     */
    protected void setRightNode(Node r) { right = r; }

    /**
     * Set the pointer of left node
     *
     * @param l as the direction of the right node
     */
    protected void setLeftNode(Node l) { left = l; }

    //GET METHODS

    /**
     * Method to know the value of an item
     *
     * @return The value of a specific item
     */
    protected E getValue() { return value; }

    /**
     * Obtains the index of the node
     *
     * @return the index of the node
     */
    protected E getIndex() { return index; }

    /**
     * This method return the right node pointer of a specific item
     *
     * @return The right node pointer
     */
    protected Node rightNode() { return right; }

    /**
     * This method return the left node pointer of a specific item
     *
     * @return The left node pointer
     */
    protected Node leftNode() { return left; }

    /**
     * This method shows the type of a specific item
     *
     * @return The left node pointer
     */
    protected String typeOf() {
        String valueOf = "";
        if(value instanceof Integer){
            valueOf =  "Integer";
        }else if(value instanceof Float){
            valueOf =  "Float";
        }else if(value instanceof Double){
            valueOf =  "Double";
        }else if(value instanceof Character){
            valueOf =  "Character";
        }else if(value instanceof Boolean){
            valueOf =  "Boolean";
        }else if(value instanceof Short){
            valueOf =  "Short";
        }else if(value instanceof Long){
            valueOf =  "Long";
        }else if(value instanceof Byte){
            valueOf =  "Byte";
        }else if(value instanceof String){
            valueOf =  "String";
        }else{
            valueOf =  value.getClass().toString();
        }

        return valueOf;
    }
}
