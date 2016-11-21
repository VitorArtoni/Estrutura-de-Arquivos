package gerenciador.de.arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Registro {

    public ArrayList<Campo> campos = new ArrayList<>();
    public String posicao;

    public int Tamanho() {
        int tamTotal = 0;
        int i = 0;
        while (i < campos.size()) {
            tamTotal += campos.get(i).dado.length();
            i++;
        }
        return tamTotal;
    }

    public static Registro lerRegistro(RandomAccessFile AF) throws IOException {
        Registro registro = new Registro();
        while (true) {
            Campo campo = Campo.lerCamposDados(AF);
            registro.campos.add(campo);
            if (campo.fReg) {
                break;
            }
        }
        return registro;
    }
}
