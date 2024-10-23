public class UnionFind {
    // TODO: Instance variables
    private int[] parent;
    private int[] size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        parent = new int[N];
        size = new int[N];
        for (int i=0;i<N;i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return size[v];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return parent[v1] == parent[v2];
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        rangeCheck(v);
        if (parent[v] != v) {
            parent[v] = find(parent[v]);
        }
        return parent[v];
    }

    private void rangeCheck(int v) {
        if (v<0 || v>= parent.length) {
            throw new IllegalArgumentException("out of range!");
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        rangeCheck(v1);
        rangeCheck(v2);
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1==p2) {
            return;
        }
        if (size[p1] <= size[p2]) {
            parent[p1] = p2;
            size[p2] += size[p1];
        } else {
            parent[p2] = p1;
            size[p1] += size[p2];
        }
    }

}
