package com.example.controls.tda.list;
import java.util.Comparator;

import com.example.controls.exception.ListEmptyException;


public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last; 
    private Integer size; 

    public LinkedList() {
        this.header = null; 
        this.last = null; 
        this.size = 0; 
    }

    public Boolean isEmpty() {
        return (this.header == null || this.size == 0);
    }

    private void addHeader(E dato) {
        Node<E> help;
        if (isEmpty()) {
            help = new Node<>(dato); 
            header = help; 
            this.size++; 
        } else {
            Node<E> healpHeader = this.header;
            help = new Node<>(dato, healpHeader);
            this.header = help;
        }
        this.size++;
    }

    private void addLast(E info) {
        Node<E> help; 
        if (isEmpty()) { 
            help = new Node<>(info); 
            header = help; 
            last = help; 
        } else {
            help = new Node<>(info, null); 
            last.setNext(help); 
            last = help; 
        }
        this.size++; 
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return last.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header; 
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }

        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public void update(E data, Integer post) throws Exception{
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }else if (post < 0 || post >= size){
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        }else if (post == 0){
            header.setInfo(data);
        }else if (post == (size-1)){
            last.setInfo(data);
        }else{
            Node<E> search = header;
            Integer cont = 0;
            while (cont < post) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }

    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;   
            }
            size--;
            return element;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
            }
    }


    public E delete(Integer post) throws Exception {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        } else if (post < 0 || post >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites de la lista");
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }
    }
    



    /****************************************METODOS DE ORDENAMIENTO******************************** */

    // -------------------------------------------- Implementación de Quicksort ----------------------------- //
    public void quickSort(Comparator<E> comparator) {
        if (isEmpty()) return;
        
        E[] array = this.toArray();
        quickSortRecursive(array, 0, array.length - 1, comparator);
        
        this.reset();
        this.toList(array);
    }

    private void quickSortRecursive(E[] arr, int low, int high, Comparator<E> comparator) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high, comparator);
            
            quickSortRecursive(arr, low, partitionIndex - 1, comparator);
            quickSortRecursive(arr, partitionIndex + 1, high, comparator);
        }
    }

    private int partition(E[] arr, int low, int high, Comparator<E> comparator) {
        E pivot = arr[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                
                E temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        E temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }



    // -------------------------------------------- Implementación de MergeSort ----------------------------- //
    public void mergeSort(Comparator<E> comparator) {
        if (isEmpty()) return;
        E[] array = this.toArray();
        mergeSortRecursive(array, 0, array.length - 1, comparator);
        this.reset();
        this.toList(array);
    }

    private void mergeSortRecursive(E[] arr, int left, int right, Comparator<E> comparator) {
        if (left < right) {
            int mid = (left + right) / 2;
            
            mergeSortRecursive(arr, left, mid, comparator);
            mergeSortRecursive(arr, mid + 1, right, comparator);
            
            merge(arr, left, mid, right, comparator);
        }
    }

    private void merge(E[] arr, int left, int mid, int right, Comparator<E> comparator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        E[] leftArray = (E[]) new Object[n1];
        E[] rightArray = (E[]) new Object[n2];

        for (int i = 0; i < n1; ++i)
            leftArray[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            rightArray[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }

    // -------------------------------------------- Implementación de ShellSort ----------------------------- //
    public void shellSort(Comparator<E> comparator) {
        if (isEmpty()) return;
        
        E[] array = this.toArray();
        int n = array.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                E temp = array[i];
                int j;
                
                for (j = i; j >= gap && comparator.compare(array[j - gap], temp) > 0; j -= gap) {
                    array[j] = array[j - gap];
                }
                
                array[j] = temp;
            }
        }
        
        this.reset();
        this.toList(array);
    }

    /****************************************METODOS DE BUSQUEDA******************************** */

    // -------------------------------------------- Implementación de busqueda lineal ----------------------------- //
    public Integer linearSearch(E target, Comparator<E> comparator) {
        if (isEmpty()) return -1;
        
        Node<E> current = header;
        int index = 0;
        
        while (current != null) {
            if (comparator.compare(current.getInfo(), target) == 0) {
                return index;
            }
            current = current.getNext();
            index++;
        }
        
        return -1;
    }

    // -------------------------------------------- Implementación de busqueda Binaria ----------------------------- //
    public Integer binarySearch(E target, Comparator<E> comparator) {
        if (isEmpty()) return -1;
        
        E[] array = this.toArray();
        int left = 0;
        int right = array.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compareResult = comparator.compare(array[mid], target);
            
            if (compareResult == 0) {
                return mid;
            }
            
            if (compareResult < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }

}
    

