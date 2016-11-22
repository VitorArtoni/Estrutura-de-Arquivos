package gerenciador.de.arquivos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class Indice {

    public final static String ArqDados = "dados.txt";
    public final static String ArqInd = "indice.txt";
    public final static String ArqEVazio = "espacosVazios.txt";

    public static void gerarIndices() throws IOException {

        File arq = new File("dados.txt");

        RandomAccessFile arquivo = new RandomAccessFile(arq, "rw");
        arquivo.seek(0);

        ArrayList<Registro> listaIndices;
        listaIndices = new ArrayList<Registro>();

        Registro.lerRegistro(arquivo);

        while (arquivo.getFilePointer() < arquivo.length()) {
            String posicao = String.valueOf(arquivo.getFilePointer());

            Registro reg = Registro.lerRegistro(arquivo);
            reg.posicao = posicao;

            if (reg.campos.get(0).dado.indexOf("*") != -1) {
                continue;
            }

            int contador = 0;

            if (listaIndices.size() == 0) {
                listaIndices.add(reg);
            } else {
                boolean controle = false;

                while (contador < listaIndices.size()) {

                    long PK = Long.parseLong(listaIndices.get(contador).campos.get(0).dado);
                    long PKreg = Long.parseLong(reg.campos.get(0).dado);

                    if (PKreg < PK) {
                        listaIndices.add(contador, reg);
                        controle = true;
                        break;
                    } else {
                        contador++;
                    }
                }
                if (controle == false) {
                    listaIndices.add(reg);
                }
            }
        }
        arquivo.close();

        new File("indice.txt").delete();
        File arq2 = new File("indice.txt");

        RandomAccessFile ArqIndice = new RandomAccessFile(arq2, "rw");
        ArqIndice.seek(0);
        for (Registro registro : listaIndices) {
            String chaveP = preencherNaFrente(registro.campos.get(0).dado, 20, ' ');
            String str = preencherNaFrente(registro.posicao, 10, ' ');

            ArqIndice.write(chaveP.getBytes());
            ArqIndice.write(str.getBytes());
        }
        ArqIndice.close();
    }

    public static String preencherNaFrente(String registro, int tamanho, char caracter) {
        String s = "";

        int qtd = (tamanho - registro.length());
        int x = 0;

        while (x < qtd) {
            x++;
            s += caracter;
        }
        return registro + s;
    }

    public static void exibirIndices() throws IOException {
        File arq = new File("indice.txt");
        RandomAccessFile arqIndice = new RandomAccessFile(arq, "rw");
        arqIndice.seek(0);

        System.out.println("ChavePrimaria----------------Posicao");

        while (arqIndice.getFilePointer() < arqIndice.length()) {
            String chavePrimaria = "";
            String Posicao = "";

            for (int i = 0; i < 20; i++) {
                char caracter = (char) arqIndice.read();
                chavePrimaria += caracter;
            }

            for (int i = 0; i < 10; i++) {
                char caracter = (char) arqIndice.read();
                Posicao += caracter;
            }

            System.out.println(chavePrimaria + "\t         4"
                    + "" + Posicao.trim());
        }
        arqIndice.close();
    }

    public static void buscarPorChavePrimaria() throws IOException {
        System.out.println("Digite a Chave Primária: ");
    
        Scanner scan = new Scanner(System.in);
        long chavePri = scan.nextLong();
        IndiceRegistro reg = realizarBuscaBinaria(chavePri);
        
        if (reg == null) {
            System.out.println("Registro não encontrado!");
            return;
        }
        
        File f = new File(ArqDados);

        RandomAccessFile arq = new RandomAccessFile(f, "rw");
        arq.seek(0);

        Registro registro = Registro.lerRegistro(arq);
        arq.seek(reg.posicao);

        Registro novo = Registro.lerRegistro(arq);

        for (int i = 0; i < novo.campos.size(); i++) {
            System.out.println(registro.campos.get(i).dado + ":" + novo.campos.get(i).dado);
        }
        arq.close();
    }

    public static IndiceRegistro realizarBuscaBinaria(long chavePrimaria) throws IOException {
        File arq = new File(ArqInd);
        RandomAccessFile file = new RandomAccessFile(arq, "rw");
        file.seek(0);

        long totalRegistros = arq.length() / 30;
        long inicio = 0;
        long fim = totalRegistros - 1;

        IndiceRegistro RegAchou = null;

        while (true) {
            if (fim - inicio == 1) {

                file.seek(inicio * 30);
                IndiceRegistro reg = IndiceRegistro.lerIndiceRegistro(file);

                if (reg.chavePrimaria == chavePrimaria) {
                    RegAchou = reg;
                    break;
                }

                file.seek(fim * 30);
                reg = IndiceRegistro.lerIndiceRegistro(file);

                if (reg.chavePrimaria == chavePrimaria) {
                    RegAchou = reg;
                    break;
                }
                break;
            }

            long meio = ((fim - inicio) / 2) + inicio;
            file.seek(meio * 30);
            IndiceRegistro reg = IndiceRegistro.lerIndiceRegistro(file);

            if (reg.chavePrimaria > chavePrimaria) {
                fim = meio;
            } else if (reg.chavePrimaria < chavePrimaria) {
                inicio = meio;
            } else {
                RegAchou = reg;
                break;
            }
        }
        file.close();
        return RegAchou;
    }
}
