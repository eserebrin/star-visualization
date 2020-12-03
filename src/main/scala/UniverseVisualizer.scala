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

    def caption(x: Long): String = {
        if      (x <= 1000) "This is Earth. Press right to see the Sun"
        else if (x <= 1700) "The sun is huge in comparison to the small blue dot that is our home."
        else if (x <= 1900) "Press up or down to zoom in/out."
        else if (x <= 2300) ""
        else if (x <= 3500) "This is the Alpha Centauri star system, the closest stars to the Sun: A, B, and Proxima."
        else if (x <= 3600) ""
        else if (x <= 4100) "Here is Sirius, the brightest star in the night sky."
        else if (x <= 4400) "It is about twice as massive as the Sun, and there is a White Dwarf Companion."
        else if (x <= 5200) ""
        else if (x <= 6250) "Next is Rigel, a B type star that is 21 times as massive as the Sun."
        else if (x <= 8000) ""
        else if (x <= 10000) "And finally, we have Betelgeuse, a red supergiant that is one of the largest stars seen from Earth."
        else if (x <= 12000) ""
        else if (x <= 14000) "There's still quite a long way to go."
        else if (x <= 15000) ""
        else if (x <= 18000) "This star would engulf a large portion of our solar system."
        else ""
    }

    stage = new JFXApp.PrimaryStage {
        title = "Star Size Comparison"
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
            val alphaCentauriA = new SpaceObject(Color.White, 2500, 1.2234)
            val alphaCentauriB = new SpaceObject(Color.Khaki, 3000, 0.8632)
            val proximaCentauri = new SpaceObject(Color.DarkOrange, 3400, 0.1542)
            val siriusA = new SpaceObject(Color.LightBlue, 4000, 1.711)
            val siriusB = new SpaceObject(Color.White, 4750, 0.0084)
            val rigel = new SpaceObject(Color.Blue, 5500, 78.9)
            val betelgeuse = new SpaceObject(Color.Red, 8500, 764.0)

            val objects = Vector(sun, earth, alphaCentauriA, alphaCentauriB, proximaCentauri, siriusA, siriusB, rigel, betelgeuse)
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
                    g.fillText(caption(screenX), 100, displayHeight - 100)

                    if (scale > 1 && (keysPressed.contains(KeyCode.Down) || keysPressed.contains(KeyCode.S))) scale -= 1.0
                    if (keysPressed.contains(KeyCode.Up) || keysPressed.contains(KeyCode.W)) scale += 1.0

                    if (keysPressed.contains(KeyCode.Right) || keysPressed.contains(KeyCode.D)) {
                        objects.foreach(_.moveRight())
                        screenX += 2
                    }
                    if (keysPressed.contains(KeyCode.Left) || keysPressed.contains(KeyCode.A)) {
                        objects.foreach(_.moveLeft())
                        screenX -= 2
                    }

                    oldT = t
                }
            })

            canvas.requestFocus()
            mainLoop.start()
        }
    }
}