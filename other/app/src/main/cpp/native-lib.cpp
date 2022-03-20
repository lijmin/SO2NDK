#include <jni.h>
#include <string>
#include "include/other.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_lijmin_other_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_lijmin_other_LinkOther_add(JNIEnv *env, jobject thiz, jint a, jint b) {
    return other().add(a,b);
}