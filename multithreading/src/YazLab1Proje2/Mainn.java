package YazLab1Proje2;

import java.io.File;
import static java.util.Collections.list;
import java.util.Scanner;


public class Mainn {

    static String listProduct[] = new String[10000];
    static String listIssue[] = new String[10000];
    static String listCompany[] = new String[10000];
    static String listState[] = new String[10000];
    static String listComplaintid[] = new String[10000];
    static String listZipcode[] = new String[10000];
    static String gSutun = "product";
    static String sutun = "company";
    static String b_durumu = "=";
    static double oran = 25.0;
    static int numThreads = 5000;

    static class WorkerThread implements Runnable {

        int start;
        int stop;

        WorkerThread(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }

        @Override
        public void run() {
            try {
                compare(start, stop);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static void main(String[] args) {
        readCsv();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Karşılaştırılacak Sutün:");
        sutun=sc.next();
        System.out.println("Yazdırılacak Sutün:");
        gSutun=sc.next();
        System.out.println("Benzerlik Oranı:");
        oran=sc.nextDouble();
        System.out.println("Benzerlik Durumu:");
        b_durumu=sc.next();
        System.out.println("Thread Sayısı:");
        numThreads=sc.nextInt();
        
        Thread[] threads = new Thread[numThreads];
        WorkerThread[] workerThreads = new WorkerThread[numThreads];

        int bas = 0, son = 0;

        int totalLen = 10000;
        int ln = totalLen / numThreads;

        for (int i = 0; i < numThreads; i++) {

            son += ln;
            if (son > 10000) {
                son = 10000;
            }
            workerThreads[i] = new WorkerThread(bas, son);

            bas += ln;
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(workerThreads[i]);
        }

        long startTime = System.nanoTime();

        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        for (int j = 0; j < threads.length; j++) {

            try {
                threads[j].join();
            } catch (InterruptedException e) {
                System.out.println("Problem with joining threads!");
            }
        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        System.out.printf("Süre (Nanosaniye): %d \n ", totalTime);
    }
    public static void compare(int start, int end) {

        float benzerlik;

        for (int i = start; i < end; i++) {

            for (int j = 0; j < 10000; j++) {
                String str1 = null, str2 = null;
                if (sutun.equals("product")) {
                    
                    str1 = listProduct[i];
                    str2 = listProduct[j];
                } else if (sutun.equals("issue")) {
                    str1 = listIssue[i];
                    str2 = listIssue[j];
                } else if (sutun.equals("company")) {
                    
                    str1 = listCompany[i];
                    str2 = listCompany[j];
                } else if (sutun.equals("state")) {
                    str1 = listState[i];
                    str2 = listState[j];
                } else if (sutun.equals("zipcode")) {
                    str1 = listZipcode[i];
                    str2 = listZipcode[j];
                }
                //String str1 = listProduct[i];
                //String str2 = listProduct[j];

                // System.out.print(str1+"        ");
                // System.out.println(str2);
                String tempstr1 = str1.toLowerCase();
                String tempstr2 = str2.toLowerCase();

                String words1[] = tempstr1.split(" ");
                String words2[] = tempstr2.split(" ");

                int count = 0;
                for (int k = 0; k < words1.length; k++) {

                    for (int l = 0; l < words2.length; l++) {

                        if (words1[k].equals(words2[l])) {
                            count += 1;
                            break;
                        }
                    }
                }

                int big;
                if (words1.length > words2.length) {
                    big = words1.length;
                } else {
                    big = words2.length;
                }
                benzerlik = (float) count / (float) big * 100;
                statement(benzerlik, i, j);
                //   System.out.println(benzerlik);
            }
        }
    }
    
    public static void statement(double benzerlik, int i, int j) {
        if (b_durumu.equals("=")) {
            if (benzerlik == oran) {
                //System.out.println("eşit");
                //System.out.println(benzerlik);
                write(i, j, benzerlik);
                //System.out.println(str1 + "   " + str2);
            }
        } else if (b_durumu.equals(">=")) {

            if (benzerlik >= oran) {
                write(i, j, benzerlik);
                //System.out.println("büyük eşit");
                //System.out.println(benzerlik);
                //System.out.println(str1 + "   " + str2);
            }
        } else if (b_durumu.equals("<=")) {
            if (benzerlik <= oran) {
                write(i, j, benzerlik);
                //System.out.println("küçük eşit");
                //System.out.println(benzerlik);
                // System.out.println(str1 + "   " + str2);
            }
        }
    }

    public static void write(int i, int j, double benzerlik) {
        if (gSutun.equals("product")) {
            System.out.println("Kayıt 1: " + listProduct[i]);
            System.out.println("Kayıt 2: " + listProduct[j]);
            System.out.println("Benzerlik: " + benzerlik);
            System.out.println();
        } else if (gSutun.equals("issue")) {
            System.out.println("Kayıt 1: " + listIssue[i]);
            System.out.println("Kayıt 2: " + listIssue[j]);
            System.out.println("Benzerlik: " + benzerlik);
            System.out.println();
        } else if (gSutun.equals("company")) {
            System.out.println("Kayıt 1: " + listCompany[i]);
            System.out.println("Kayıt 2: " + listCompany[j]);
            System.out.println("Benzerlik: " + benzerlik);
            System.out.println();
        } else if (gSutun.equals("state")) {
            System.out.println("Kayıt 1: " + listState[i]);
            System.out.println("Kayıt 2: " + listState[j]);
            System.out.println("Benzerlik: " + benzerlik);
            System.out.println();
        } else if (gSutun.equals("zipcode")) {
            System.out.println("Kayıt 1: " + listZipcode[i]);
            System.out.println("Kayıt 2: " + listZipcode[j]);
            System.out.println("Benzerlik: " + benzerlik);
            System.out.println();
        } else if (gSutun.equals("complaintid")) {
            System.out.println("Kayıt 1: " + listComplaintid[i]);
            System.out.println("Kayıt 2: " + listComplaintid[j]);
            System.out.println("Benzerlik: " + benzerlik);
            System.out.println();
        }

    }
    public static void readCsv() {
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\AKS\\PycharmProjects\\pythonProject3\\product.csv"));
            int i = 0, j = 0;
            
           
             while (i < 10000) {
                    String temp = sc.nextLine();
                    temp = temp.replace(",", "");
                    listProduct[i] = temp;
                    //System.out.println(listProduct[i]);
                    i++;
             }
             sc.close();
             sc = new Scanner(new File("C:\\Users\\AKS\\PycharmProjects\\pythonProject3\\issue.csv"));
             i = 0;
             
             while (i < 10000) {
                    String temp = sc.nextLine();
                    temp = temp.replace(",", "");
                    listIssue[i] = temp;
                    i++;
             }
             sc.close();
             sc = new Scanner(new File("C:\\Users\\AKS\\PycharmProjects\\pythonProject3\\company.csv"));
             i = 0;
             
             while (i < 10000) {
                    String temp = sc.nextLine();
                    temp = temp.replace(",", "");
                    listCompany[i] = temp;
                    i++;
             }
             sc.close();
             sc = new Scanner(new File("C:\\Users\\AKS\\PycharmProjects\\pythonProject3\\state.csv"));
             i = 0;
             
             while (i < 10000) {
                    String temp = sc.nextLine();
                    temp = temp.replace(",", "");
                    listState[i] = temp;
                    i++;
             }
             sc.close();
              sc = new Scanner(new File("C:\\Users\\AKS\\PycharmProjects\\pythonProject3\\zipcode.csv"));
             i = 0;
             
             while (i < 10000) {
                    String temp = sc.nextLine();
                    temp = temp.replace(",", "");
                    listZipcode[i] = temp;
                    i++;
             }
             sc.close();
             sc = new Scanner(new File("C:\\Users\\AKS\\PycharmProjects\\pythonProject3\\new.csv"));
             i = 0;
             
             int control = 0;
            String id = "";
             String t = sc.nextLine();
                while (j < 10000) {
                    t = sc.nextLine();

                    String s[] = t.split(",");
                    listComplaintid[j] = s[6];
                    
                    j++;
                }
             sc.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    
}