import java.io.*;
import java.util.*;
public class Main {

    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader("/Users/macbook/Documents/IF_BBC/EAD/Estrutura_de_dados_II/Arquivos_ordenacao/dados_3.txt");
        BufferedReader readFile = new BufferedReader(file);
        FileWriter novoArquivo = new FileWriter("/Users/macbook/Documents/IF_BBC/EAD/Estrutura_de_dados_II/Arquivos_ordenacao/Algoritmos_ord_5/dadosOrdenados_3.txt");
        PrintWriter gravarArquivo = new PrintWriter(novoArquivo);

        gravarArquivo.printf("Desenvolvedor: Clayton Rodrigues Dos Prazeres.\n ___________ \n");
        // preparando o documento

        double contador = 0.0;
        String line = "";
        long tempoInicial;
        long tempoFinal;

        String linha = readFile.readLine();
        while (linha != null) {
            linha = readFile.readLine();
            if (linha != null) {
                line = linha;
            }
        }
        file.close();
        line = line.replace("[", "");
        line = line.replace("]", "");
        line = line.replace(" ", "");
        String[] str = line.split(",");
        int[] dados = new int[str.length];
        iniciarVetor(dados, str);

        //Bucket Sort
        tempoInicial = System.currentTimeMillis();
        bucketSort(dados);
        tempoFinal = System.currentTimeMillis() - tempoInicial;
        System.out.println("Bucket Sort Executado em = " + (tempoFinal) + " ms\nCom " + contador + " movimentos.\n");
        gravarArquivo.printf("\nBucket Sort: \n--------------------\n");
        gravarArquivo(gravarArquivo, contador, tempoFinal, dados);

        //Cocktail sort
        iniciarVetor(dados, str);
        tempoInicial = System.currentTimeMillis();
        cocktailSort(dados);
        tempoFinal = System.currentTimeMillis() - tempoInicial;
        System.out.println("Cocktail Sort Executado em = " + (tempoFinal) + " ms\nCom " + contador + " movimentos.\n");
        gravarArquivo.printf("\nCoktail Sort: \n--------------------\n");
        gravarArquivo(gravarArquivo, contador, tempoFinal, dados);

        //Tim Sort
//        iniciarVetor(dados, str);
//        tempoInicial = System.currentTimeMillis();
//        timSort(dados, dados.length);
//        tempoFinal = System.currentTimeMillis() - tempoInicial;
//        System.out.println("Tim Sort Executado em = " + (tempoFinal) + " ms\nCom " + contador + " movimentos.\n");
//        gravarArquivo.printf("\nTim Sort: \n--------------------\n");
//        gravarArquivo(gravarArquivo, contador, tempoFinal, dados);


