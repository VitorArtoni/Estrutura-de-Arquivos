package gerenciador.de.arquivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author Vitor
 */
class Indice {

    public final static String ArqDados = "dados.txt";
    public final static String ArqIndiceP = "indiceP.txt";
    public final static String ArqIndiceTitulo = "indiceTitulo.txt";
    public final static String ArqIndiceA1 = "indiceA1.txt";
    public final static String ArqIndiceA2 = "indiceA2.txt";
    public final static String ArqIndiceA3 = "indiceA3.txt";
    public final static String ArqIndiceAno = "indiceAno.txt";

    static void gerarIndice() throws FileNotFoundException, IOException {
        File arqD = new File(ArqDados);
        RandomAccessFile f = new RandomAccessFile(arqD, "rw");
        ArrayList<Registro> listaReg = new ArrayList<Registro>();
        f.seek(0);

        while (f.getFilePointer() < f.length()) {
            int contador = 0;
            String posicao = String.valueOf(f.getFilePointer());
            Registro reg = Registro.lerRegistro(f);
            reg.posicao = posicao;

            if (listaReg.size() == 0) {
                listaReg.add(reg);
            } else {
                boolean confirma = false;
                while (contador < listaReg.size()) {
                    if ((Long.parseLong(reg.ISBN.dado) < (Long.parseLong(listaReg.get(contador).ISBN.dado)))) {
                        confirma = true;
                        listaReg.add(contador, reg);
                        break;
                    } else {
                        contador++;
                    }
                }
                if (confirma == false) {
                    listaReg.add(reg);
                }
            }
        }
        f.close();

        new File(ArqIndiceP).delete();
        File novoArqI = new File(ArqIndiceP);
        RandomAccessFile g = new RandomAccessFile(novoArqI, "rw");
        g.seek(0);

        for (Registro reg : listaReg) {
            String PK = preencherNaFrente(reg.ISBN.dado, 13, '*');
            g.write(PK.getBytes());
            String posicao = preencherNaFrente(reg.posicao, 10, '*');
            g.write(posicao.getBytes());
        }
        g.close();
    }

    private static String preencherNaFrente(String dado, int tamanho, char carac) {
        int c = 0;
        while (c < (tamanho-dado.length())) {
            dado += carac;
            c++;
        }
        return dado;
    }

    private static String preencherAtras(String dado,int tamanho,char carac) {
        String antes="";
        int c=0;
        while(c<(tamanho-dado.length())){
            antes+=carac;
            c++;
        }
        return antes + dado;
    }
}
