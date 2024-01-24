package game

class CardLibrary {

	static CardLibrary instance = new CardLibrary()
	static final BEASTS_NAMES = """\
Angry Chicken
Bloodfen Raptor
Boar
Captain's Parrot
Core Hound
Devilsaur
Dire Wolf Alpha
Emperor Cobra
Haunted Creeper
Hungry Crab
Ironbeak Owl
Ironfur Grizzly
Jungle Panther
King Krush
King Mukla
Leokk
Maexxna
Misha
Oasis Snapjaw
Panther
River Crocolisk
Savannah Highmane
Scavenging Hyena
Sheep
Silverback Patriarch
Squirrel
Stampeding Kodo
Starving Buzzard
Stonetusk Boar
Stranglethorn Tiger
The Beast
Timber Wolf
Tundra Rhino
Webspinner
Young Dragonhawk"""
	static List<String> beasts = null
	
	int next_id = 1
	def cards = [:]


	static String random_beast_name() {
		if (beasts == null) {
			beasts = []
			CardLibrary.BEASTS_NAMES.eachLine { beasts << it }
		}
		return Game.random_pick(beasts)
	}

	static CardDefinition getCardDefinition(String card_name) {
		def class_name = card_name.replaceAll("'s", "s").replaceAll(/(\w)(\w*)/) { wholeMatch, initialLetter, restOfWord ->
			initialLetter.toUpperCase() + restOfWord
		}.replaceAll("[^a-zA-Z0-9]", "")
		def cd = instance.cards[class_name]
		def cd_class
		if (cd == null) {
			try {
				cd_class = instance.class.classLoader.loadClass("cards.${class_name}", true)
			}
			catch (ClassNotFoundException e) {
				println e
				throw new InvalidDefinitionException("carte inconnue: $card_name")
			}
			try {
				cd = cd_class.newInstance()
			}
			catch (ReflectiveOperationException e) {
				println e
				throw new InvalidDefinitionException("carte invalide: $card_name")
			}
			instance.cards[class_name] = cd
		}
		return cd
	}

	static Card new_card(String card_name) {
		//println getCardDefinition(card_name)
		return new Card(getCardDefinition(card_name))
	}
}
