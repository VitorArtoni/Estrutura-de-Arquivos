package gerenciador.de.arquivos;

import java.io.RandomAccessFile;
import 

/**
 *
 * @author Vitor
 */
class Registro {

    static Registro lerRegistro(RandomAccessFile f) {
       Registro re = new Registro();
        Campo camp= Campo.lerDados(arq);
        re.ISBN=camp;
        re.titulo=camp;
        re.autor1=camp;
        re.autor2=camp;
        re.autor3=camp;
        re.ano=camp;
        
        return re;
        
    }
    
    String posicao;
    
    Campo ISBN;
    Campo titulo;
    Campo autor1;
    Campo autor2;
    Campo autor3;
    Campo ano;   
}