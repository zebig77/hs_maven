package ui


import static javax.swing.JFrame.EXIT_ON_CLOSE
import groovy.swing.SwingBuilder

class LogViewer {
	
	static LogViewer instance

	def swing

	LogViewer() {
		swing = SwingBuilder.build {
			frame( title: "Log Viewer", size: [650, 230], show: true,
			defaultCloseOperation: EXIT_ON_CLOSE, id: "frame" ) {
				borderLayout()
				scrollPane( constraints: CENTER ) {
					editorPane( id: "log", editable: false )
				}
			}
		}
		instance = this
	}
	
	void clear() {
		swing.doLater { log.text = '' }
	}
	
	void add(msg) {
		swing.doLater { log.text += msg+"\n" }
	}
}
