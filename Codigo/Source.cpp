#include <iostream>
#include <string>
#include <sys/stat.h>

using namespace std;

void gerarArqIndicePrimario() 
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
		arqIndice = fopen("indicesP.txt", "w");

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
				
				if (podeLerRegistros == 1)
				{
					if (ch == 124)//L� at� achar um pipe, quando achar ele para de ler e salva no arquivo
					{
						cout << indice << " " << posicao << "\n";
						fprintf(arqIndice, "%s|%d\n", indice.c_str(), posicao);
						fflush(arqIndice);
						indice = "";	
						podeLerRegistros = 0;
					}
					else//Concatena os caracteres da chave prim�ria 
					{
						indice += ch;
					}
				}

				if (ch == 35)//Quando encontra um #
				{	
					podeLerRegistros = 1;
					posicao = contador;
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

	gerarArqIndicePrimario();

	/*do
	{
		printf("1 - Consultar por chave prim�ria \n");
		printf("2 - Consultar por chave secund�ria \n");
		printf("Digite uma op��o: \n");
		scanf("%d", &op);

		switch (op)
		{

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