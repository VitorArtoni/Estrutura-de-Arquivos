package gerenciador.de.arquivos;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    public static void exibirMenu() throws IOException {

        Indice.gerarIndices();
        EspacoVazio.gravaEspacoVazio();

        int escolha = 0;

        while (escolha != 5) {
            System.out.println("-------------------------");
            System.out.println("1-Inserir um registro");
            System.out.println("2-Excluir um registro");
            System.out.println("3-Buscar um registro");
            System.out.println("4-Listar Índices");
            System.out.println("5-Sair");
            System.out.println("-------------------------");
            
            Scanner scan = new Scanner(System.in);
            escolha = scan.nextInt();

            long tempoInicial = System.currentTimeMillis();
            long tempoFinal;
           
            switch (escolha) {
                case 1:
                    Registro.inserirRegistro();
                    tempoFinal = System.currentTimeMillis();
                    System.out.printf("Tempo gasto para inserir registro: %.3f ms%n",(tempoFinal - tempoInicial)/1000d);
                    break;
                case 2:
                    Registro.excluirRegistro();
                    tempoFinal = System.currentTimeMillis();
                    System.out.printf("Tempo gasto para excluir registro: %.3f ms%n",(tempoFinal - tempoInicial)/1000d);
                    break;
                case 3:
                    exibirMenuBuscas();
                    break;
                case 4:
                    Indice.exibirIndices();
                    tempoFinal = System.currentTimeMillis();
                    System.out.printf("Tempo gasto para exibir indices: %.3f ms%n",(tempoFinal - tempoInicial)/1000d);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
            }
            new Scanner(System.in).nextLine();
        }

    }

    public static void exibirMenuBuscas() throws IOException {
        int escolha = 0;

        while (escolha != 3) {
            System.out.println("----------------------------");
            System.out.println("1-Buscar por chave primária");
            System.out.println("2-Voltar");
            System.out.println("----------------------------");

            Scanner scan = new Scanner(System.in);
            escolha = scan.nextInt();

            long tempoInicial = System.currentTimeMillis();
            long tempoFinal;
            
            switch (escolha) {
                case 1:
                    Indice.buscarPorChavePrimaria();
                    tempoFinal = System.currentTimeMillis();
                    System.out.printf("Tempo gasto para buscar por chave primária: %.3f ms%n",(tempoFinal - tempoInicial)/1000d);
                    break;
                case 2:
                    exibirMenu();
                    break;
            }
            new Scanner(System.in).nextLine();
        }
    }
}
