package main;

public enum Weapon {
    // Weapon types
    NORMAL_SWORD("Normal_Sword", 10, 100, 1, 1, 10),
    FIRE_SWORD("Fire Sword", 20, 50, 0.5, 2, 2),
    ICE_SWORD("Ice Sword", 5, 200, 0.1, 3, 30);

    public String name;
    public int damage;
    public int range;
    public double fireRate;
    public double coolDown;
    public int clipSize;

    Weapon(String name, int damage, int range, double fireRate, double coolDown, int clipSize) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.coolDown = coolDown;
        this.clipSize = clipSize;
    }
}
