
import com.android.build.gradle.internal.BuildToolsExecutableInput
import com.android.build.gradle.internal.initialize
import com.android.build.gradle.internal.tasks.NonIncrementalGlobalTask
import com.android.build.gradle.internal.tasks.factory.GlobalTaskCreationAction
import com.android.build.gradle.internal.tasks.factory.GlobalTaskCreationConfig
import com.android.ddmlib.AndroidDebugBridge
import org.gradle.api.tasks.Nested
import java.util.concurrent.TimeUnit

abstract class PortForwardTask : NonIncrementalGlobalTask() {
    @get:Nested
    abstract val buildTools: BuildToolsExecutableInput

    override fun doTaskAction() {
        AndroidDebugBridge.init(false)
        val adbLocation = buildTools.adbExecutable().get()
        val bridge =
            AndroidDebugBridge.createBridge(
                adbLocation.asFile.absolutePath,
                false,
                Long.MAX_VALUE,
                TimeUnit.MILLISECONDS,
            )
        while (!bridge.hasInitialDeviceList()) {
            Thread.sleep(1000)
        }
        bridge.devices.forEach {
            logger.info("Port forwarding for ${it.name}")
            it.createReverse(8080, 8080)
        }
        AndroidDebugBridge.terminate()
    }

    class CreationAction(config: GlobalTaskCreationConfig) : GlobalTaskCreationAction<PortForwardTask>(config) {
        override val name = "portForward"
        override val type = PortForwardTask::class.java

        override fun configure(task: PortForwardTask) {
            super.configure(task)

            task.buildTools.initialize(
                creationConfig.services.buildServiceRegistry,
                creationConfig.compileSdkHashString,
                creationConfig.buildToolsRevision,
            )
        }
    }
}
