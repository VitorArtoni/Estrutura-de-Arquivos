package gerenciador.de.arquivos;

import java.io.RandomAccessFile;

/**
 *
 * @author Vitor
 */
class Registro {

    static Registro lerRegistro(RandomAccessFile f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    String posicao;
    
    Campo ISBN;
    Campo titulo;
    Campo autor1;
    Campo autor2;
    Campo autor3;
    Campo ano;   
}