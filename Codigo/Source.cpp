#include <iostream>
#include <string>
#include <sys/stat.h>

using namespace std;

void gerarArqIndice() 
{
	FILE *arqIndice, *arqDados;
	
	char ch;
	
	arqDados = fopen("dados.txt", "r");

	if (arqDados == NULL) 
	{
		printf("ERRO ARQ DADOS!\n");
		getchar();
	}
	else 
	{
		arqIndice = fopen("indices.txt", "w");

		if (arqIndice == NULL)
		{
			printf("ERRO ARQ INDICES!\n");
			getchar();
		}
		else
		{
			int contador = -1, posicao = 0, podeLerRegistros = 0;
			string indice = "";

			while ((ch = fgetc(arqDados)) != EOF) 
			{
				contador++;
				
				if (contador==37)//Pula o cabeçalho
				{
					posicao = contador - 1;//posicao = 36
					podeLerRegistros = 1;//pode começar a criar o arquivo de indices após o cabeçalho
				}

				if (podeLerRegistros == 1)
				{
					if ((ch == 124) || (ch == 35))//Se encontrar um | ou #
					{
						fflush(arqIndice);
						fprintf(arqIndice, "%s|%d\n", indice.c_str(), posicao);
						indice = "";
							
						posicao = contador;
					}
					else
					{
						indice += ch;
					}
				}
			}

			getchar();
			fclose(arqDados);
			fclose(arqIndice);
		}
	}
}

int main()
{

	int op = 0;

	gerarArqIndice();

	/*	do {

	printf("1 - Consultar por chave primária \n");
	printf("2 - Consultar por chave secundária \n");
	printf("Digite uma opção: \n");
	scanf("%d", &op);

	switch (op) {

	case 1:
	consulta_PK();
	break;

	case 2:
	consulta_SK();
	break;
	}



	}while(op=0);
	*/
}

int consulta_PK() {
	return 0;
}

int consulta_SK() {
	return 0;
}