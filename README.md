# Modern analog watch face

Useful links: https://github.com/android/wear-os-samples/blob/main/WatchFaceKotlin/app/src/main/java/com/example/android/wearable/alpha/AnalogWatchFaceService.kt

## Instructions

- pair watch with android studio
  - navigate to settings -> developer settings -> debugging over wlan -> pair device
  - in android studio -> terminal: `adb pair [displayed pairing ip with port] [displayed safety code]`

- connect watch to android studio
  - make sure watch is already paired
  - in android studio -> terminal: `adb connect [displayed watch ip with port]`