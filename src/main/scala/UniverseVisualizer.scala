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

            val earth = new SpaceObject(Color.Blue, 640, 0.009)
            val sun = new SpaceObject(Color.White, 1500, 1.0)
            val betelgeuse = new SpaceObject(Color.Red, 3000, 764.0)

            val objects = Vector(sun, earth, betelgeuse)
            val captions = Vector(("This is Earth", 0, 500))

            var oldT = 0L
            var timer = 0L
            val mainLoop = AnimationTimer(t => {
                if (t - oldT >= 1e9 / 60) {
                    g.setFill(Color.Black)
                    g.fillRect(0, 0, displayWidth, displayHeight)

                    objects.foreach(_.draw(g, scale))

                    g.setFill(Color.White)
                    captions.foreach(c => g.fillText(c._1, 100, displayHeight - 100))

                    if (keysPressed.contains(KeyCode.Down) && scale > 1.0) scale -= 1.0
                    if (keysPressed.contains(KeyCode.Up)) scale += 1.0

                    if (keysPressed.contains(KeyCode.Right)) objects.foreach(_.moveRight())
                    if (keysPressed.contains(KeyCode.Left)) objects.foreach(_.moveLeft())

                    oldT = t
                }
            })

            canvas.requestFocus()
            mainLoop.start()
        }
    }
}