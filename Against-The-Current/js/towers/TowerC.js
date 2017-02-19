class TowerC extends Tower {
    constructor() {
        super(3);
    }

    goUpgradeTo1() {
        this.ARMOR_PEN = 0;
        this.DAMAGE = 50;
        this.SHOT_DELAY = 300;
        this.SLOW = 0;

        this.BUILDING_PRICE = 300;
        this.UPGRADE_PRICE = 150;
    }

    goUpgradeTo2() {
        this.LEVEL = 2;
        this.RANGE = 4;
        this.DAMAGE = 70;
        this.SLOW = 0;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
        this.UPGRADE_PRICE = 200;
    }

    goUpgradeTo3() {
        this.LEVEL = 3;
        this.RANGE = 5;
        this.DAMAGE = 50;
        this.SLOW = 0;

        Dakra.MONEY = Dakra.MONEY - this.UPGRADE_PRICE;
    }
}
