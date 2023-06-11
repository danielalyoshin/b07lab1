import java.io.*;

public class Polynomial {

    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        this.coefficients = new double[]{};
        this.exponents = new int[]{};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] terms = line.split("[-+]");
        int numTerms = terms.length;
        char[] elements = line.toCharArray();
        br.close();

        double[] coefficients = new double[numTerms];
        int[] exponents = new int[numTerms];

        int counter = 0;
        for (int i = 0; i < numTerms; i++) {
            StringBuilder strCoefficient = new StringBuilder();
            StringBuilder strExponent = new StringBuilder();

            if (elements[counter] == '-') {
                strCoefficient.append(elements[counter]);
                counter++;
            } else if (elements[counter] == '+') counter++;

            if (elements[counter] == 'x')
                coefficients[i] = Double.parseDouble(String.valueOf(strCoefficient.append("1")));
            while (counter < elements.length && elements[counter] != 'x' && elements[counter] != '-' && elements[counter] != '+') {
                strCoefficient.append(elements[counter]);
                counter++;
            }
            coefficients[i] = Double.parseDouble(String.valueOf(strCoefficient));

            if (counter == elements.length || elements[counter] != 'x') {
                exponents[i] = 0;
                continue;
            }
            counter++;
            if (elements[counter] == '-' || elements[counter] == '+') exponents[i] = 1;
            while (counter != elements.length && elements[counter] != '-' && elements[counter] != '+') {
                strExponent.append(elements[counter]);
                counter++;
            }
            exponents[i] = Integer.parseInt(String.valueOf(strExponent));
        }

        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial add(Polynomial p) {
        if (p == null || p.exponents.length != p.coefficients.length) {
            System.out.println("Polynomial is invalid!");
            return null;
        }

        int highestExp = 0;
        for (int exponent : this.exponents) if (exponent > highestExp) highestExp = exponent;
        for (int exponent : p.exponents) if (exponent > highestExp) highestExp = exponent;

        double[] rawCombinedCoefficients = new double[highestExp + 1];

        for (int i = 0; i < this.exponents.length; i++)
            rawCombinedCoefficients[this.exponents[i]] += this.coefficients[i];
        for (int i = 0; i < p.exponents.length; i++)
            rawCombinedCoefficients[p.exponents[i]] += p.coefficients[i];

        int numTerms = 0;
        for (double coefficient : rawCombinedCoefficients) if (coefficient != 0) numTerms++;

        double[] combinedCoefficients = new double[numTerms];
        int[] combinedExponents = new int[numTerms];

        int indexCounter = 0;
        for (int i = 0; i < rawCombinedCoefficients.length; i++) {
            if (rawCombinedCoefficients[i] != 0) {
                combinedCoefficients[indexCounter] = rawCombinedCoefficients[i];
                combinedExponents[indexCounter] = i;
                indexCounter++;
            }
        }

        return new Polynomial(combinedCoefficients, combinedExponents);
    }

    public Polynomial multiply(Polynomial p) {
        if (p == null || p.exponents.length != p.coefficients.length) {
            System.out.println("Polynomial is invalid!");
            return null;
        }

        Polynomial result = new Polynomial();

        double[] singleCoefficient = {0};
        int[] singleExponent = {0};
        Polynomial singleTerm = new Polynomial(singleCoefficient, singleExponent);

        for (int i = 0; i < this.exponents.length; i++) {
            singleTerm.coefficients[0] = this.coefficients[i];
            singleTerm.exponents[0] = this.exponents[i];

            Polynomial temp = new Polynomial();
            temp.coefficients = new double[p.coefficients.length];
            temp.exponents = new int[p.exponents.length];

            for (int j = 0; j < p.exponents.length; j++) {
                temp.coefficients[j] = p.coefficients[j] * singleTerm.coefficients[0];
                temp.exponents[j] = p.exponents[j] + singleTerm.exponents[0];
            }

            result = result.add(temp);
        }

        return result;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double root) {
        return evaluate(root) == 0;
    }

    public void saveToFile(String fileName) throws IOException {
        FileWriter fr = new FileWriter(fileName);
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] > 0 && i == 0) fr.write(Double.toString(coefficients[i]));
            else if (coefficients[i] > 0) fr.write("+" + coefficients[i]);
            else if (coefficients[i] < 0) fr.write(Double.toString(coefficients[i]));
            if (exponents[i] != 0) fr.write("x" + exponents[i]);
        }
        fr.flush();
        fr.close();
    }

}