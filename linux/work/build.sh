#!/bin/bash

cd /work && \
  gcc napier.c -o napier -Wl,-rpath ./ -L. -lmpp_sample && \
  ./napier
