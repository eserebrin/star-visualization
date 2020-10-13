import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.animation.AnimationTimer
import scalafx.scene.paint.Color

object UniverseVisualizer extends JFXApp {
    val displayWidth = 500
    val displayHeight = 500

    stage = new JFXApp.PrimaryStage {
        title = "Universe Visualization"
        scene = new Scene(displayWidth, displayHeight) {
            val canvas = new Canvas(displayWidth, displayHeight)
            content = canvas
            val g = canvas.graphicsContext2D
            val args = parameters.raw

            val ship = new Spaceship(displayWidth / 2 - 15, displayHeight - 100)

            var oldT = 0L
            var timer = 0L
            val mainLoop = AnimationTimer(t => {
                if (t - oldT >= 1e9 / 60) {

                    g.setFill(Color.Black)
                    g.fillRect(0, 0, displayWidth, displayHeight)
                    ship.display(g)

                    if (timer % 60 == 0) ship.step()
                    timer += 1

                    oldT = t
                }
            })

            canvas.requestFocus()
            mainLoop.start()
        }
    }
}