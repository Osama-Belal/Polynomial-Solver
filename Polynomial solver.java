import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


interface IPolynomialSolver {
    /**
    * Set polynomial terms (coefficients & exponents)
    * @param poly: name of the polynomial
    * @param terms: array of [coefficients][exponents]
    */
    void setPolynomial(char poly, int[][] terms);
  
    /**
    * Print the polynomial in ordered human readable representation
    * @param poly: name of the polynomial
    * @return: polynomial in the form like 27x^2+x-1
    */
    String print(char poly);
  
    /**
    * Clear the polynomial
    * @param poly: name of the polynomial
    */
    void clearPolynomial(char poly);
  
    /**
    * Evaluate the polynomial
    * @param poly: name of the polynomial
    * @param value: the polynomial constant value
    * @return the value of the polynomial
    */
    float evaluatePolynomial(char poly, float value);
  
    /**
    * Add two polynomials
    * @param poly1: first polynomial
    * @param poly2: second polynomial
    * @return the result polynomial
    */
    int[][] add(char poly1, char poly2);
  
    /**
    * Subtract two polynomials
    * @param poly1: first polynomial
    * @param poly2: second polynomial
    * @return the result polynomial*/
    int[][] subtract(char poly1, char poly2);
  
    /**
    * Multiply two polynomials
    * @param poly1: first polynomial
    * @param poly2: second polynomial
    * @return: the result polynomial
    */
    int[][] multiply(char poly1, char poly2);
}


public class PolynomialSolver implements IPolynomialSolver{
    

    public static class DoubleLinkedList{

            public class myNode{
                Object item;
                myNode next;
            }

            int lenth = 0;
            myNode head = null , tail = null;


        //---------------------------------------------------------------------------

            public void add (Object element){ 
                myNode input = new myNode(); 
                input.item = element; 

                if (this.lenth == 0){ 
                    this.head = this.tail = input; 
                    input.next = null ; 
                } 
                else { 
                    this.tail.next = input; 
                    this.tail = input; 
                    input.next = null; 
                } 

                this.lenth++; 
            } 

        //-------------------------------------------------------------------------

            public void clear(){ 
                this.head = this.tail = null; 
            } 
        //-------------------------------------------------------------------------

            public boolean isEmpty(){ 
                return this.head == null; 
            } 

        }
    
    
    
    ////////////////////////////////////////////////////////////////////////////////
    
        static DoubleLinkedList A = new DoubleLinkedList() ;
        static DoubleLinkedList B = new DoubleLinkedList() ;
        static DoubleLinkedList C = new DoubleLinkedList() ;
        static DoubleLinkedList R = new DoubleLinkedList() ;


    //------------------------------------------------------------------------------

        
        public void setPolynomial(char poly, int[][] terms){
            DoubleLinkedList pr = makePr(poly);
            pr.head = pr.tail = null ;
            pr.lenth = 0 ;
            for(int i = 0; i < terms[0].length; i++)
                pr.add(terms[0][i]);
        }
    
    //------------------------------------------------------------------------------
    
        public void clearPolynomial(char poly){
            DoubleLinkedList pr = makePr(poly);
            pr.head = pr.tail = null; 
            pr.lenth = 0 ;
    }

    //------------------------------------------------------------------------------

        public float evaluatePolynomial(char poly, float value){
            
            int term;
            int ans = 0;
            DoubleLinkedList pr = makePr(poly);
            DoubleLinkedList.myNode cur = pr.head;
            if(pr.head == null){System.out.println("Error") ; System.exit(0);}
            for(int i = 0;cur.next != null; i++){
                term = (int)Math.pow(value, pr.lenth - (i+1));
                term = term * (int)cur.item;
                ans = ans + term;
                cur = cur.next;
            }

            ans = ans + (int)cur.item;
            return (float)ans;

        }

    //------------------------------------------------------------------------------

