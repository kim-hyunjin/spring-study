package chap07;

public class ImpCalculator implements Calculator{

    public long factorial(long num) {
        long result = 1;
        for(int i = 1; i<= num; i++) {
            result *= i;
        }
        return result;
    }
}
