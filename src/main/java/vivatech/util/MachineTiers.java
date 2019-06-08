package vivatech.util;

public enum MachineTiers {
	
	MINIMAL(1, 0, "minimal"), NORMAL(2, 3, "normal"), ADVANCED(3, 5, "advanced");
	
	private final int speedMultiplier;
	private final int upgradeSlots;
	private final String affix;
	
	public int getSpeedMultiplier() {
		return speedMultiplier;
	}

	public int getUpgradeSlots() {
		return upgradeSlots;
	}

	public String getAffix() {
		return affix;
	}

	MachineTiers(int speedMultiplier, int upgradeSlots, String affix) {
		this.speedMultiplier = speedMultiplier;
		this.upgradeSlots = upgradeSlots;
		this.affix = affix;
	}

}