        public int[][] multiply(char poly1, char poly2){

            DoubleLinkedList pol1 = makePr(poly1);
            DoubleLinkedList.myNode cur1 = pol1.head;

            DoubleLinkedList pol2 = makePr(poly2);
            DoubleLinkedList.myNode cur2 = pol2.head;

            if(pol1.isEmpty() || pol2.isEmpty()){
                System.out.print("Error");
                System.exit(0);
            }

            int[][] result = new int[2][pol1.lenth + pol2.lenth - 1];

            for(int i = 0; i < pol1.lenth; i++){
                for(int j = 0; j < pol2.lenth; j++){

                    result[0][i+j] = result[0][i+j] + (int)cur1.item * (int)cur2.item; // coeffiecient
                    result[1][i+j] = (pol1.lenth + pol2.lenth - 2) - (i+j); //exponent
                    cur2 = cur2.next;

                }
                cur1 = cur1.next;
                cur2 = pol2.head;
            }

            DoubleLinkedList.myNode cur = R.head;
            for(int i = 0; i < result[0].length; i++)
                R.add(result[0][i]);

            return result;

    }
    
    //------------------------------------------------------------------------------
    
        public int[][] add(char poly1, char poly2){
            DoubleLinkedList pr1 = makePr(poly1);
            DoubleLinkedList pr2 = makePr(poly2);
            DoubleLinkedList.myNode cur1 = pr1.head ;
            DoubleLinkedList.myNode cur2 = pr2.head ;
            
            if(pr1.isEmpty() || pr2.isEmpty()){
                System.out.print("Error");
                System.exit(0);
            }

            if(pr1.lenth > pr2.lenth){
                for(int i = 0; i < pr1.lenth - pr2.lenth; i++){
                    R.add(cur1.item);
                    cur1 = cur1.next;
                }
            }
            else if(pr2.lenth > pr1.lenth){
                for(int i = 0;i< pr2.lenth - pr1.lenth; i++){
                    R.add(cur2.item);
                    cur2 = cur2.next;
                }
            }
            while (cur1 != null && cur2 != null){
                R.add((int)cur1.item + (int)cur2.item);
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            
            cur1 = R.head;
            
            int[][] arr = new int [2][R.lenth];
            for(int i = 0; i < R.lenth; i++){
                arr[0][i] = (int)cur1.item;
                arr[1][i]= R.lenth-(1+i);
                cur1 = cur1.next ;
            }
            return arr;
        }
    
    //------------------------------------------------------------------------------
    

        public int[][] subtract(char poly1, char poly2){
            DoubleLinkedList pr1 = makePr(poly1) ;
            DoubleLinkedList pr2 = makePr(poly2) ;
            DoubleLinkedList.myNode cur1 = pr1.head ;
            DoubleLinkedList.myNode cur2 = pr2.head ;
            if(pr1.isEmpty() || pr2.isEmpty()){
                System.out.print("Error");
                System.exit(0);
            }
            if(pr1.lenth > pr2.lenth){
                for(int i = 0; i < pr1.lenth - pr2.lenth; i++){
                    R.add(cur1.item);
                    cur1 = cur1.next;
                }
            }
            else if(pr2.lenth > pr1.lenth){
                for(int i = 0;i< pr2.lenth - pr1.lenth; i++){
                    R.add(cur2.item);
                    cur2 = cur2.next;
                }
            }
            while (cur1 != null && cur2 != null){
                R.add((int)cur1.item - (int)cur2.item);
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            
            cur1 = R.head;
            
            int[][] arr = new int [2][R.lenth];
            for(int i = 0; i < R.lenth; i++){
                arr[0][i] = (int)cur1.item;
                arr[1][i]= R.lenth - (1+i);
                cur1 = cur1.next ;
            }
            return arr;
        }

    
    //------------------------------------------------------------------------------

        public static DoubleLinkedList makePr(char poly){
            DoubleLinkedList list = new DoubleLinkedList();
            if(poly == 'A')
                list = A;

            else if(poly == 'B')
                list = B;

            else if(poly == 'C')
                list = C;
            
            else if(poly == 'R')
                list = R;


            return list;

        }

    //-----------------------------------------------------------------------------

         public String print(char poly) {
                DoubleLinkedList pr = makePr(poly) ;
                DoubleLinkedList.myNode cur = pr.head;
               StringBuilder str = new StringBuilder();
               int flag = 0 ; 
                if(pr.isEmpty()){
                    System.out.println("Error") ; 
                    System.exit(0) ;
                }
                else if (pr.lenth ==1){
                    System.out.println(pr.head.item) ; 
                    System.exit(0);
                }
                for (int i = 0; i < pr.lenth; i++) {
                    if((int)cur.item !=0) {
                        if ((int) cur.item != 1){
                            str.append(cur.item);
                            flag = 1 ;
                        }
                        if (pr.lenth - (i + 1) != 0){
                            str.append("x");
                            flag = 1 ;
                        }
                        if (pr.lenth - (i + 1) != 1 && pr.lenth - (i + 1) != 0) {
                            str.append("^");
                            str.append(pr.lenth - (i + 1));
                            flag = 1 ;
                        }
                    }
                    if (i != pr.lenth - 1 && (int) cur.next.item > 0 && (int)cur.item !=0)
                        str.append("+");

                    cur = cur.next;
                }
             if(flag ==0){
                 System.out.println("0");
                 System.exit(0);
             }
                return str.toString();
            }  
    
    //----------------------------------------------------------------------------------------
        
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */

        Scanner sc = new Scanner(System.in);
        PolynomialSolver solution = new PolynomialSolver();

        while(sc.hasNext()){

            String ss = sc.nextLine();
            if(ss.equalsIgnoreCase("set")){

                char poly = sc.nextLine().charAt(0);
                if(poly != 'A' && poly != 'B' && poly != 'C'){
                    System.out.println("Error") ;
                    System.exit(0);
                }

                String sin = sc.nextLine().replaceAll("[\\[\\]]", "");
                String[] str = sin.split(",");
                if(!(str[0].isEmpty())){
                int [][] arr = new int [2][str.length];

                for (int i=0 ; i< str.length ; i++){
                    arr[0][i] = Integer.parseInt(str[i]);
                    arr[1][i] = str.length-(i+1) ;
                }
                solution.setPolynomial(poly , arr);
                }
                else{
                    System.out.println("Error") ;
                    System.exit(0);
                }
                

            }
            
            else if(ss.equalsIgnoreCase("print")){

                char poly = sc.nextLine().charAt(0);
                System.out.println(solution.print(poly));

            }
            
            else if(ss.equalsIgnoreCase("clear")){
                
                char poly = sc.nextLine().charAt(0);
                DoubleLinkedList p = makePr(poly) ; 
                if(p.head == null){
                    System.out.println("Error");
                }
                else
                {
                solution.clearPolynomial(poly);
                System.out.println("[]");
                }

            }

            else if(ss.equalsIgnoreCase("eval")){

                char poly = sc.nextLine().charAt(0);
                float value = sc.nextFloat();

                System.out.print((int)solution.evaluatePolynomial(poly, value));

            }
            
            else if(ss.equalsIgnoreCase("mult")){

                char poly1 = sc.nextLine().charAt(0);
                char poly2 = sc.nextLine().charAt(0);
                
                solution.multiply(poly1, poly2);

                System.out.print(solution.print('R'));

            }
            
            else if(ss.equalsIgnoreCase("add")){

                char poly1 = sc.nextLine().charAt(0);
                char poly2 = sc.nextLine().charAt(0);
                
                solution.add(poly1, poly2);
                System.out.print(solution.print('R'));

            }

            else if(ss.equalsIgnoreCase("sub")){

                char poly1 = sc.nextLine().charAt(0);
                char poly2 = sc.nextLine().charAt(0);
                
                solution.subtract(poly1, poly2);
                System.out.print(solution.print('R'));

            }
            
            else {
                System.out.print("Error");
                return ;
            }
       
        }
            
    }
}