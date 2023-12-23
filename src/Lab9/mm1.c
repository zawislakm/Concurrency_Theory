#include <stdio.h>
#include <stdlib.h>


int SIZE;

#define _first_(i,j) first[ (j)*SIZE + (i) ]
#define _second_(i,j) second[ (j)*SIZE + (i) ]
#define _multiply_(i,j) multiply[ (j)*SIZE + (i) ]

#include <sys/time.h>
#include <time.h>

static double gtod_ref_time_sec = 0.0;

/* Adapted from the bl2_clock() routine in the BLIS library */

double dclock()
{
  double         the_time, norm_sec;
  struct timeval tv;
  gettimeofday( &tv, NULL );
  if ( gtod_ref_time_sec == 0.0 )
    gtod_ref_time_sec = ( double ) tv.tv_sec;
  norm_sec = ( double ) tv.tv_sec - gtod_ref_time_sec;
  the_time = norm_sec + tv.tv_usec * 1.0e-6;
  return the_time;
}

int mm(double * first, double * second, double * multiply)
{
  register unsigned int i,j,k;
  double sum = 0;

  for (i = 0; i < SIZE; i++) { //rows in multiply
    for (j = 0; j < SIZE; j++) { //columns in multiply
      sum = 0;
      for (k = 0; k < SIZE; k++) { //columns in first and rows in second
            sum = sum + _first_(i,k)*_second_(k,j);
          }
      _multiply_(i,j) = sum;
    }
  }
  return 0;
}

int main( int argc, const char* argv[] )
{
  int i,j,iret;
  double ** first;
  double ** second;
  double ** multiply;

  double * first_;
  double * second_;
  double * multiply_;

  double dtime;

  SIZE = 1000;

  first_ = (double*) malloc(SIZE*SIZE*sizeof(double));
  second_ = (double*) malloc(SIZE*SIZE*sizeof(double));
  multiply_ = (double*) malloc(SIZE*SIZE*sizeof(double));

  first = (double**) malloc(SIZE*sizeof(double*));
  second = (double**) malloc(SIZE*sizeof(double*));
  multiply = (double**) malloc(SIZE*sizeof(double*));

  for (i = 0; i < SIZE; i++) {
    first[i] = first_ + i*SIZE;
    second[i] = second_ + i*SIZE;
    multiply[i] = multiply_ + i*SIZE;
  }

  for (i = 0; i < SIZE; i++) { //rows in first
    for (j = 0; j < SIZE; j++) { //columns in first
      first[i][j]=i+j;
      second[i][j]=i-j;
    }
  }

  dtime = dclock();

  iret=mm(first_,second_,multiply_);

  dtime = dclock()-dtime;
  printf( "Time: %le \n", dtime);

  fflush( stdout );

  free(first_);
  free(second_);
  free(multiply_);

  free(first);
  free(second);
  free(multiply);

  return iret;
}
