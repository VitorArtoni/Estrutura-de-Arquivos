package gerenciador.de.arquivos;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Campo {

    public String dado;
    public Boolean fReg;

    public static Campo lerCamposDados(RandomAccessFile AF) throws IOException {
        char ch = 'a';
        String campo = "";
        boolean fReg = false;

        for (; ch != '#' && ch != '|';) { // lupe para ler o dado
            ch = (char) AF.readByte();//lê um byte do arquivo
            if (ch != '#' && ch != '|') { //verifica as condições  de o caracter é diff de # e |
                campo += ch; //campo recebe o caracter
            } else if (ch == '#') { // encontra o fime do registo
                fReg = true;//Fim de registro
            }
        }

        Campo camp = new Campo();
        camp.dado = campo;//preenche o objeto campo com dado
        camp.fReg = fReg;//preenche o objeto com o fim do reg
        return camp;// retorna o  objeto campo
    }
}
