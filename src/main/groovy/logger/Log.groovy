package logger

import ui.LogViewer

class Log {
	
	static info(msg) {
		println msg
		if (LogViewer.instance != null) {
			LogViewer.instance.add(msg)
		}
	}

}
