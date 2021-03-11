package com.bgtaa.posaljiracun

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


const val TAG: String = "TAG"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

             button2.setOnClickListener {
        scanQRCode()
            }

   //     button2.setOnClickListener {
            scanQRCode()
    //    }
    }

    private fun scanQRCode(){
        val integrator = IntentIntegrator(this).apply {
            captureActivity = CaptureActivity::class.java
            setOrientationLocked(false)
            setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
            setPrompt("SKENIRA SE...")
        }
        integrator.initiateScan()
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val regex = Regex(pattern = "K:PR")
        val itsok = regex.containsMatchIn(result.contents)
        if (result != null && itsok == true) {
            if (result.contents == null) Toast.makeText(this, "NEUSPEŠNO", Toast.LENGTH_LONG).show()
            else Toast.makeText(this, "USPEŠNO", Toast.LENGTH_LONG).show()
            fun konvert() {
                //////////////////////////////////////////////
                val rezult = result.contents
                val racunkod = Regex(pattern = """R:\d+""")
                var racun1 = racunkod.find(rezult)?.value
                racun1 = racun1?.replace("R:", "")
                var racun2 = racun1?.take(3)
                var ostatak = racun1?.drop(3)
                var treci = ostatak?.takeLast(2)
                var drugi = ostatak?.dropLast(2)
                var drugia = drugi?.trimStart('0')
                var racun = "Račun primaoca: " + racun2 + "-" + drugia + "-" + treci
                val primakod = Regex(pattern = """N:[\w+\s+]+""")
                var primalac = primakod.find(rezult)?.value
                primalac = "Primalac: " + primalac?.replace("N:", "")
                var uplatil = Regex(pattern = """P:[\w+\s+]+""")
                var uplatilac = uplatil.find(rezult)?.value
                if (uplatilac != null) {
                    uplatilac = "Uplatilac: " + uplatilac?.replace("P:", "") + "\n"
                } else {
                    uplatilac = ""
                }
                var svrha = Regex(pattern = """S:[\w+?(.,:)\s+]+""")
                var svrhap = svrha.find(rezult)?.value
                if (svrhap != null) {
                    svrhap = "Svrha uplate: " + svrhap?.replace("S:", "") + "\n"
                } else {
                    svrhap = ""
                }
                var iznoskod = Regex(pattern = """I:RSD\d+(,\d+)?""")
                var iznos = iznoskod.find(rezult)?.value
                iznos = "Iznos: " + iznos?.replace("I:RSD", "") + " RSD"
                var rokod = Regex(pattern = """RO:\d+""")
                var ro = rokod.find(rezult)?.value
                var poziv = ""
                if (ro != null) {
                    ro = ro?.replace("RO:", "")
                    poziv = "Poziv na broj: " + ro?.drop(2) + "\n"
                } else {
                    poziv = ""
                }
                var krajnjirezultat = uplatilac + svrhap + primalac + "\n" + iznos + "\n" + racun + "\n" + poziv
                tekst1.setText(krajnjirezultat)
            }
            konvert()
            var myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?;
            var myClip = ClipData.newPlainText("text", tekst1.text);
            myClipboard?.setPrimaryClip(myClip);
        } else {
            Toast.makeText(this, "NEUSPEŠNO", Toast.LENGTH_LONG).show()
            tekst1.setText("NEUSPEŠNO")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}