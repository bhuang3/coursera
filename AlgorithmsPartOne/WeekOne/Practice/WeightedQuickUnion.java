public class WeightedQuickUnion {
  private int[] values;
  private int[] sizes;
  private int components;

  public WeightedQuickUnion(int n) {
    this.components = n;
    this.values = new int[n];
    this.sizes = new int[n];

    for (int i = 0; i < n; i++) {
      this.values[i] = i;
      this.sizes[i] = 1;
    }
  }

  public void union(int p, int q) {
    int pRoot = this.find(p);
    int qRoot = this.find(q);

    if (pRoot == qRoot) {
      return;
    }

    if (this.sizes[qRoot] < this.sizes[pRoot]) {
      this.values[qRoot] = pRoot;
      this.sizes[pRoot] += this.sizes[qRoot];
    } else {
      this.values[pRoot] = qRoot;
      this.sizes[qRoot] += this.sizes[pRoot];
    }

    this.components--;
  }

  public int find(int site) {
    int curr = site;

    while (curr != this.values[curr]) {
      this.values[curr] = this.values[this.values[curr]];
      curr = this.values[curr];
    }

    return curr;
  }

  public boolean connected(int p, int q) {
    return this.find(p) == this.find(q);
  }

  public int count() {
    return this.components;
  }
}