public class Polynomial {

    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial p) {
        int polyLength = Math.max(coefficients.length, p.coefficients.length);
        double[] placeholder = new double[polyLength];
        Polynomial sum = new Polynomial(placeholder);

        for (int i = 0; i < polyLength; i++) {
            if (coefficients.length > i) sum.coefficients[i] += coefficients[i];
            if (p.coefficients.length > i) sum.coefficients[i] += p.coefficients[i];
        }
        return sum;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double root) {
        return evaluate(root) == 0;
    }

}