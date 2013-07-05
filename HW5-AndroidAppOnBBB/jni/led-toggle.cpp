#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

using namespace std;

extern "C" {
	JNIEXPORT jstring JNICALL Java_com_example_hw5_MainActivity_LEDToggle(JNIEnv* env,
	jobject thiz, jstring ledStatus);
};

// change LED light
JNIEXPORT jstring JNICALL Java_com_example_hw5_MainActivity_LEDToggle(JNIEnv* env,
		jobject thiz, jstring ledStatus) {
	FILE *LEDHandle = NULL;
	char *LEDBrightness = "/sys/bus/platform/drivers/leds-gpio/gpio-leds.7/leds/beaglebone:green:usr1/brightness";
	if ((LEDHandle = fopen(LEDBrightness, "r+")) != NULL) {
		const char* ledStatus_ = env->GetStringUTFChars(ledStatus, 0);
		// toggle LED light
		fwrite(ledStatus_, sizeof(char), 1, LEDHandle);
		env->ReleaseStringUTFChars(ledStatus, ledStatus_);
		fclose(LEDHandle);
	}
	else
		return env->NewStringUTF("FAILED!");
	return env->NewStringUTF("OK!");
}

