class Explosion {
    constructor() {
        this.sprite = Dakra.explosionGroup.create(0, 0, 'holders', 'tower-3-upgrade-1.png');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.kill();
        this.counter = 0;
    }

    revive(x, y) {
        this.sprite.reset(x, y);
    }

    update() {
        if (this.counter++ == 1) {
            this.sprite.kill();
            this.counter = 0;
        }
    }
}
