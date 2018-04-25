public class MergeSort<T extends Comparable> {
  public static <T extends Comparable> sort(T[] array, int start, int end) {
    if (start < end) {
      int middle = start + (end - start) / 2;

      sort(array, start, middle);
      sort(array, middle + 1, end);

      merge(array, start, middle, end);
    }
  }

  private static <T extends Comparable> merge(T[] array, int start, int middle, int end) {
    int i = start;
    int j = middle + 1;
    int k = 0;
    T[] temp = new T[end - start + 1];

    while (i <= middle && j <= end) {
      if (array[i].compareTo(array[j]) <= 0) {
        temp[k++] = array[i++];
      } else {
        temp[k++] = array[j++];
      }
    }

    while (i <= middle) {
      temp[k++] = array[i++];
    }

    while (j <= end) {
      temp[k++] = array[j++];
    }

    for (int m = 0, n = start; n <= start; n++) {
      array[n] = temp[m++];
    }
  };
}