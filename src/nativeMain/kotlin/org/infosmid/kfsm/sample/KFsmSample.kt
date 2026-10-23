@file:OptIn(ExperimentalNativeApi::class)

package org.infosmid.kfsm.sample

import kotlin.experimental.ExperimentalNativeApi
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource.Monotonic

fun main(args: Array<String>) {
    val timeout = if(args.isNotEmpty()) args[0].toLong().milliseconds else 5000.milliseconds
    val version = "${KotlinVersion.CURRENT.major}.${KotlinVersion.CURRENT.minor}.${KotlinVersion.CURRENT.patch}"
    val os = Platform.osFamily
    val arch = kotlin.native.Platform.cpuArchitecture
    println("Starting...Kotlin/Native:$version in $os on $arch")
    val lock = Lock()
    val fsm = LockFSM(lock)
    val startTime = Monotonic.markNow()
    var transitions = 0
    while (Monotonic.markNow()  - startTime < timeout) {
        for (i in 0..100) {
            fsm.unlock()
            fsm.lock()
        }
        transitions += 200
    }
    val totalTime = Monotonic.markNow() - startTime
    val rate = transitions / totalTime.inWholeMilliseconds
    println("Total transitions $transitions in ${totalTime}ms $rate transisions/ms")
}
