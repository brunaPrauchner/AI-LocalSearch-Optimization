//Bruna Vargas 15105058
package alggenetico;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class AlgGenetico {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        //Ler r = new Ler();
        int cicloPop = 8;
        int ciclo = 0;
        int tempoEv[] = null;
        String evento = "";
        String [] campos = null;
        int[] result = null;
        System.out.println("Digite 1 para ler arquivo de texto \nDigite 2 para digitar manualmente");
        int op = in.nextInt();
        switch (op) {
            case 1:
                try {
                    //SELECIONAR APENAS UMA LINHA PARA LER UM DOS 3 ARQUIVOS DISPONÍVEIS
                    Scanner t = new Scanner(new FileReader("ex1-7ev.txt"));
                    //Scanner t = new Scanner(new FileReader("ex2-13ev.txt"));
                    //Scanner t = new Scanner(new FileReader("ex3-20ev.txt"));
                    while (t.hasNext()) {
                        String linha = t.next();
                        campos = linha.split(";");
                        System.out.println(linha);
                    }
                    result = new int[campos.length];
                    for (int j = 0; j < campos.length; j++) {
                        result[j] = Integer.parseInt(campos[j]);
                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    System.err.println(" Error opening file. ");
                    System.exit(0);
                }
                Eventos event = new Eventos((result.length), result);
                event.geraPop();//1ª populacao criada
                System.out.println(event.toString());
                //a  cada while cria uma nova populacao
                while (ciclo < cicloPop) {
                    event.probabilidade();
                    event.crossOver();
                    event.mutacao();
                    System.out.println("GEROU MAIS UMA POPULACAO");
                    System.out.println(event.toString());
                    ciclo++;
                }
                break;
            case 2:
                System.out.println("Quantos eventos tem?");
                int qnt = in.nextInt();
                tempoEv = new int [qnt];
                //tempoEv [evento1 = 30min,evento2 = 50min,evento3 = 20min]

                for (int i = 0; i < qnt; i++) {
                    System.out.println("Duração do Evento " + i + ": ");
                    tempoEv[i] = in.nextInt();
                    System.out.println("O evento " + i + " tem duração de: " + tempoEv[i]);
                }

                Eventos event2 = new Eventos(qnt, tempoEv);
                event2.geraPop();//1ª populacao criada
                System.out.println(event2.toString());
                //a  cada while cria uma nova populacao
                while (ciclo < cicloPop) {
                    event2.probabilidade();
                    event2.crossOver();
                    event2.mutacao();
                    System.out.println("GEROU MAIS UMA POPULACAO");
                    System.out.println(event2.toString());
                    ciclo++;
                }
                break;
            default:
                break;
        }
    }
}