        gravarArquivo.close();
    }

    public static void gravarArquivo(PrintWriter gravarArquivo, double contador, long tempoFinal, int dados[]) {
        gravarArquivo.printf("O números de movimentos foi: " + contador + ".\n");
        gravarArquivo.printf("O tempo gasto foi: " + tempoFinal + " ms.\n");
        gravarArquivo.printf("O vetor ordenado é: \n");
        for (int i = 0; i < dados.length; i++) {
            if (i == 0) {
                gravarArquivo.printf("[" + dados[i] + ", ");
            } else if (i == dados.length - 1) {
                gravarArquivo.printf(dados[i] + "]");
            } else {
                gravarArquivo.printf(dados[i] + ", ");
            }
        }
        gravarArquivo.printf("_______________________________\n\n\n");
    }

    public static void iniciarVetor(int dados[], String str[]) {
        for (int i = 0; i < str.length; i++) {
            dados[i] = Integer.parseInt(str[i]);
        }
    }

    public static void cocktailSort(int a[]) {
        boolean swapped = true;
        int start = 0;
        int end = a.length;

        while (swapped == true) {
            // reset the swapped flag on entering the
            // loop, because it might be true from a
            // previous iteration.
            swapped = false;

            // loop from bottom to top same as
            // the bubble sort
            for (int i = start; i < end - 1; ++i) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    swapped = true;
                }
            }

            // if nothing moved, then array is sorted.
            if (swapped == false)
                break;

            // otherwise, reset the swapped flag so that it
            // can be used in the next stage
            swapped = false;

            // move the end point back by one, because
            // item at the end is in its rightful spot
            end = end - 1;

            // from top to bottom, doing the
            // same comparison as in the previous stage
            for (int i = end - 1; i >= start; i--) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                    swapped = true;
                }
            }

            // increase the starting point, because
            // the last stage would have moved the next
            // smallest number to its rightful spot.
            start = start + 1;
        }
    }
    public static int[] bucketSort(int[] lista) {

        //CONTA A QTD DE NÚMEROS NEGATIVOS E POSITIVOS EM 'LISTA'
        int qtdNeg = 0, qtdPos = 0;

        for(int i = 0; i < lista.length; i++) {

            if(lista[i] < 0) {
                qtdNeg++;
            }
            else {
                qtdPos++;
            }
        }

        //POPULA OS VETORES DE NEGATIVOS E POSITIVOS
        int[] negativos = new int[qtdNeg];
        int[] positivos = new int[qtdPos];

        int indexNeg = 0, indexPos = 0;

        for(int i = 0; i < lista.length; i++) {

            if(lista[i] < 0) {

                negativos[indexNeg] = lista[i];
                indexNeg++;
            }
            else {

                positivos[indexPos] = lista[i];
                indexPos++;
            }
        }


        int maxValue = maxValue(lista);
        int minValue = minValue(lista);

        //ORDENANDO 'NEGATIVOS'
        int[] bucket_neg = new int[minValue + 1];
        int[] sorted_numsNeg = new int[negativos.length];
        int[] neg_sorted = new int[negativos.length];

        for(int i = 0; i < negativos.length; i++) {
            negativos[i] *= -1;
        }

        for(int i = 0; i < negativos.length; i++)
            bucket_neg[negativos[i]]++;

        int outPosition_neg = 0;

        for(int i = 0; i < bucket_neg.length; i++)
            for (int j = 0; j < bucket_neg[i]; j++)
                sorted_numsNeg[outPosition_neg++] = i;

        for(int i = 0; i < negativos.length; i++) {
            sorted_numsNeg[i] *= -1;
        }

        int idx = 0;

        for(int i = negativos.length-1; i >= 0; i--) {

            neg_sorted[idx] = sorted_numsNeg[i];
            idx++;
        }

        //ORDENANDO 'POSITIVOS'
        int[] bucket_pos = new int[maxValue + 1];
        int[] sorted_numsPos = new int[positivos.length];

        for(int i = 0; i < positivos.length; i++)
            bucket_pos[positivos[i]]++;

        int outPosition_pos = 0;

        for(int i = 0; i < bucket_pos.length; i++)
            for (int j = 0; j < bucket_pos[i]; j++)
                sorted_numsPos[outPosition_pos++] = i;

        //ORDENANDO 'LISTA'
        int aux = 0;

        for(int i = 0; i < neg_sorted.length; i++) {

            lista[aux] = neg_sorted[i];
            aux++;
        }

        for(int i = 0; i < sorted_numsPos.length; i++) {

            lista[aux] = sorted_numsPos[i];
            aux++;
        }


        return lista;
    }

    private static int maxValue(int[] lista) {

        int maxValue = lista[0];

        for(int i = 0; i < lista.length; i++)

            if(lista[i] > maxValue)
                maxValue = lista[i];

        return maxValue;
    }

    private static int minValue(int[] lista) {

        int minValue = lista[0];

        for(int i = 0; i < lista.length; i++)

            if(lista[i] < minValue)
                minValue = lista[i];

        return Math.abs(minValue);
    }

    static int MIN_MERGE = 64;

    public static int minRunLength(int n)
    {
        assert n >= 0;

        // Becomes 1 if any 1 bits are shifted off
        int r = 0;
        while (n >= MIN_MERGE){
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    // This function sorts array from left index to
    // to right index which is of size atmost RUN
    public static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++){
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    // Merge function merges the sorted runs
    public static void merge(int[] arr, int l,int m, int r) {
        // Original array is broken in two parts
        // left and right array
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        for (int x = 0; x < len1; x++){
            left[x] = arr[l + x];
        }
        for (int x = 0; x < len2; x++){
            right[x] = arr[m + 1 + x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        // After comparing, we merge those two array
        // in larger sub array
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            }
            else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements
        // of left, if any
        while (i < len1){
            arr[k] = left[i];
            k++;
            i++;
        }

        // Copy remaining element
        // of right, if any
        while (j < len2){
            arr[k] = right[j];
            k++;
            j++;
        }
    }

    // Iterative Timsort function to sort the
    // array[0...n-1] (similar to merge sort)
    public static void timSort(int[] arr, int n){
        int minRun = minRunLength(MIN_MERGE);

        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += minRun){
            insertionSort(arr, i, Math.min((i + 31), (n - 1)));
        }
        // Start merging from size
        // RUN (or 32). It will
        // merge to form size 64,
        // then 128, 256 and so on
        // ....
        for (int size = minRun; size < n; size = 2 * size){
            // Pick starting point
            // of left sub array. We
            // are going to merge
            // arr[left..left+size-1]
            // and arr[left+size, left+2*size-1]
            // After every merge, we
            // increase left by 2*size
            for (int left = 0; left < n;left += 2 * size){
                // Find ending point of left sub array
                // mid+1 is starting point of right sub
                // array
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1),(n - 1));
                // Merge sub array arr[left.....mid] &
                // arr[mid+1....right]
                merge(arr, left, mid, right);
            }
        }
    }
}
