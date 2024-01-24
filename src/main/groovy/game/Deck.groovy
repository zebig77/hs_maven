package game

import state.ListState

class Deck {
	
	ListState<Card> cards = [] 
	
	def build(Map definition) {
		cards.clear()
		definition.each{ String card_name, int count ->
			count.times{ 
				def cd = CardLibrary.getCardDefinition(card_name)
				if (cd.collectible == false) {
					throw new InvalidDeckException("$card_name ne peut pas être mis dans un deck")
				}
				Card c = new Card(CardLibrary.getCardDefinition(card_name))
				add(c)
			}
		}
		if (size() != 30) {
			throw new InvalidDeckException("La définition du deck contient ${size()} cartes au lieu de 30")
		}
	}
	
	Card draw() {
		if (cards.isEmpty())
			return null
		return cards.remove(0)
	}
	
	def add(Card c) {
		cards.add(0, c)
	}
	
	boolean isEmpty() {
		return cards.isEmpty()
	}
	
	int size() {
		return cards.size()
	}
}
