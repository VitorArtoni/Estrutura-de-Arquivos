package gerenciador.de.arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;

public class IndiceRegistro {

    public long chavePrimaria;
    public long posicao;

    public static IndiceRegistro lerIndiceRegistro(RandomAccessFile file) throws IOException {
        IndiceRegistro registro = new IndiceRegistro();
        int contador = 0;
        String dado = "";

        while (contador < 20) {
            char caracter = (char) file.readByte();
            dado = dado + caracter;
            contador++;
        }

        contador = 0;
        dado = dado.trim();
     
        registro.chavePrimaria = Long.parseLong(dado);
        dado = "";

        while (contador < 10) {
            contador++;
            char caracter = (char) file.readByte();
            dado = dado + caracter;
        }

        dado = dado.trim();
        registro.posicao = Long.parseLong(dado);

        return registro;
    }
}
