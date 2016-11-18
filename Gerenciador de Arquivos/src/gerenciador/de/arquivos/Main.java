
package gerenciador.de.arquivos;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Vitor
 */
public class Main 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
        int choice = 0;
        
        Indice.gerarIndice();
 
        do
        {
            System.out.println("1-Inserir Registro");
            System.out.println("2-Excluir Registro");
            System.out.println("3-Buscar Registro");
            System.out.println("4-Exibir todos os Registros");
            System.out.println("5-Sair");
            
            Scanner entrada = new Scanner(System.in);
            choice = entrada.nextInt();
            
            switch (choice)
            {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                    
            }
        }while(choice > 5);
        
    }
    
}
