import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode
import scala.collection.mutable

object UniverseVisualizer extends JFXApp {
    val displayWidth = 1280
    val displayHeight = 720

    stage = new JFXApp.PrimaryStage {
        title = "If the Sun was 1 pixel"
        scene = new Scene(displayWidth, displayHeight) {
            val canvas = new Canvas(displayWidth, displayHeight)
            content = canvas
            val g = canvas.graphicsContext2D
            val args = parameters.raw

            var keysPressed = mutable.Set[KeyCode]()
            canvas.onKeyPressed = (e: KeyEvent) => keysPressed += e.code
            canvas.onKeyReleased = (e: KeyEvent) => keysPressed -= e.code

            var scale = 109.0

            val sun = new Star(1.0)
            val earth = new Earth()
            var objects = mutable.Buffer(sun, earth)
            // val betelgeuse = new Star(sunRadius * 764)

            var oldT = 0L
            var timer = 0L
            val mainLoop = AnimationTimer(t => {
                if (t - oldT >= 1e9 / 60) {

                    g.setFill(Color.Black)
                    g.fillRect(0, 0, displayWidth, displayHeight)

                    earth.draw(g, scale, 20, displayHeight / 2)
                    sun.draw(g, scale, 150, 100)

                    if (keysPressed.contains(KeyCode.Down) && scale > 1.0) scale -= 1.0
                    if (keysPressed.contains(KeyCode.Up)) scale += 1.0

                    if (keysPressed.contains(KeyCode.Right)) objects.foreach(_.moveRight())
                    if (keysPressed.contains(KeyCode.Left)) objects.foreach(_.moveLeft())
                    // betelgeuse.draw(g, 0, 0)

                    oldT = t
                }
            })

            canvas.requestFocus()
            mainLoop.start()
        }
    }
}