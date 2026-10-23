package org.infosmid.kfsm.sample

import org.infosmid.kfsm.viz.Parser.parseStateMachine
import org.infosmid.kfsm.viz.Visualization.asciiDoc
import org.infosmid.kfsm.viz.Visualization.plantUml
import org.infosmid.kfsm.viz.Visualization.mermaidDiagram
import org.junit.Before
import org.junit.Test
import java.io.File

class VisualizeFSM {
    @Before
    fun setup() {
        val generated = File("generated")
        if (generated.exists() && !generated.isDirectory) {
            error("Expected generated to be a directory")
        } else if (!generated.exists()) {
            generated.mkdirs()
        }
    }
    @Test
    fun visualizeLock() {
        val visualisation = parseStateMachine(
            "LockFSM",
            File("src/commonMain/kotlin/org/infosmid/kfsm/sample/lock.kt")
        )
        File("generated", "lock-manual.plantuml").writeText(plantUml(visualisation))
        File("generated", "lock-manual.mmd").writeText(mermaidDiagram(visualisation))
        File("generated", "lock-manual.adoc").writeText(asciiDoc(visualisation))
    }
}
