package gerenciador.de.arquivos;

import java.io.IOException;
import java.util.Scanner;

public class Menu {

    public static void exibirMenu() throws IOException {

        Indice.gerarIndices();
        EspacoVazio.gravaEV();//Cria o arquivo de indice de Excluidos ao iniciar o programa

        int escolha = 0;

        while (escolha != 5) {
            System.out.println("---------Menu---------");
            System.out.println("1-Inserir um registro");
            System.out.println("2-Excluir um registro");
            System.out.println("3-Buscar");
            System.out.println("4-Listar");
            System.out.println("5-Sair");

            Scanner scan = new Scanner(System.in);//essa n entendi hahahaha
            escolha = scan.nextInt();

            switch (escolha) {
                case 1:
                    Indice.inserirRegistro();//chama as funções de inserção
                    break;
                case 2:
                    Indice.excluirRegistro();//chama a função de Excluir reg
                    break;
                case 3:
                    menuBuscar();//chama as funções de Busca
                    break;
                case 4:
                    menuListar();//Chama as funções de listar
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
            }
            new Scanner(System.in).nextLine();
        }

    }

    public static void menuBuscar() throws IOException {
        int escolha = 0;

        while (escolha != 3) {
            System.out.println("---------Menu Buscar---------");
            System.out.println("1-Buscar por chave prim�ria");
            System.out.println("2-Buscar por chave secund�ria");
            System.out.println("3-Voltar");

            Scanner scan = new Scanner(System.in);
            escolha = scan.nextInt();

            switch (escolha) {
                case 1:
                    Indice.buscarPorChavePrimaria();
                    break;
                case 2:
                    Indice.buscarPorChaveSecundaria();
                    break;
                case 3:
                    exibirMenu();
                    break;
            }
            new Scanner(System.in).nextLine();
        }

    }

    public static void menuListar() throws IOException {
        int escolha = 0;

        while (escolha != 3) {
            System.out.println("---------Menu Listas---------");
            System.out.println("1-Lista de �ndices");
            System.out.println("2-Lista de espa�os vazios");
            System.out.println("3-Voltar");

            Scanner scan = new Scanner(System.in);
            escolha = scan.nextInt();

            switch (escolha) {
                case 1:
                    Indice.exibirIndices();
                    break;
                case 2:
                    EspacoVazio.mostraEspacosVazios();
                    break;
                case 3:
                    exibirMenu();
                    break;
            }
            new Scanner(System.in).nextLine();
        }
    }
}
