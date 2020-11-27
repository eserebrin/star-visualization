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

    def caption(x: Long, scale: Double): String = {
        if (x <= 1000) "This is Earth. Press right to see the Sun"
        else if (x <= 1150) "The sun is huge in comparison to the small blue dot that is our home."
        else if (x <= 1500) ""
        else if (x <= 1700 && scale > 1.0) "Press down to zoom out and see the Sun as just one pixel."
        else if (x <= 1700 && scale <= 1.0) ""
        else if (x <= 2000) ""
        else ""
    }

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

            var scale = 250.0

            val earth = new SpaceObject(Color.Blue, 640, 0.009)
            val sun = new SpaceObject(Color.White, 1500, 1.0)
            val betelgeuse = new SpaceObject(Color.Red, 3000, 764.0)

            val objects = Vector(sun, earth, betelgeuse)
            var screenX = 640L

            var oldT = 0L
            var timer = 0L
            val mainLoop = AnimationTimer(t => {
                if (t - oldT >= 1e9 / 60) {
                    println(s"Scale = $scale, X = $screenX")

                    g.setFill(Color.Black)
                    g.fillRect(0, 0, displayWidth, displayHeight)

                    objects.foreach(_.draw(g, scale))

                    g.setFill(Color.White)
                    g.fillText(caption(screenX, scale), 100, displayHeight - 100)

                    if ((keysPressed.contains(KeyCode.Down) || keysPressed.contains(KeyCode.S)) && scale > 1.0) scale -= 1.0
                    if ((keysPressed.contains(KeyCode.Up) || keysPressed.contains(KeyCode.W))) scale += 1.0

                    if (keysPressed.contains(KeyCode.Right) || keysPressed.contains(KeyCode.D)) {
                        objects.foreach(_.moveRight())
                        screenX += 1
                    }
                    if (keysPressed.contains(KeyCode.Left) || keysPressed.contains(KeyCode.A)) {
                        objects.foreach(_.moveLeft())
                        screenX -= 1
                    }

                    oldT = t
                }
            })

            canvas.requestFocus()
            mainLoop.start()
        }
    }
}