#include <stdio.h>
#include <math.h>

int main () {
	
	int w = 0;
	int n = 0;
	
	do {
		printf("\n	ESTRUTURA DE ARQUIVOS\n\n");
		printf("MENU:\n\n");
		printf("1 - Consultar registro por chave primária (ISBN)\n");
		printf("2 - Consultar registro por chave secundária (TITULO)\n");
		printf("3 - Consultar registro por chave secundária (AUTOR1)\n");
		printf("4 - Consultar registro por chave secundária (AUTOR2)\n");
		printf("5 - Consultar registro por chave secundária (AUTOR3)\n");
		printf("6 - Consultar registro por chave secundária (ANO)\n");
		printf("7 - Inserir registro\n");
		printf("8 - Remover registro por chave primária\n");
		printf("9 - Visualizar índices\n");	
		printf("10 - Sair\n\n");
		printf("Digite o valor correspondente a funcionalidade desejada: \n\n");
		scanf("%d" , &w);

		switch (w) {
			case 1:
				printf("ISBN: ");
				scanf("%d", &n);
				//Codigo consulta por P.K.
				break;

			case 2:
				printf("Titulo: ");
				scanf("%d", &n);
				//codigo consulta por TITULO
				break;

			case 3:
				printf("Autor1: ");
				scanf("%d", &n);
				//codigo consulta por AUTOR1
				break;

			case 4:
				printf("Autor2: ");
				scanf("%d", &n);
				//codigo consulta por AUTOR2
				break;

			case 5:
				printf("Autor3: ");
				scanf("%d", &n);
				//codigo consulta por AUTOR3
				break;

			case 6:
				printf("Ano: ");
				scanf("%d", &n);
				//codigo consulta por Ano
				break;

			case 7:
				printf("Registro a ser inserido: ");
				scanf("%d", &n);
				//codigo inserção
				break;

			case 8:
				printf("Chave Primaria do Registro a ser removido: ");
				scanf("%d", &n);
				//codigo remocao
				break;

			case 9:
				// Codigo visualizacao indices
				break;

			case 10:
				break;

			default:
				printf("Opção inválida! Tente novamente!\n\n");
				break;

		}
	}while (w!=10);

	printf("\nSistema Finalizado!\n\n");
	
	return 0;
}
