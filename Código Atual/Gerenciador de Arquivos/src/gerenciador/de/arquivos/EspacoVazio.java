package gerenciador.de.arquivos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class EspacoVazio {

    public long tamanho;
    public long posicao;

    public static void mostraEspacosVazios() throws IOException {
        File arq = new File("espacosVazios.txt");
        RandomAccessFile file = new RandomAccessFile(arq, "rw");
        file.seek(0);

        System.out.println("Tam----------Pos");
        System.out.println("----------------");

        while (file.getFilePointer() < file.length()) {
            String tamanhoLocal = "";
            String posicaoLocal = "";

            for (int i = 0; i < 10; i++) {
                char character = (char) file.read();
                tamanhoLocal += character;
            }

            for (int i = 0; i < 10; i++) {
                char character = (char) file.read();
                posicaoLocal += character;
            }
            System.out.println(tamanhoLocal.trim() + "\t" + posicaoLocal.trim());
        }
        file.close();
    }

    public static EspacoVazio worstFit() throws IOException {
        String ArqEVazio = "espacosVazios.txt", tamanhoLocal = "", posicaoLocal = "";
        File arq = new File(ArqEVazio);
        RandomAccessFile file = new RandomAccessFile(arq, "rw");
        if (file.length() == 0) {
            return null;
        }
        file.seek(0);

        for (int i = 0; i < 10; i++) {
            char caracter = (char) file.read();
            tamanhoLocal += caracter;
        }

        for (int i = 0; i < 10; i++) {
            char character = (char) file.read();
            posicaoLocal += character;
        }
        file.close();
        EspacoVazio espacovazio = new EspacoVazio();
        espacovazio.posicao = Long.parseLong(posicaoLocal.trim());
        espacovazio.tamanho = Long.parseLong(tamanhoLocal.trim());
        return espacovazio;
    }

    public static void gravaEV() throws IOException {
        String ArqDados = "dados.txt";
        String ArqEVazio = "espacosVazios.txt";

        File arq = new File(ArqDados);
        RandomAccessFile file = new RandomAccessFile(arq, "rw");
        file.seek(0);

        Registro.lerRegistro(file);
        ArrayList<Registro> listaRegistro = new ArrayList<>();

        while (file.getFilePointer() < file.length()) {
            String posicaoLocal = String.valueOf(file.getFilePointer());
            Registro registro = Registro.lerRegistro(file);
            registro.posicao = posicaoLocal;
            String ChavePri = registro.campos.get(0).dado;
            if (ChavePri.contains("*")) {
                listaRegistro.add(registro);
            }
        }

        file.close();
        new File(ArqEVazio).delete();
        File novoArq = new File(ArqEVazio);
        RandomAccessFile novoFile = new RandomAccessFile(novoArq, "rw");
        novoFile.seek(0);

        while (true) {
            if (listaRegistro.isEmpty()) {
                break;
            }

            Registro maior = null;

            for (int i = 0; i < listaRegistro.size(); i++) {

                Registro atual = listaRegistro.get(i);

                if ((maior == null) || (maior.Tamanho() < atual.Tamanho())) {
                    maior = atual;
                }
            }
            
            String Tamanho = Indice.preencherNaFrente(String.valueOf(maior.Tamanho()), 10, ' ');
            novoFile.write(Tamanho.getBytes());
            
            String pos = Indice.preencherNaFrente(maior.posicao, 10, ' ');
            novoFile.write(pos.getBytes());
            listaRegistro.remove(maior);
        }
        novoFile.close();
    }
}
