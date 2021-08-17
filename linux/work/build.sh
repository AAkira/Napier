#!/bin/bash

cd /work && \
  gcc napier.c -o napier -Wl,-rpath ./ -L. -lnative && \
  ./napier
