#include<iostream>
#include<stdio.h>
#include<unistd.h>
#include<sys/time.h>
#include<math.h>
#include<omp.h>

using namespace std;

void show( struct timeval *ti ) {
  struct timeval tf;
  static double last = 0.0;
  gettimeofday( &tf, NULL );
  double now = tf.tv_sec + tf.tv_usec * 0.000001;
  double ini = ti->tv_sec - ti->tv_usec * 0.000001;
  if ( last > 0.0 )
     printf( "--- Time report: %8.5lf ( + %09.6lf )\n", now - ini , now - last ); 
  else
     printf( "--- Time report: %8.5lf ( + %09.6lf )\n", now - ini , now - ini ); 
  last = now;
} 

int main( int p, char ** argv ) {

  struct timeval ti;
  gettimeofday( &ti, NULL );
  cout << "START" << endl;

# pragma omp parallel shared(ti)
{ 
  cout << "Petla 1" << endl;
  #pragma omp for nowait 
  for ( int i = 0; i < 5000; i++ )
     usleep( 2000 );
  show( &ti ); 

  cout << "Petla 2" << endl;
  #pragma omp for nowait
  for ( int i = 0; i < 5000; i++ )
     usleep( i );
  show( &ti ); 

  cout << "Petla 3" << endl;
  #pragma omp for nowait
  for ( int i = 0; i < 5000; i++ )
     usleep( 5000 - i );
  show( &ti ); 

  cout << "Petla 4" << endl;
  #pragma omp for nowait
  for ( int i = 0; i < 5000; i++ )
     usleep( 100 + 4000 * ( i / 2500 ) );
  show( &ti ); 

  cout << "Petle 5" << endl;
  for ( int i = 0; i < 3; i++ )
  #pragma omp for nowait
   for ( int j = 0; j < 50000; j++ )
     usleep( 1 );
  show( &ti ); 
}
  cout << "END" << endl;


  return 0;
}