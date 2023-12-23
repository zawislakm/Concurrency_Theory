
#include <iostream>
#include <omp.h>

int main(){
    printf("start\n");
    
    int sum = 0;
    # pragma omp parallel shared(sum)
    {
        int id = omp_get_thread_num();
        // printf(" watek %d parallel\n", id);
        // # pragma omp sections
        // {
            // #pragma omp section
            // printf("watek %d w sekcji 1/n",id);

            // #pragma omp section
            // printf("watek %d w sekcji 2/n",id);
        // }
                

        #pragma omp for reduction(+:sum)
        for(int i = 0; i < 10000; i++){

            sum  += 2;
            printf("watek %d i = %d, sum = %d\n",id,i,sum);
        }
    }


    printf("sum = %d, end\n",sum);
    return 0;
}