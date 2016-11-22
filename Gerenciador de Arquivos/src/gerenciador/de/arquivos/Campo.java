package gerenciador.de.arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Campo {

    public String dado;
    public Boolean fimDoRegistro;

    public static Campo lerCamposDados(RandomAccessFile file) throws IOException {
        char caracter = 'a';
        String campo = "";
        boolean fReg = false;

        for (; caracter != '#' && caracter != '|';) { 
            caracter = (char) file.readByte();
            if (caracter != '#' && caracter != '|') {
                campo += caracter; 
            } else if (caracter == '#') { 
                fReg = true;
            }
        }

        Campo camp = new Campo();
        camp.dado = campo;
        camp.fimDoRegistro = fReg;
        return camp;
    }
}
