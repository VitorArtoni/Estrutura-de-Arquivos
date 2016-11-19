package gerenciador.de.arquivos;

import java.io.RandomAccessFile;


/**
 *
 * @author Vitor
 */
class Registro {
    
        public int Tam(){
		int tamanhoTotal = 0;
		int i = 0;
		while(i < camp.size()){//faz uma verificação do tamaho do campo
			tamanhoTotal += camp.get(i).dado.length();//recebe tamanho
			i++;
		}
		return tamanhoTotal;
	}
    
    static Registro lerRegistro(RandomAccessFile arq) {
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