// Simulation der benötigten Interfaces, damit der Code kompiliert

interface HashTable {
    boolean isFree(int index);
    int get(int index);
    void insert(int key, int index);
    void replace(int key, int index);
}

interface Probe {
    int evaluate(int key, int i);
}

interface HashTableWithChaining {
    boolean containsNoChainElement(int index);
    void insertChainElement(ChainElement element, int index);
    ChainElement get(int index);
    void replaceChainElement(ChainElement element, int index);
}

class ChainElement {
    private int key;
    private ChainElement next;

    public ChainElement(int key) { this.key = key; }
    public int getKey() { return key; }
    public void setNext(ChainElement next) { this.next = next; }
    public ChainElement getNext() { return next; }
}