package state

class MapState extends State {
	
	def storage = [:]
	
	void setProperty(String name, value) {
		//println "setProperty($name,$value)"
		if (storage.containsKey(name)) {
			Transaction.logPropertyUpdate(this, name, storage[name])
		}
		else {
			Transaction.logPropertyCreate(this, name)
		}
		storage[name] = value 
	}
	
	def getProperty(String name) {
		//println "getProperty($name)"
		storage[name] 
	}
	
	int size() { storage.size() }
	
	
	MapState clone() {
		def ps2 = new MapState()
		this.@storage.each { k,v ->
			ps2[k] = v
		}
		return ps2
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false
		}
		if (o.getClass() != this.getClass()) {
			return false
		}
		boolean result = this.@storage.equals( (o as MapState).@storage )
		return result
	}

	@Override
	public int hashCode() {
		return storage.hashCode()
	}
	
}
