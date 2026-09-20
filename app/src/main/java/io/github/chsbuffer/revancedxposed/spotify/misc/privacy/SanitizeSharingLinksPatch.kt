package io.github.chsbuffer.revancedxposed.spotify.misc.privacy

import android.content.ClipData
import android.content.Intent
import app.revanced.extension.spotify.misc.privacy.SanitizeSharingLinksPatch
import de.robv.android.xposed.XposedHelpers
import io.github.chsbuffer.revancedxposed.scopedHook
import io.github.chsbuffer.revancedxposed.spotify.SpotifyHook

fun SpotifyHook.SanitizeSharingLinks() {
    ::shareCopyUrlFingerprint.hookMethod(
        scopedHook(
            XposedHelpers.findMethodExact(
                ClipData::class.java.name,
                lpparam.classLoader,
                "newPlainText",
                CharSequence::class.java,
                CharSequence::class.java
            )
        ) {
            before { param ->
                val url = param.args[1] as String
                param.args[1] = SanitizeSharingLinksPatch.sanitizeSharingLink(url)
            }
        })

    XposedHelpers.findAndHookMethod(
        Intent::class.java,
        "putExtra",
        String::class.java,
        String::class.java,
        object : de.robv.android.xposed.XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                if (param.args[0] == Intent.EXTRA_TEXT) {
                    val url = param.args[1] as String
                    param.args[1] = SanitizeSharingLinksPatch.sanitizeSharingLink(url)
                }
            }
        })
}