class TowerD extends Tower {
    constructor() {
        super(4);
    }

    goUpgradeTo1() {
        this.ARMOR_PEN = 1;
        this.DAMAGE = 1000;
        this.SHOT_DELAY = 2000;
        this.SLOW = 0;

        this.BUILDING_PRICE = 400;
        this.UPGRADE_PRICE = 200;
    }

    goUpgradeTo2() {
        this.LEVEL = 2;
        this.RANGE = 4;
        this.DAMAGE = 1400;
        this.SLOW = 0;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
        this.UPGRADE_PRICE = 300;
    }

    goUpgradeTo3() {
        this.LEVEL = 3;
        this.RANGE = 5;
        this.DAMAGE = 2000;
        this.SLOW = 0;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
    }
}
