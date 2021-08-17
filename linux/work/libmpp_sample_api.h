#ifndef KONAN_LIBMPP_SAMPLE_H
#define KONAN_LIBMPP_SAMPLE_H
#ifdef __cplusplus
extern "C" {
#endif
#ifdef __cplusplus
typedef bool            libmpp_sample_KBoolean;
#else
typedef _Bool           libmpp_sample_KBoolean;
#endif
typedef unsigned short     libmpp_sample_KChar;
typedef signed char        libmpp_sample_KByte;
typedef short              libmpp_sample_KShort;
typedef int                libmpp_sample_KInt;
typedef long long          libmpp_sample_KLong;
typedef unsigned char      libmpp_sample_KUByte;
typedef unsigned short     libmpp_sample_KUShort;
typedef unsigned int       libmpp_sample_KUInt;
typedef unsigned long long libmpp_sample_KULong;
typedef float              libmpp_sample_KFloat;
typedef double             libmpp_sample_KDouble;
typedef float __attribute__ ((__vector_size__ (16))) libmpp_sample_KVector128;
typedef void*              libmpp_sample_KNativePtr;
struct libmpp_sample_KType;
typedef struct libmpp_sample_KType libmpp_sample_KType;

typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Byte;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Short;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Int;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Long;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Float;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Double;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Char;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Boolean;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_kotlin_Unit;
typedef struct {
  libmpp_sample_KNativePtr pinned;
} libmpp_sample_kref_io_github_aakira_napier_mppsample_Sample;


typedef struct {
  /* Service functions. */
  void (*DisposeStablePointer)(libmpp_sample_KNativePtr ptr);
  void (*DisposeString)(const char* string);
  libmpp_sample_KBoolean (*IsInstance)(libmpp_sample_KNativePtr ref, const libmpp_sample_KType* type);
  libmpp_sample_kref_kotlin_Byte (*createNullableByte)(libmpp_sample_KByte);
  libmpp_sample_kref_kotlin_Short (*createNullableShort)(libmpp_sample_KShort);
  libmpp_sample_kref_kotlin_Int (*createNullableInt)(libmpp_sample_KInt);
  libmpp_sample_kref_kotlin_Long (*createNullableLong)(libmpp_sample_KLong);
  libmpp_sample_kref_kotlin_Float (*createNullableFloat)(libmpp_sample_KFloat);
  libmpp_sample_kref_kotlin_Double (*createNullableDouble)(libmpp_sample_KDouble);
  libmpp_sample_kref_kotlin_Char (*createNullableChar)(libmpp_sample_KChar);
  libmpp_sample_kref_kotlin_Boolean (*createNullableBoolean)(libmpp_sample_KBoolean);
  libmpp_sample_kref_kotlin_Unit (*createNullableUnit)(void);

  /* User functions. */
  struct {
    struct {
      struct {
        struct {
          struct {
            struct {
              struct {
                struct {
                  libmpp_sample_KType* (*_type)(void);
                  libmpp_sample_kref_io_github_aakira_napier_mppsample_Sample (*Sample)();
                  void (*handleError)(libmpp_sample_kref_io_github_aakira_napier_mppsample_Sample thiz);
                  const char* (*hello)(libmpp_sample_kref_io_github_aakira_napier_mppsample_Sample thiz);
                } Sample;
              } mppsample;
            } napier;
          } aakira;
        } github;
      } io;
    } root;
  } kotlin;
} libmpp_sample_ExportedSymbols;
extern libmpp_sample_ExportedSymbols* libmpp_sample_symbols(void);
#ifdef __cplusplus
}  /* extern "C" */
#endif
#endif  /* KONAN_LIBMPP_SAMPLE_H */
