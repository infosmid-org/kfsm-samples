import org.infosmid.kfsm.sample.Lock
import org.infosmid.kfsm.sample.LockFSM
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource.Monotonic

fun main(args: Array<String>) {
    val timeout = if(args.isNotEmpty()) args[0].toLong().milliseconds else 5000.milliseconds
    val version = js("process.version")
    println("Starting...$version")
    val lock = Lock()
    val fsm = LockFSM(lock)
    val startTime = Monotonic.markNow()
    var transitions = 0
    while (Monotonic.markNow() - startTime < timeout) {
        for (i in 0..100) {
            fsm.unlock()
            fsm.lock()
        }
        transitions += 200
    }
    val totalTime = Monotonic.markNow() - startTime
    val rate = transitions.toDouble() / totalTime.inWholeMilliseconds.toDouble()
    println("Total transitions $transitions in ${totalTime}ms $rate transitions/ms")
}
