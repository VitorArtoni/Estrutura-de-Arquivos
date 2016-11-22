package gerenciador.de.arquivos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class Registro {

    public ArrayList<Campo> campos = new ArrayList<>();
    public String posicao;

    public int getTamanho() {
        int tamTotal = 0;
        int i = 0;
        while (i < campos.size()) {
            tamTotal += campos.get(i).dado.length();
            i++;
        }
        return tamTotal;
    }

    public static Registro lerRegistro(RandomAccessFile file) throws IOException {
        Registro registro = new Registro();
        while (true) {
            Campo campo = Campo.lerCamposDados(file);
            registro.campos.add(campo);
            if (campo.fimDoRegistro) {
                break;
            }
        }
        return registro;
    }
    
    public static void excluirRegistro() throws IOException {
        System.out.println("Digite a Chave Primária do registro que deseja Excluir: ");
        Scanner scan = new Scanner(System.in);
        long chavePri = scan.nextLong();
        IndiceRegistro reg = Indice.realizarBuscaBinaria(chavePri);
        if (reg == null) {
            System.out.println("Registro não encontrado");
            return;
        }
        File f = new File("dados.txt");
        RandomAccessFile AF = new RandomAccessFile(f, "rw");
        AF.seek(reg.posicao);
        Campo campo = Campo.lerCamposDados(AF);
        String excluido = "*";
        excluido = Indice.preencherNaFrente(excluido, campo.dado.length(), '*');
        AF.seek(reg.posicao);
        AF.write(excluido.getBytes());
        System.out.println("Registro Excluído!");
        Indice.gerarIndices();
        EspacoVazio.gravaEspacoVazio();
    }

    public static void inserirRegistro() throws IOException {
        File f = new File("dados.txt");
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
            EspacoVazio.gravaEspacoVazio();
        }
        Indice.gerarIndices();
    }
}
