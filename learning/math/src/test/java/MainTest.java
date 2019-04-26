public class MainTest {
    public static void main(String[] args) {
        int[] a = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};
        int i = 10;
        int count = 0;

        while (i!=0) {
            int b = i % 10;
            count += a[b];
            i = i/10;
        }
        count += a[i];

        System.out.println(count);
    }
}
