class TowerB extends Tower {
    constructor() {
        super(2);
    }

    goUpgradeTo1() {
        this.ARMOR_PEN = 1;
        this.DAMAGE = 50;
        this.SHOT_DELAY = 300;
        this.SLOW = 5;

        this.BUILDING_PRICE = 150;
        this.UPGRADE_PRICE = 70;
    }

    goUpgradeTo2() {
        this.LEVEL = 2;
        this.RANGE = 4;
        this.DAMAGE = 70;
        this.SLOW = 7;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
        this.UPGRADE_PRICE = 120;
    }

    goUpgradeTo3() {
        this.LEVEL = 3;
        this.RANGE = 5;
        this.DAMAGE = 100;
        this.SLOW = 10;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
    }
}
