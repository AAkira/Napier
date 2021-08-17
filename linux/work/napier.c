#include <stdio.h>
#include "libmpp_sample_api.h"

int main(int argc, char **argv) {
  printf("-----\n");

  libmpp_sample_ExportedSymbols* lib = libmpp_sample_symbols();
  libmpp_sample_kref_io_github_aakira_napier_mppsample_Sample newInstance = lib->kotlin.root.io.github.aakira.napier.mppsample.Sample.Sample();
  lib->kotlin.root.io.github.aakira.napier.mppsample.Sample.hello(newInstance);

  lib->DisposeStablePointer(newInstance.pinned);

  printf("====\n");
  return 0;
}
