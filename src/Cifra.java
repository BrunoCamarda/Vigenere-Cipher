
/**
 * @author Bruno Camarda - Matrícula 14.1.8092
 * Engenharia da Computação
 * Exercício Enviado pelo Moodle - 21/05/2017
 */
import java.io.IOException;
import java.util.Scanner; 
import java.io.*;
import java.text.Normalizer; 

public class Cifra {
char[] alfabeto = 
{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v','w', 'x', 'y', 'z'};
	

//Funcao para escrever as frases no arquivo "mensagem.txt"
    public void EscreveMensagem() throws IOException {
    try {
	Scanner in = new Scanner(System.in); 
	FileWriter arq = new FileWriter("mensagem.txt"); 
	BufferedWriter escritor = new BufferedWriter(arq); 
	String linha = ""; 
        System.out.print("Insira uma frase a ser salva no arquivo (fim para acabar): ");
	linha = in.nextLine();	
//impede o salvamento de palavras com acentos nas letras
	linha = linha.toLowerCase();
        linha = Normalizer.normalize(linha, Normalizer.Form.NFKD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            while (!linha.equals("fim")){
		escritor.write(linha);
		escritor.newLine();
                System.out.print("Insira outra frase a ser salva no arquivo (fim para acabar): ");
		linha = in.nextLine();
                
//impede o salvamento de palavras com acentos nas letras
//No meu NetBeans so funcionou assim, mas no Eclipse 
//usei a funcao 
// linha = Normalizer.normalize(linha, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""); 
//Professora, talvez no seu computador seja melhor dessa forma! ^

                linha = linha.toLowerCase(); 
                linha = Normalizer.normalize(linha, Normalizer.Form.NFKD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            }//fim while
        escritor.close();
	System.out.println("acabou");
	
    }catch(IOException e){
	e.printStackTrace();
    }
    }//Fim funcao EscreveMensagem
	
//recebe uma letra e retorna a posicao dela no alfabeto;
// Ex.: A = 1; B = 2; C = 3;
    public int Posicao (char x){ 
	int posicao = 0; 
            for (int i=0; i<26; i++){ 
		if (alfabeto[i] == x) { 
                    posicao = i+1; 
		}
            }
	return posicao; 
    }
	
//Pega a  frase digitada e  a palavra-chave e substitui as letras da frase pelas letras da palavra-chave
//considerando espacos
//Em seguida, faz a substituicao com base na posicao das letras e exibe a frase criptografada;
    public void GeraFraseCript(String chave, String frase) throws IOException{ 
	char[] mensagem = new char[frase.length()]; 
	char[] frase_aux = frase.toCharArray();
	char[] chave_aux = chave.toCharArray();
	int tam = chave.length();
	char espaco = ' '; 
	int j = 0;
            for (int i=0; i < frase.length(); i++){
		if (frase_aux[i] == espaco ){ 
			mensagem[i] = espaco ; 
		}else {
                    if (j >= tam){ 
			j = 0;
			mensagem[i] = chave_aux[j]; 
			j++; 			
                    }else{ 
			mensagem[i] = chave_aux[j]; 
			j++; 				
                    }
		}	
            }
//A partir daqui comeca a fazer a criptgrafia, pegando como base as letras trocadas
//Usando calculo com base na posicao das letras da frase e da chave
	char[] mensagem_final = new char[frase.length()]; 		
            for (int i=0; i<frase.length(); i++){ 
		int posicao_frase = Posicao(frase_aux[i]); 
		int posicao_chave = Posicao(mensagem[i]); 
                    if (mensagem[i] != espaco){
                        if ((posicao_frase) <= (26 - (posicao_chave - 1))){ 
                            mensagem_final[i]  = alfabeto[(posicao_chave - 1) + (posicao_frase - 1)]; 
			} else { 
                            mensagem_final[i] = alfabeto[(posicao_frase - (26 - (posicao_chave - 1)) - 1)];
			}
                    }else{ 
			mensagem_final[i] = espaco;
                    }
            }
        //Enviando frases para o arquivo cifrado.txt
        //o true no FileWriter permite escrever no arquivo ao final dele
        //sem apagar os dados anteriores.
        try{
            FileWriter cifra = new FileWriter("cifrado.txt", true);
            BufferedWriter cw = new BufferedWriter(cifra);
            cw.write(mensagem_final);
            cw.newLine();
            cw.flush();
            cw.close();
        }catch(IOException e){ 
            e.printStackTrace();
        }
    } // fim função GeraFraseScript
	
    public void GeraFraseDescript(String chave, String frase) throws IOException{ 
        char[] mensagem = new char[frase.length()]; 
	char[] frase_aux = frase.toCharArray();
	char[] chave_aux = chave.toCharArray();
	int tam = chave.length();
	char espaco = ' '; 
	int j = 0;
            for (int i=0; i < frase.length(); i++){
		if (frase_aux[i] == espaco ){ 
			mensagem[i] = espaco ; 
		}else {
                    if (j >= tam){ 
			j = 0;
			mensagem[i] = chave_aux[j]; 
			j++; 			
                    }else{ 
			mensagem[i] = chave_aux[j]; 
			j++; 				
                    }
		}	
            }
//A partir daqui comeca a fazer a descriptgrafia, pegando como base as letras trocadas
//Usando calculo com base na posicao das letras da frase e da chave
	char[] mensagem_final = new char[frase.length()]; 		
            for (int i=0; i<frase.length(); i++){ 
		int posicao_frase = Posicao(frase_aux[i]); 
		int posicao_chave = Posicao(mensagem[i]); 
                    if (mensagem[i] != espaco){
                        if (posicao_frase >= posicao_chave ){ 
                            mensagem_final[i]  = alfabeto[(posicao_frase  - posicao_chave) ]; 
                        
                        } else { 
                            mensagem_final[i] = alfabeto[(26 - (posicao_chave ) + posicao_frase)];
			}
                    }else{ 
			mensagem_final[i] = espaco;
                    }
            }
        //Enviando frases para o arquivo mensagem2.txt
        //o true no FileWriter permite escrever no arquivo ao final dele
        //sem apagar os dados anteriores.
        try{
            FileWriter cifra = new FileWriter("mensagem2.txt", true);
            BufferedWriter cw = new BufferedWriter(cifra);
            cw.write(mensagem_final);
            cw.newLine();
            cw.flush();
            cw.close();
        }catch(IOException e){ 
            e.printStackTrace();
        }
    }//Fim funcao GeraFraseDescript
	
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner (System.in);
        Cifra p = new Cifra(); 
        p.EscreveMensagem(); 
        //Gilda, tentei usar um switch e fazer um menu de opcao 
        //para o usuário decidir entre encri ou descri
        //mas por algum motivo meu código estava dando erro
        //de ArrayOutofBounds e então preferi deixar dessa forma.
        
            //Encriptografar: 
                System.out.print("Palavra-chave: ");
                String chave = in.nextLine(); 
                try { 
                FileReader arq = new FileReader("mensagem.txt");
                BufferedReader fw = new BufferedReader(arq);
                String linha = "";
            //leitura de cada linha no arquivo
                while (fw.ready()) {
                    linha = fw.readLine();
                    p.GeraFraseCript(chave, linha);
                }
                fw.close(); 
                }catch(IOException e){ 
                    e.printStackTrace();
                }
                    
            //Descriptografar
                System.out.print("Descriptografia:\nPalavra-chave: ");
                chave = in.nextLine(); 
                try { 
                FileReader arq = new FileReader("cifrado.txt");
                BufferedReader fw = new BufferedReader(arq);
                String linha = "";
            //leitura de cada linha no arquivo
                while (fw.ready()) {
                    linha = fw.readLine();
                    p.GeraFraseDescript(chave, linha);
                }
                fw.close(); 
                }catch(IOException e){ 
                    e.printStackTrace();
                }
   }//fim main
} //Fim programa

    
