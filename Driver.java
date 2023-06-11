import java.io.File;
import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        Polynomial emptyPoly = new Polynomial();
        System.out.println("emptyPoly(3) = " + emptyPoly.evaluate(3));

        double[] c1 = {5, 2, 1, -3};
        int[] e1 = {3, 2, 1, 0};
        Polynomial testPoly1 = new Polynomial(c1, e1);

        double[] c2 = {1, -2, 4, 4, -9};
        int[] e2 = {1, 3, 0, 5, 2};
        Polynomial testPoly2 = new Polynomial(c2, e2);

        Polynomial sumPoly = testPoly1.add(testPoly2);
        System.out.println("sumPoly(2) = " + sumPoly.evaluate(2));

        Polynomial productPoly = testPoly1.multiply(testPoly2);
        System.out.println("productPoly(0.5) = " + productPoly.evaluate(0.5));

        double[] c3 = {1, -4};
        int[] e3 = {2, 0};
        Polynomial testPoly3 = new Polynomial(c3, e3);

        if (testPoly3.hasRoot(-2)) System.out.println("-2 is a root of x^2-4");
        else System.out.println("-2 is not a root of x^2-4");
        if (testPoly3.hasRoot(0)) System.out.println("0 is a root of x^2-4");
        else System.out.println("0 is not a root of x^2-4");

        File f = new File("testFile.txt");

        double[] c4 = {1, -2, 3, -4, 5};
        int[] e4 = {0, 1, 2, 5, 3};
        Polynomial testPoly4 = new Polynomial(c4, e4);

        testPoly4.saveToFile("testFile.txt");

        Polynomial filePoly = new Polynomial(f);
        System.out.println("filePoly(1.5) = " + filePoly.evaluate(1.5));
    }

}