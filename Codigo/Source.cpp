#include <iostream>
#include <string>
#include <sys/stat.h>
#include <fstream>
#include <sstream>

using namespace std;

typedef struct registro
{
	string ISBN;
	string Titulo;
	string Autor1;
	string Autor2;
	string Autor3;
	string Ano;
}registro;

typedef struct noChaveP
{
	string isbn;
	int rrn;
	char ativo;
	struct noChaveP *proximo;
}noChaveP;	

typedef struct noChaveS
{
	string chaves;
	struct noListaInv *primeiro;
	struct noListaInv *ultimo;
	struct noChaveS *proximo;

}noChaveS;

typedef struct noListaInv
{
	struct noChaveP *cp;
	struct noListaInv *proximo;
}noListaInv;

noChaveP *primeiroISBN, *ultimoISBN;

noChaveS *primeiroTitulo, *ultimoTitulo, *primeiroAutor1, *ultimoAutor1, *primeiroAutor2, *ultimoAutor2, *primeiroAutor3, *ultimoAutor3,
*primeiroAno, *ultimoAno;

string completarCP(string str,int tamanho)
{
	int i = 0, x = 0;

	if (str[i] == 0)
		return str;

	for (i = 0; i < tamanho; i++)
	{
		x = i;
		if (str[i] == 0)
			break;
	}
	
	for (i = x; i < tamanho; i++)
	{
		str += '*';
	}
	str[tamanho - 1] = 0;

	return str;
}

string pegarDados(string campo, int tam)
{
	string input;
	int tamanho;
	bool continuar = true;

	do
	{
		system("cls");
		cout << "Digite o " << campo << " do livro\n";
		
		if (campo == "ISBN")
		{
			fflush(stdin);
			getline(cin, input);
			tamanho = input.length();

			if (tamanho > tam)
				cout << "Limite de " << tam << " caracteres";
			else
			{
				input = completarCP(input,tam);
				continuar = false;
				return input;
			}	
		}
		else if (campo == "Titulo")
		{
			fflush(stdin);
			getline(cin, input);
			tamanho = input.length();

			if (tamanho == tam)
				cout << "Digite o titulo do livro";
			else
				return input;
		}
		else if (campo == "Autor1")
		{
			fflush(stdin);
			getline(cin, input);
			tamanho = input.length();
			if (tamanho == tam)
				cout << "Digite o Autor do livro";
			else
				return input;
		}
		else if (campo == "Autor2")
		{
			fflush(stdin);
			getline(cin, input);
			tamanho = input.length();
			return input;
		}
		else if (campo == "Autor3")
		{
			fflush(stdin);
			getline(cin, input);
			tamanho = input.length();
			return input;
		}
		else if (campo == "Ano")
		{
			fflush(stdin);
			getline(cin, input);
			tamanho = input.length();

			if (tamanho == tam)
				cout << "Digite o ano do livro";
			else
				return input;
		}
		getchar();
	} while (continuar);
}

noChaveP* inserirIndicePK(string chave, int rrn, noChaveP **primeiro, noChaveP **ultimo)
{
	fstream arqIndiceP;
	noChaveP *no = new noChaveP;
	noChaveP *atual = new noChaveP;
	noChaveP *anterior = new noChaveP;

	arqIndiceP.open("indiceTitulo.txt", fstream::app);

	no->isbn = chave;
	no->rrn = rrn;
	no->proximo = NULL;

	//Se a lista estiver vazia
	if (!*primeiro)
	{
		*primeiro = no;
		*ultimo = no;
	}
	else
	{
		atual = *primeiro;
		anterior = *primeiro;

		while (atual != NULL)
		{
			//Se ISBN do registro percorrido atualmente = ISBN digitado 
			if (atual->isbn ==chave)
			{
				return atual;
			}
			
			//Se ISBN do registro percorrido atualmente >= ISBN digitado
			if (atual->isbn >= chave)
			{
				break;
			}

			//Percorre a lista deixando o "atual" como anterior e o próximo como atual;
			anterior = atual;
			atual = atual->proximo;

			if (atual == NULL)
			{
				(*ultimo)->proximo = no;
				*ultimo = no;
			}
			else if (atual == anterior)
			{
				no->proximo = atual;
				*primeiro = no;
			}
			else
			{
				anterior->proximo = no;
				no->proximo = atual;
			}
		}
	}
	return no;
}

