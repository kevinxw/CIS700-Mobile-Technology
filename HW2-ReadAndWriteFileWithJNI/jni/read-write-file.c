#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <android/log.h>
#include <errno.h>

#define  LOG_TAG    "DEO MSG"//all my logs are labeled with this
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

jstring Java_com_example_hw2_MainActivity_rwFileContent(JNIEnv* env,
		jobject thiz, jstring fName, jstring fContent) {
	// convert Java String to char *
	const char* content = (*env)->GetStringUTFChars(env, fContent, 0);
	const char* name = (*env)->GetStringUTFChars(env, fName, 0);
	// define file path
	const char* FILE_PATH = "/data/data/com.example.hw2/%s";
	char fPath[128];
	sprintf(fPath, FILE_PATH, name);
	FILE* file = fopen(fPath, "ab+");
	char buffer[4096];
	// first append file content
	if (file != NULL) {
		LOGE("trying to open file for writing");
		fputs(content, file);
		fputs("\n", file);
		fflush(file);
		fclose(file);
	}
	// then read the whole file, file must exist
	file = fopen(fPath, "rb");
	LOGE("trying to open file for reading");
	if (buffer == NULL) {
		return (*env)->NewStringUTF(env, "Memory error!");
	}
	// copy the file into the buffer:
	fread(buffer, 1, 4096, file);
	fclose(file);
	// release memory for these string, Java GC will not release them!
	(*env)->ReleaseStringUTFChars(env, fContent, content);
	(*env)->ReleaseStringUTFChars(env, fName, name);
	// return content read from file to Java method
	return (*env)->NewStringUTF(env, buffer);
}
