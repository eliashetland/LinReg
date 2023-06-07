import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinReg {

    private List<Double> x1List = new ArrayList<>();
    private List<Double> x2List = new ArrayList<>();
    private List<Double> yList = new ArrayList<>();


    public LinReg(Point... points) {
        for (Point point : points) {
            x1List.add(point.getX());
            x2List.add(1.0);

            yList.add(point.getY());
        }

        List<Double> A = AtA();
        List<Double> b = Atb();
    
        Point lin = calcGaus(A, b);
    
        System.out.printf("Best fitted Line: %,.3f x + %,.3f",  lin.getX(), lin.getY());
    }


    private List<Double> AtA(){
        double a1 = 0;
        double a2 = 0;
        double a3 = 0;

        for (int i = 0; i < x1List.size(); i++) {
            a1 += (x1List.get(i) * x1List.get(i));
            a2 += (x1List.get(i) * x2List.get(i));
            a3 += (x2List.get(i) * x2List.get(i));
        }

        return Arrays.asList(a1,a2,a2,a3);
    }

    private List<Double> Atb(){
        double a1 = 0;
        double a2 = 0;

        for (int i = 0; i < x1List.size(); i++) {
            a1 += (x1List.get(i) * yList.get(i));
            a2 += (x2List.get(i) * yList.get(i));
        }

        return Arrays.asList(a1,a2);
    }

    private Point calcGaus(List<Double> A,List<Double> b){
        // printMatrix(A, b, "");
        
        // normalixe first row
        double firstPiv = A.get(0);
        double secondPiv = A.get(2);
        if (firstPiv != 0 && secondPiv != 0)  {

            //normalize first row
            A.set(0, A.get(0)/firstPiv);
            A.set(1, A.get(1)/firstPiv);
            b.set(0, b.get(0)/firstPiv);
            // printMatrix(A, b, "normalize first");

            //remove first element in r2;
            A.set(2, 0.0);
            A.set(3, A.get(3)-(secondPiv*A.get(1)));
            b.set(1, b.get(1)-(secondPiv*b.get(0)));
            // printMatrix(A, b, "remove under first piv");

            // normalize second row
            secondPiv = A.get(3);
            A.set(2, A.get(2)/secondPiv);
            A.set(3, A.get(3)/secondPiv);
            b.set(1, b.get(1)/secondPiv);    
            // printMatrix(A, b, "normalize second");

           
            // remove over second piv
            double v2 = A.get(1);
            A.set(1, 0.0);
            b.set(0, b.get(0)-(v2));
            //printMatrix(A, b, "remove under second piv");
            

                                    
            return new Point(b.get(0), b.get(1));
        }else{ throw new IllegalArgumentException();}
    }


    private void printMatrix(List A, List b, String message){
        System.out.println(message);
        System.out.println("| "+ A.get(0) +" " + A.get(1) + " | " + b.get(0)+"| \n| " + 
        A.get(2) +" " + A.get(3) + " | " + b.get(1)+"| ");
    }


    public static void main(String[] args) throws Exception {
        
        Point p1 = new Point(0,1);
        Point p2 = new Point(1,2);
        Point p3 = new Point(6,9);
        Point p4 = new Point(1,4);
        Point p5 = new Point(2,9);


        LinReg l = new LinReg(p1, p2, p3, p4);


    }
}
