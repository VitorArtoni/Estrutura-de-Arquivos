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
				
				if (contador==37)//Pula o cabe�alho
				{
					posicao = contador - 1;//posicao = 36
					podeLerRegistros = 1;//pode come�ar a criar o arquivo de indices ap�s o cabe�alho
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

	printf("1 - Consultar por chave prim�ria \n");
	printf("2 - Consultar por chave secund�ria \n");
	printf("Digite uma op��o: \n");
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