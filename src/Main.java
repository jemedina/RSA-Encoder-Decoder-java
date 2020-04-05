import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            switch(menu()) {
                case 1:
                    CertGenerator.create();
                    break;
                case 2:
                    System.out.print("Input message to encode > ");
                    String msg = new Scanner(System.in).nextLine();
                    String encoded = EncoderDecoder.encode(msg);
                    System.out.println(encoded);
                    break;
                case 3:
                    System.out.print("Input message to decode > ");
                    String msg2 = new Scanner(System.in).nextLine();
                    String decoded = EncoderDecoder.decode(msg2);
                    System.out.println(decoded);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int menu() {
        System.out.println("1: Gen keys");
        System.out.println("2: Encode message");
        System.out.println("3: Decode message");
        System.out.print("> ");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        return option;
    }

}
