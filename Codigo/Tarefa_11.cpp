#include <windows.h>

int main() {
    int i, j;
    int inicio, final, tempo_ms;

    inicio = GetTickCount();
    /* Substitua o for a seguir pelo trecho de código
       cujo tempo de execução deverá ser medido. */
    for (j = 0; j < 10; j ++)
        for (i = 0; i < 1387634340; i ++);
    final = GetTickCount();
    tmili = final - inicio;

    printf("tempo decorrido: %d\n", tempo_ms);
    return 0;
}
