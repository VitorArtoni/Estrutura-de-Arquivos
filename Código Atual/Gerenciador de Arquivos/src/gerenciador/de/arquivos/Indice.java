package gerenciador.de.arquivos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class Indice {

    public final static String ArqDados = "dados.txt";
    public final static String ArqInd = "indice.txt";
    public final static String arqEVazio = "espacosVazios.txt";

    public static void gerarIndices() throws IOException {

        File arq = new File("dados.txt");

        RandomAccessFile arquivo = new RandomAccessFile(arq, "rw");
        arquivo.seek(0);

        ArrayList<Registro> listaIndices;
        listaIndices = new ArrayList<>();

        Registro.lerRegistro(arquivo);

        while (arquivo.getFilePointer() < arquivo.length()) {
            String posicao = String.valueOf(arquivo.getFilePointer());

            Registro reg = Registro.lerRegistro(arquivo);
            reg.posicao = posicao;

            if (reg.campos.get(0).dado.contains("*")) {
                continue;
            }

            int contador = 0;

            if (listaIndices.isEmpty()) {
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
            String chaveP = preencherNaFrente(registro.campos.get(0).dado, 20, '*');
            String str = preencherNaFrente(registro.posicao, 10, '*');

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

    public static String preencherAtras(String registro, int tamanho, char caracter) {
        String s = "";

        int qtd = (tamanho - registro.length());
        int x = 0;

        while (x < qtd) {
            x++;
            s += caracter;
        }
        return s + registro;
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

            System.out.println(chavePrimaria.trim() + "\t" + Posicao.trim());
        }
        arqIndice.close();
    }
    
    public static void buscarPorChavePrimaria() throws IOException {
        System.out.println("Digite a Chave Primária do registro que deseja buscar: ");
        Scanner scan = new Scanner(System.in);
        long chavePri = scan.nextLong();
        IndiceRegistro reg = realizarBuscaBinaria(chavePri);
        if (reg == null) {
            System.out.println("Registro not found!");
            return;
        }
        File f = new File(ArqDados);
        RandomAccessFile AF = new RandomAccessFile(f, "rw");
        AF.seek(0);
        Registro regCampos = Registro.lerRegistro(AF);
        AF.seek(reg.posicao);
        Registro novo = Registro.lerRegistro(AF);
        for (int i = 0; i < novo.campos.size(); i++) {
            System.out.println(regCampos.campos.get(i).dado + ":" + novo.campos.get(i).dado);
        }
        AF.close();
    }
    
    public static IndiceRegistro realizarBuscaBinaria(long chavePri) throws IOException {
        File f = new File(ArqInd);
        RandomAccessFile AF = new RandomAccessFile(f, "rw");
        AF.seek(0);
        long totalReg = AF.length() / 30;
        long inicio = 0;
        long fim = totalReg - 1;
        IndiceRegistro RegAchou = null;
        while (true) {
            if (fim - inicio == 1) {

                AF.seek(inicio * 30);//vai para a posição do reg inicio*30
                IndiceRegistro reg = IndiceRegistro.lerIndiceRegistro(AF);
                if (reg.chavePrimaria == chavePri) {
                    RegAchou = reg;
                    break;
                }
                AF.seek(fim * 30);
                reg = IndiceRegistro.lerIndiceRegistro(AF);
                if (reg.chavePrimaria == chavePri) {
                    RegAchou = reg;
                    break;
                }
                break;
            }
            long meio = ((fim - inicio) / 2) + inicio;
            AF.seek(meio * 30);
            IndiceRegistro reg = IndiceRegistro.lerIndiceRegistro(AF);
            if (reg.chavePrimaria > chavePri) {
                fim = meio;
            } else if (reg.chavePrimaria < chavePri) {
                inicio = meio;
            } else {
                RegAchou = reg;
                break;
            }
        }
        AF.close();
        return RegAchou;
    }

    public static void excluirRegistro() throws IOException {
        System.out.println("Digite a Chave Prim�ria do registro que deseja Excluir: ");
        Scanner scan = new Scanner(System.in);
        long chavePri = scan.nextLong();
        IndiceRegistro reg = realizarBuscaBinaria(chavePri);
        if (reg == null) {
            System.out.println("Registro não encontrado");
            return;
        }
        File f = new File(ArqDados);
        RandomAccessFile AF = new RandomAccessFile(f, "rw");
        AF.seek(reg.posicao);
        Campo campo = Campo.lerCamposDados(AF);
        String excluido = "*";
        excluido = Indice.preencherNaFrente(excluido, campo.dado.length(), '*');
        AF.seek(reg.posicao);
        AF.write(excluido.getBytes());
        System.out.println("Registro Excluído!");
        Indice.gerarIndices();
        EspacoVazio.gravaEV();
    }
    
    public static void inserirRegistro() throws IOException {
        File f = new File(ArqDados);
        RandomAccessFile AF = new RandomAccessFile(f, "rw");
        AF.seek(0);
        Registro reg = Registro.lerRegistro(AF);
        int i;
        Scanner ler = new Scanner(System.in);
        ArrayList<String> valores = new ArrayList<String>();
        int tam = 0;
        for (i = 0; i < reg.campos.size(); i++) {
            System.out.print(reg.campos.get(i).dado + ": ");
            String val = ler.nextLine();
            valores.add(val);
            tam += val.length();
        }
        EspacoVazio ev = EspacoVazio.worstFit();
        boolean isEv = false;
        if (ev == null || tam > ev.tamanho) {
            AF.seek(AF.length());
        } else {
            AF.seek(ev.posicao);
            isEv = true;
        }
        if (isEv) {
            for (int j = 0; j < (ev.tamanho + reg.campos.size() - 1); j++) {
                AF.write(" ".getBytes());
            }
            AF.seek(ev.posicao);
        }
        for (i = 0; i < valores.size(); i++) {
            AF.write(valores.get(i).getBytes());
            if (i == valores.size() - 1) {
                if (!isEv) {
                    AF.write("#".getBytes());
                }
            } else {
                AF.write("|".getBytes());
            }
        }
        AF.close();
        if (isEv) {
            EspacoVazio.gravaEV();
        }
        Indice.gerarIndices();
    }

    static void buscarPorChaveSecundaria() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
