package concurrent.sort;

public class mp {

    public static void main(String[] args) {

        int a[] = {9,2,5,8,0,3,7,5,1};
        mpsort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }

    }

    private static void mpsort(int[] a) {

        for (int i = 0; i < a.length; i++) {
            for (int j = a.length-1; j > i; j--) {
                if (a[j]<a[j-1]){
                    int temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] = temp;
                }
            }
        }
    }


}
