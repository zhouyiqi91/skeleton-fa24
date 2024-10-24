import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns true if the map contains the given key. */
    @Override
    public boolean containsKey(K key) {
        return getNode(root, key) != null;
    }

    /** Returns the value associated with the given key. */
    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    /** Returns the node with the specified key, or null if it doesn't exist. */
    private Node getNode(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return getNode(x.left, key);
        } else if (cmp > 0) {
            return getNode(x.right, key);
        } else {
            return x;
        }
    }

    /** Associates the specified value with the specified key. */
    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        } else if (cmp > 0) {
            x.right = put(x.right, key, value);
        } else {
            x.value = value;
        }
        return x;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        keySet(root, keySet);
        return keySet;
    }

    private void keySet(Node x, Set<K> set) {
        if (x == null) {
            return;
        }
        set.add(x.key);
        keySet(x.left, set);
        keySet(x.right, set);
    }

    /** Removes the mapping for the specified key. */
    @Override
    public V remove(K key) {
        Node removedNode = getNode(root, key);
        if (removedNode == null) {
            return null;
        }
        root = remove(root, key);
        size--;
        return removedNode.value;
    }

    private Node remove(Node x, K key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            // Case with one or no children
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;

            // Case with two children: find the smallest node in the right subtree
            Node t = x;
            x = min(t.right);
            x.right = removeMin(t.right);
            x.left = t.left;
        }
        return x;
    }

    /** Finds the smallest node in the subtree rooted at x. */
    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    /** Removes the smallest node in the subtree rooted at x. */
    private Node removeMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = removeMin(x.left);
        return x;
    }


    /** Returns an iterator over the keys of the map. */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private Set<K> keys;
        private Iterator<K> iterator;

        public BSTMapIterator() {
            keys = keySet();
            iterator = keys.iterator();
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iterator.next();
        }
    }
}
