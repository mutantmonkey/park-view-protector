package org.javateerz.ParkViewProtector.Levels;

import org.javateerz.ParkViewProtector.Bosses.Boss;

public interface BossLevel
{
	public Boss getBoss();
	public boolean levelComplete();
}
