import java.util.Scanner;

public class Atlas {



    public static void main(String[] args) {
        String logo = "   ===      ========   =            ===       =====  \n" +
                "  =   =        =       =           =   =     =     = \n" +
                " =     =       =       =          =     =    =       \n" +
                " =     =       =       =          =     =     =====  \n" +
                " =======       =       =          =======          = \n" +
                " =     =       =       =          =     =    =     = \n" +
                " =     =       =       ========   =     =     =====  \n";
        System.out.println(logo);



        new AtlasCLI(new Scanner(System.in)).run();


    }
}
