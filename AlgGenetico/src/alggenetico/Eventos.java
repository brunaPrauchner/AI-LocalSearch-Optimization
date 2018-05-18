//Bruna Vargas 15105058
package alggenetico;

import java.util.Random;
import java.util.ArrayList;

//o auditório funciona por 12 horas
//720 minutos são 12 horas

public class Eventos{
    Random r = new Random();
    private int n; //numero de eventos
    private int ev[]; // tem todos os eventos que querem entrar, cada posicao é o tempo
    private int solucao[]; //vetor que diz qual evento entrou ou não
    //se for 0 aquele evento nao entrou
    private int nPop = 10;
    private double[] probPerCent;
    private double[] prob;
    private int melhorFit = 0;
    
    ArrayList<int[]> populacao = new ArrayList<>();
    int soma = 0;
    int[] result;
    
    //construtor
    public Eventos(int qnt, int[] vetor){
        n = qnt;
        ev = vetor;
        solucao = new int[n];
        prob = new double[nPop];
        result = new int[n];
        probPerCent = new double[nPop];
    }

    public int[] criaSol(){
    //preenche os vetores com 0 ou 1 aleatoriamente
    //criando solução
        int[] vAux = new int [n];
        do{
            for(int i = 0; i<n; i++){
                vAux[i] = r.nextInt(2);
            }
        //qndo passar do limite das 12 horas fica calculando de novo
        }while(fitness(ev, vAux)>720);
        return vAux;
    }

    public int fitness (int vetorTempo[], int vetorBool[]){
        //calcula o tempo da solução
        int sAux = 0;
        for(int i = 0; i<n; i++){
            result[i] = vetorBool[i] * vetorTempo[i];
            sAux += result[i];
        }
        return sAux;
    }
    
    public void geraPop(){
        //uma geração são várias soluções
        //um arrayList onde cada posição é um vetor que é uma solução
        //10 solucoes
        //é uma população
        for(int i = 0; i<nPop; i++){
            populacao.add(criaSol());
        }
    }
    
    public void probabilidade(){
        //tem a probabilidade de cada um ser escolhido
        //vetor de eventos com a posicao de cada um na populacao
        int acum = 0;
        for(int i = 0; i<nPop; i++){
            acum += fitness(ev, populacao.get(i));
        }
        for(int m = 0; m< populacao.size(); m++){
            prob[m] = ((double)fitness(ev, populacao.get(m))/(double)acum)*100;
        }
        probPerCent[0] = prob[0];
        for(int z = 1; z<nPop; z++){
                            //pos anterior + atual
            probPerCent[z] = probPerCent[z-1] + prob[z];
        }
    }
    @Override
    public String toString(){
        String s = "";
        for(int i = 0; i<10; i++){
            int aux[] = populacao.get(i);
            for(int j = 0; j<n; j++){
                s += aux[j];
            }
        s += " fitness: " +  fitness(ev, populacao.get(i));
        s += "\n";
        if (fitness(ev, populacao.get(i)) > melhorFit){
            melhorFit = fitness(ev, populacao.get(i));
        }
        }
        s += melhorFit;
        return s;
    }

    public void crossOver(){
        ArrayList<int[]> newPop = new ArrayList<>();
        for (int h = 0; h < nPop; h++) {
            int pai1 = 0;
            int pai2 = 0;
            int pontoDeCorte = r.nextInt(n - 1) + 1;
            int[] newV1 = new int[n];
            int[] newV2 = new int[n];
            int[] cruzado = new int[n];
            do {
                do {
                    pai1 = r.nextInt(100);
                    pai2 = r.nextInt(100);
                    int q = 0;
                    for (int i = 0; i < probPerCent.length; i++) {
                        if (pai1 <= probPerCent[i]) {
                            for (int k = 0; k < pontoDeCorte; k++) {
                                newV1[k] = populacao.get(i)[k];
                                cruzado[q] = newV1[k];
                                q++;
                            }
                            break;
                        }
                    }
                    for (int i = 0; i < probPerCent.length; i++) {
                        if (pai2 <= probPerCent[i]) {
                            for (int j = pontoDeCorte; j < n; j++) {
                                newV2[j] = populacao.get(i)[j];
                                cruzado[q] = newV2[j];
                                q++;
                            }
                            break;
                        }
                    }
                } while (fitness(ev, cruzado) > 720);
            } while (pai1 == pai2);
            newPop.add(cruzado);
        }
        populacao = newPop;
    }
    
    public void mutacao(){
        for (int j = 0; j < nPop; j++) {
            int taxa = 10;//taxa de mutacao
            int numero = r.nextInt(101);
            do{
                int mudarPos = r.nextInt(n);
                if (numero <= taxa) {
                    populacao.get(j)[mudarPos] = populacao.get(j)[mudarPos] == 1 ? 0 : 1;
                }
            } while (fitness(ev, populacao.get(j)) > 720);
        }
        //(Expressão) ? ValorTrue : ValorFalse
        //se for =1 coloca 0 se não, coloca 1
    }
}