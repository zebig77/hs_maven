package mechanics.buffs

import game.Target

class FrozenBuff extends Buff {
	
	static final BUFF_STRING = 'frozen'

	public FrozenBuff(Target t) {
		super(BuffType.FROZEN,t)
	}
	
}
