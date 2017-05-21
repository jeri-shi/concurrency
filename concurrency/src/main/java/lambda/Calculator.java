package lambda;

public class Calculator {
  
  interface IntegerMath {
    int operation(int a, int b);
  }
  
  public int operateBinary(int a, int b, IntegerMath math) {
    System.out.print("a=" + a + ", b=" + b + " :");
    return math.operation(a, b);
  }
  
  public static void main(String[] args) {
    Calculator cal = new Calculator();
    IntegerMath add = (a, b) -> a + b;
    IntegerMath minus = (a, b) -> a - b;
    IntegerMath mutiple = (a, b) -> a * b;
    
    System.out.println(cal.operateBinary(20, 10, add));
    System.out.println(cal.operateBinary(20, 10, minus));
    System.out.println(cal.operateBinary(20, 10, mutiple));
    
  }

}
