package io.github.chsbuffer.revancedxposed.spotify.misc.widgets

import io.github.chsbuffer.revancedxposed.fingerprint

val canBindAppWidgetPermissionFingerprint = fingerprint {
    strings("android.permission.BIND_APPWIDGET")
}