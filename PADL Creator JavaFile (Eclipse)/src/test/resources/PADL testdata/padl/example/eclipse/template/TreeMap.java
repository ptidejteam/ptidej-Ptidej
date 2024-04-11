package java.util;
/** 
 * Implements a TreeMap using a red-black tree. This guarantees O(log n)
 * performance on lookups, inserts, and deletes while maintaining linear
 * in-order traversal time. Null keys and values are fully supported if the
 * comparator supports them (the default comparator does not).
 * @param<K>
 *  key type
 * @param<V>
 *  value type
 */
public class TreeMap<K,V> extends AbstractMap<K,V> implements SortedMap<K,V> {
  /** 
 * Iterator for <code>EntrySet</code>.
 */
private final class EntryIterator implements Iterator<Entry<K,V>> {
    private final Iterator<Map.Entry<K,V>> iter;
    private Map.Entry<K,V> last=null;
    /** 
 * Constructor for <code>EntrySetIterator</code>.
 */
    public EntryIterator(){
      this(SubMapType.All,null,null);
    }
    /** 
 * Create an iterator which may return only a restricted range.
 * @param fromKey the first key to return in the iterator.
 * @param toKey the upper bound of keys to return.
 */
    public EntryIterator(    SubMapType type,    K fromKey,    K toKey){
      List<Map.Entry<K,V>> list=new ArrayList<Map.Entry<K,V>>();
      inOrderAdd(list,type,TreeMap.this.root,fromKey,toKey);
      this.iter=list.iterator();
    }
    public boolean hasNext(){
      return iter.hasNext();
    }
    public Map.Entry<K,V> next(){
      return last=iter.next();
    }
    public void remove(){
      iter.remove();
      TreeMap.this.remove(last.getKey());
    }
    private void inOrderAdd(    List<Map.Entry<K,V>> list,    SubMapType type,    Node<K,V> current,    K fromKey,    K toKey){
      if (current == null) {
        return;
      }
      if (current.child[LEFT] != null) {
        inOrderAdd(list,type,current.child[LEFT],fromKey,toKey);
      }
      if (inRange(type,current.getKey(),fromKey,toKey)) {
        list.add(current);
      }
      if (current.child[RIGHT] != null) {
        inOrderAdd(list,type,current.child[RIGHT],fromKey,toKey);
      }
    }
    private boolean inRange(    SubMapType type,    K key,    K fromKey,    K toKey){
      if (type.toKeyValid()) {
        if (cmp.compare(key,toKey) >= 0) {
          return false;
        }
      }
      if (type.fromKeyValid()) {
        if (cmp.compare(key,fromKey) < 0) {
          return false;
        }
      }
      return true;
    }
  }
private final class EntrySet extends AbstractSet<Entry<K,V>> {
    @Override public void clear(){
      TreeMap.this.clear();
    }
    @SuppressWarnings("unchecked") @Override public boolean contains(    Object o){
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry<K,V> entry=(Entry<K,V>)o;
      Entry<K,V> lookupEntry=getEntry(entry.getKey());
      return lookupEntry != null && Utility.equalsWithNullCheck(lookupEntry.getValue(),entry.getValue());
    }
    @Override public Iterator<Entry<K,V>> iterator(){
      return new EntryIterator();
    }
    @SuppressWarnings("unchecked") @Override public boolean remove(    Object o){
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry<K,V> entry=(Map.Entry<K,V>)o;
      State<V> state=new State<V>();
      state.matchValue=true;
      state.value=entry.getValue();
      return removeWithState(entry.getKey(),state);
    }
    @Override public int size(){
      return TreeMap.this.size();
    }
  }
  /** 
 * Tree node.
 * @param<K>
 *  key type
 * @param<V>
 *  value type
 */
private static class Node<K,V> implements Entry<K,V> {
    protected Node<K,V>[] child;
    protected boolean isRed;
    protected K key;
    protected V value;
    /** 
 * Create a red node.
 * @param key
 * @param value
 */
    public Node(    K key,    V value){
      this(key,value,true);
    }
    /** 
 * Create a node of the specified color.
 * @param key
 * @param value
 * @param isRed true if this should be a red node, false for black
 */
    @SuppressWarnings("unchecked") public Node(    K key,    V value,    boolean isRed){
      this.key=key;
      this.value=value;
      child=new Node[2];
      this.isRed=isRed;
    }
    @SuppressWarnings("unchecked") @Override public boolean equals(    Object o){
      if (!(o instanceof Node)) {
        return false;
      }
      Node<K,V> other=(Node<K,V>)o;
      return Utility.equalsWithNullCheck(key,other.key) && Utility.equalsWithNullCheck(value,other.value);
    }
    public K getKey(){
      return key;
    }
    public V getValue(){
      return value;
    }
    @Override public int hashCode(){
      int keyHash=(key != null ? key.hashCode() : 0);
      int valueHash=(value != null ? value.hashCode() : 0);
      return keyHash ^ valueHash;
    }
    public V setValue(    V value){
      V old=this.value;
      this.value=value;
      return old;
    }
    @Override public String toString(){
      return (isRed ? "R: " : "B: ") + key + "="+ value;
    }
  }
  /** 
 * A state object which is passed down the tree for both insert and remove.
 * All uses make use of the done flag to indicate when no further rebalancing
 * of the tree is required. Remove methods use the found flag to indicate when
 * the desired key has been found. value is used both to return the value of a
 * removed node as well as to pass in a value which must match (used for
 * entrySet().remove(entry)), and the matchValue flag is used to request this
 * behavior.
 * @param<V>
 *  value type
 */
private static class State<V> {
    public boolean done;
    public boolean found;
    public boolean matchValue;
    public V value;
    @Override public String toString(){
      return "State: mv=" + matchValue + " value="+ value+ " done="+ done+ " found="+ found;
    }
  }
private class SubMap extends AbstractMap<K,V> implements SortedMap<K,V> {
    public final K fromKey;
    public final K toKey;
    public final SubMapType type;
    SubMap(    SubMapType type,    K fromKey,    K toKey){
switch (type) {
case Range:
        if (cmp.compare(toKey,fromKey) < 0) {
          throw new IllegalArgumentException("subMap: " + toKey + " less than "+ fromKey);
        }
      break;
case Head:
    cmp.compare(toKey,toKey);
  break;
case Tail:
cmp.compare(fromKey,fromKey);
break;
case All:
break;
}
this.type=type;
this.fromKey=fromKey;
this.toKey=toKey;
}
public Comparator<? super K> comparator(){
return TreeMap.this.comparator();
}
@SuppressWarnings("unchecked") @Override public boolean containsKey(Object k){
K key=(K)k;
if (!inRange(key)) {
return false;
}
return TreeMap.this.containsKey(k);
}
@Override public Set<java.util.Map.Entry<K,V>> entrySet(){
return new AbstractSet<Entry<K,V>>(){
@SuppressWarnings("unchecked") @Override public boolean contains(Object o){
if (!(o instanceof Map.Entry)) {
return false;
}
Map.Entry<K,V> entry=(Entry<K,V>)o;
K key=entry.getKey();
if (!inRange(key)) {
return false;
}
Entry<K,V> lookupEntry=getEntry(key);
return lookupEntry != null && Utility.equalsWithNullCheck(lookupEntry.getValue(),entry.getValue());
}
@Override public boolean isEmpty(){
return SubMap.this.isEmpty();
}
@Override public Iterator<Entry<K,V>> iterator(){
return new EntryIterator(type,fromKey,toKey);
}
@SuppressWarnings("unchecked") @Override public boolean remove(Object o){
if (!(o instanceof Map.Entry)) {
return false;
}
Map.Entry<K,V> entry=(Map.Entry<K,V>)o;
if (!inRange(entry.getKey())) {
return false;
}
State<V> state=new State<V>();
state.matchValue=true;
state.value=entry.getValue();
return removeWithState(entry.getKey(),state);
}
@Override public int size(){
int n=0;
Iterator<Entry<K,V>> it=iterator();
while (it.hasNext()) {
it.next();
n++;
}
return n;
}
}
;
}
public K firstKey(){
Node<K,V> node=throwNSE(getFirstSubmapNode());
if (type.toKeyValid() && cmp.compare(node.key,toKey) > 0) {
throw new NoSuchElementException();
}
return node.key;
}
@SuppressWarnings("unchecked") @Override public V get(Object k){
K key=(K)k;
if (!inRange(key)) {
return null;
}
return TreeMap.this.get(key);
}
public SortedMap<K,V> headMap(K toKey){
if (type.toKeyValid() && cmp.compare(toKey,this.toKey) > 0) {
throw new IllegalArgumentException("subMap: " + toKey + " greater than "+ this.toKey);
}
if (type.fromKeyValid()) {
return TreeMap.this.subMap(fromKey,toKey);
}
 else {
return TreeMap.this.headMap(toKey);
}
}
@Override public boolean isEmpty(){
return getFirstSubmapNode() == null;
}
public K lastKey(){
Node<K,V> node=throwNSE(getLastSubmapNode());
if (type.fromKeyValid() && cmp.compare(node.key,fromKey) < 0) {
throw new NoSuchElementException();
}
return node.key;
}
@Override public V put(K key,V value){
if (!inRange(key)) {
throw new IllegalArgumentException(key + " outside the range " + fromKey+ " to "+ toKey);
}
return TreeMap.this.put(key,value);
}
@SuppressWarnings("unchecked") @Override public V remove(Object k){
K key=(K)k;
if (!inRange(key)) {
return null;
}
return TreeMap.this.remove(key);
}
public SortedMap<K,V> subMap(K newFromKey,K newToKey){
if (type.fromKeyValid() && cmp.compare(newFromKey,fromKey) < 0) {
throw new IllegalArgumentException("subMap: " + newFromKey + " less than "+ fromKey);
}
if (type.toKeyValid() && cmp.compare(newToKey,toKey) > 0) {
throw new IllegalArgumentException("subMap: " + newToKey + " greater than "+ toKey);
}
return TreeMap.this.subMap(newFromKey,newToKey);
}
public SortedMap<K,V> tailMap(K fromKey){
if (type.fromKeyValid() && cmp.compare(fromKey,this.fromKey) < 0) {
throw new IllegalArgumentException("subMap: " + fromKey + " less than "+ this.fromKey);
}
if (type.toKeyValid()) {
return TreeMap.this.subMap(fromKey,toKey);
}
 else {
return TreeMap.this.tailMap(fromKey);
}
}
private Node<K,V> getFirstSubmapNode(){
Node<K,V> node;
if (type.fromKeyValid()) {
node=getNodeAtOrAfter(fromKey);
}
 else {
node=getFirstNode();
}
return node;
}
private Node<K,V> getLastSubmapNode(){
Node<K,V> node;
if (type.toKeyValid()) {
node=getNodeBefore(toKey);
}
 else {
node=getLastNode();
}
return node;
}
private boolean inRange(K key){
if (type.toKeyValid()) {
if (cmp.compare(key,toKey) >= 0) {
return false;
}
}
if (type.fromKeyValid()) {
if (cmp.compar...