void inserirNovoRegistro()
{
	registro reg;
	fstream arqDados;
	long rrn;

	reg.ISBN = pegarDados("ISBN", 13);
	reg.Titulo = pegarDados("Titulo", 0);
	reg.Autor1 = pegarDados("Autor1", 0);
	reg.Autor2 = pegarDados("Autor2", 0);
	reg.Autor3 = pegarDados("Autor3", 0);
	reg.Ano = pegarDados("Ano", 0);

	cout << "Aperte qualquer tecla para continuar...";
	getchar();

	arqDados.open("dados.txt",fstream::app);

	if (arqDados.is_open())
	{
		rrn = arqDados.tellp();
		
		std::stringstream ss;
		ss << "#" << reg.ISBN.c_str() << "|" << reg.Titulo.c_str() << "|" << reg.Autor1.c_str() << "|" << reg.Autor2.c_str() << "|" << reg.Autor3.c_str() << "|" << reg.Ano.c_str();
		
		string insert = ss.str();
		int tamanhoInsert = insert.length();

		arqDados.write(insert.c_str(), tamanhoInsert);

		//Incluir os registros nos indices primarios e indices secundarios
		noChaveP *primaria;
		primaria = inserirIndicePK(reg.ISBN, rrn, &primeiroISBN, &ultimoISBN);		
	}
	arqDados.close();
}

void gravarIndices()
{
	noChaveP *cp;
	noChaveS *titulo, *a1, *a2, *a3, *ano;
	noListaInv *listainv;

	fstream arqT, arqA1, arqA2, arqA3, arqAno;

	arqT.open("indiceTitulo.txt", fstream::app);
	arqA1.open("indiceA1.txt", fstream::app);
	arqA2.open("indiceA2.txt", fstream::app);
	arqA3.open("indiceA3.txt", fstream::app);
	arqAno.open("indiceAno.txt", fstream::app);
	
	titulo = primeiroTitulo;
	a1 = primeiroAutor1;
	a2 = primeiroAutor2;
	a3 = primeiroAutor3;
	ano = primeiroAno;

	while (titulo)
	{
		listainv = titulo->primeiro;
		cout << "entrei no primeiro while";

		while (listainv)
		{
			cout << "entrei no segundo while";
			if (listainv->cp->ativo)
			{
				cout << "entrei no IF";
				std::stringstream ss;
				ss << listainv->cp->isbn.c_str() << "|" << titulo->chaves.c_str() << " ";
				string insert = ss.str();
				cout << insert << "\n";
				int tamanhoInsert = insert.length();

				arqT.write(insert.c_str(), tamanhoInsert);
			}
			listainv = listainv->proximo;
		}
		titulo = titulo->proximo;
	}

	while (primeiroAutor1)
	{
		listainv = a1->primeiro;

		while (listainv)
		{
			if (listainv->cp->ativo)
			{
				std::stringstream ss;
				ss << listainv->cp->isbn.c_str() << "|" << a1->chaves.c_str() << " ";

				string insert = ss.str();
				cout << insert << "\n";
				int tamanhoInsert = insert.length();

				arqA1.write(insert.c_str(), tamanhoInsert);
			}
			listainv = listainv->proximo;
		}
	}

	while (primeiroAutor2)
	{
		listainv = a2->primeiro;

		while (listainv)
		{
			if (listainv->cp->ativo)
			{
				std::stringstream ss;
				ss << listainv->cp->isbn.c_str() << "|" << a2->chaves.c_str() << " ";

				string insert = ss.str();
				int tamanhoInsert = insert.length();
				cout << insert << "\n";
				
				arqA2.write(insert.c_str(), tamanhoInsert);
			}
			listainv = listainv->proximo;
		}
	}

	while (primeiroAutor3)
	{
		listainv = a3->primeiro;

		while (listainv)
		{
			if (listainv->cp->ativo)
			{
				std::stringstream ss;
				ss << listainv->cp->isbn.c_str() << "|" << a3->chaves.c_str() << " ";

				string insert = ss.str();
				cout << insert << "\n";
				int tamanhoInsert = insert.length();

				arqA3.write(insert.c_str(), tamanhoInsert);
			}
			listainv = listainv->proximo;
		}
	}

	while (primeiroAno)
	{
		listainv = ano->primeiro;

		while (listainv)
		{
			if (listainv->cp->ativo)
			{
				std::stringstream ss;
				ss << listainv->cp->isbn.c_str() << "|" << ano->chaves.c_str() << " ";

				string insert = ss.str();
				int tamanhoInsert = insert.length();
				cout << insert << "\n";

				arqAno.write(insert.c_str(), tamanhoInsert);
			}
			listainv = listainv->proximo;
		}
	}
	arqT.close();
	arqA1.close();
	arqA2.close();
	arqA3.close();
	arqAno.close();
}

int main()
{
	int op = 0;

	do
	{
		printf("1 - Inserir Registro \n");
		printf("2 - Consultar por chave secundária \n");
		printf("Digite uma opção: \n");
		cin >> op;
		getchar();
		switch (op)
		{
			case 1:
			inserirNovoRegistro();
			gravarIndices();
			break;
			case 2:
			//consulta_SK();
			break;
		}

	}while(op=0);
}