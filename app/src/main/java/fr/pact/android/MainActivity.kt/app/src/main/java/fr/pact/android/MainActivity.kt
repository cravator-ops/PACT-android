
package fr.pact.android

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.content.ComponentName
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.view.Gravity
import android.graphics.Typeface

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 40, 32, 32)
        }

        val title = TextView(this).apply {
            text = "PACT"
            textSize = 32f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(this).apply {
            text = "Votre téléphone. Vos règles."
            textSize = 16f
            gravity = Gravity.CENTER
        }

        layout.addView(title)
        layout.addView(subtitle)

        val apps = packageManager.queryIntentActivities(
            Intent(Intent.ACTION_MAIN).addCategory(
                Intent.CATEGORY_LAUNCHER
            ), 0
        ).sortedBy {
            it.loadLabel(packageManager).toString()
                .lowercase()
        }

        for (app in apps) {
            val info = app.activityInfo
            val button = Button(this).apply {
                text = info.loadLabel(packageManager)
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_MAIN).apply {
                        component = ComponentName(
                            info.packageName,
                            info.name
                        )
                    }
                    startActivity(intent)
                }
            }
            layout.addView(button)
        }

        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)
    }
}
