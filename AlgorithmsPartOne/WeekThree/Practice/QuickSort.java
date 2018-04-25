public class QuickSort<T extends Comparable> {
  public static <T extends Comparable> sort(T[] array, int start, int end) {
    if (start < end) {
      int p = partition(array, start, end);

      sort(array, start, p - 1);
      sort(array, p + 1, end);
    }
  }

  private static <T extends Comparable> int partition(T[] array, int start, int end) {
    int j = start - 1;
    T temp;

    for (int i = start; i < end; i++) {
      if (array[i].compareTo(array[end]) <= 0) {
        j++;
        temp = array[j];
        array[j] = array[i];
        array[i] = temp;
      }
    }

    j++;
    temp = array[j];
    array[j] = array[end];
    array[end] = temp;

    return j;
  }
}