#include <stdio.h>
#include "libnative_api.h"

int main(int argc, char **argv) {
  // init kotlin native
  libnative_ExportedSymbols* lib = libnative_symbols();

  // init napier
  lib->kotlin.root.io.github.aakira.napier.mppsample.debugBuild();

  // run napier
  libnative_kref_io_github_aakira_napier_mppsample_Sample newInstance = lib->kotlin.root.io.github.aakira.napier.mppsample.Sample.Sample();
  lib->kotlin.root.io.github.aakira.napier.mppsample.Sample.hello(newInstance);

  lib->DisposeStablePointer(newInstance.pinned);

  return 0;
}
