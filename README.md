# QR-bill
QR scanner for electricity, gas and water bills. Outputs formatted data with copy/paste function.

[![Download](https://raw.githubusercontent.com/bogeta11040/QR-bill/main/docs/googleplay.png)](https://play.google.com/store/apps/details?id=com.bgtaa.posaljiracun)


## How does it work
First, QR data is extracted from code (ZXing). Then, data is processed using regex. Billing information is formatted, displayed to user and automatically copied to clipboard. Text area is selectable so user can copy partial information.

## Built With

* [Kotlin](https://kotlinlang.org/) - Programming language used
* [Java](https://www.java.com/en/) - Programming language used
* [ZXing Android Embedded](https://github.com/journeyapps/zxing-android-embedded) - Barcode scanning library for Android, using ZXing for decoding
* [NBS IPS system](https://nbs.rs/en/ciljevi-i-funkcije/platni-sistem/nbs-operator/ips-nbs/) - IPS QR code data standard
* [Android Studio](https://developer.android.com/studio) - IDE used
