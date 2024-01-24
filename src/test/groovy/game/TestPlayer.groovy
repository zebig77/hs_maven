package game;

import static org.junit.Assert.*

import org.junit.Test

import utils.TestHelper;
import decks.GarroshDeck1

class TestPlayer extends TestHelper {

	@Test
	public void Player_play_minion() {
		/*
		 * test 1 : pas assez de mana disponible
		 */
		def abo = Game.new_card("Abomination")
		p1.available_mana = abo.get_cost()-1
		p1.hand.add(abo)
		try {
			p1.play(abo)
			fail("auraît du détecter pas assez de mana")
		}
		catch( Exception e ) {
			println e // OK
			assert p1.hand.contains(abo)
			assert p1.minions.contains(abo) == false
		}
		
		/*
		 * test 2 : ok assez de mana, le serviteur devrait arriver en jeu
		 */
		def twl = Game.new_card("Timber Wolf")
		p1.available_mana = twl.get_cost()
		p1.hand.add(twl)
		p1.play(twl)
		assert p1.hand.contains(twl) == false
		assert p1.minions.contains(twl)

	}

	@Test
	public void Player_play_spell() {
		/*
		 * test 1 : pas de cible possible
		 */
		def exe = Game.new_card("Execute")
		p1.available_mana = exe.get_cost()
		p1.hand.add(exe)
		try {
			p1.play(exe)
			fail("aurait du détecter pas de cible")
		}
		catch( Exception e ) {
			println e // OK
			assert p1.hand.contains(exe)
		}		
	}

	@Test
	public void Player_play_weapon() {
		/*
		 * test 1 : ajout d'une arme
		 */
		assert p1.hero.weapon == null
		def arr = _play("Arcanite Reaper")
		assert p1.hero.weapon != null
		assert p1.hero.get_attack() == arr.get_attack()
		assert p1.hero.weapon.durability == arr.get_max_health()
	}
	
	
	@Test
	public void testPrintStat() {
		println "Stats active player:"
		println g.active_player.stats()
		println "Stats opponent:"
		println g.passive_player.stats()
	}

}
