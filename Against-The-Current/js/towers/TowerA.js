class TowerA extends Tower {
    constructor() {
        super(1);
    }

    goUpgradeTo1() {
        this.ARMOR_PEN = 1;
        this.DAMAGE = 100;
        this.SHOT_DELAY = 300;
        this.SLOW = 0;

        this.BUILDING_PRICE = 200;
        this.UPGRADE_PRICE = 100;
    }

    goUpgradeTo2() {
        this.LEVEL = 2;
        this.RANGE = 4;
        this.DAMAGE = 140;
        this.SLOW = 0;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
        this.UPGRADE_PRICE = 150;
    }

    goUpgradeTo3() {
        this.LEVEL = 3;
        this.RANGE = 5;
        this.DAMAGE = 200;
        this.SLOW = 0;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
    }
}
