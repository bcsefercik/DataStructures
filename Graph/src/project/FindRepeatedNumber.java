package project;
import static java.lang.Math.abs;

public class FindRepeatedNumber {

    private static void findRepeatedNumber(int arr[]) {
        int i;
        for (i = 0; i < arr.length; i++) {
            if (arr[abs(arr[i])] > 0)
                arr[abs(arr[i])] = -arr[abs(arr[i])];
            else {
                System.out.print(abs(arr[i]) + ",");
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = { 4, 3, 4, 5, 3, 3, 1 };
        findRepeatedNumber(arr);
    }}